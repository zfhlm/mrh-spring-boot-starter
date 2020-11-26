package org.lushen.mrh.boot.sequence.snowflake.factory;

import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.NODE_ADDED;
import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.NODE_REMOVED;
import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.NODE_UPDATED;
import static org.lushen.mrh.boot.sequence.snowflake.SnowflakeWorker.maxCenterId;
import static org.lushen.mrh.boot.sequence.snowflake.SnowflakeWorker.maxWorkerId;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeConsumer;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeFactory;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakePayload;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeProperties;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeWorker;
import org.lushen.mrh.boot.sequence.snowflake.support.NodeDataSerializer;
import org.lushen.mrh.boot.sequence.snowflake.support.NodePathSerializer;
import org.lushen.mrh.boot.sequence.snowflake.support.NodePathSerializer.Node;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 使用zookeeper作为注册中心
 * 
 * @author hlm
 */
public class SnowflakeCuratorFactory implements SnowflakeFactory, InitializingBean, DisposableBean, TreeCacheListener {

	private final Log log = LogFactory.getLog(getClass());

	private final NodePathSerializer nodePathSerializer = new NodePathSerializer();

	private final NodeDataSerializer nodeDataSerializer = new NodeDataSerializer();

	private final LinkedHashMap<String, SnowflakePayload> payloads = new LinkedHashMap<String, SnowflakePayload>();

	private final CuratorFramework client;

	private final String basePath;

	private final Long liveTimeMillis;

	private final SnowflakeConsumer consumer;

	private TreeCache treeCache;

	public SnowflakeCuratorFactory(CuratorFramework client, SnowflakeProperties properties, SnowflakeConsumer consumer) {
		super();
		this.client = client;
		this.basePath = properties.getBasePath();
		this.liveTimeMillis = properties.getLiveTimeMillis();
		this.consumer = consumer;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		// 创建监听路径
		if(this.client.checkExists().forPath(this.basePath) == null) {
			this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(this.basePath);
		}

		// 创建监听器
		this.treeCache = new TreeCache(this.client, this.basePath);
		this.treeCache.getListenable().addListener(this);
		this.treeCache.start();

		// 创建本地节点缓存
		IntStream.range(0, (int)((maxCenterId+1)*(maxWorkerId+1))).forEach(seed -> {
			int centerId = (int)(seed/maxCenterId);
			int workerId = (int)(seed%maxWorkerId);
			SnowflakePayload payload = original(centerId, workerId);
			this.payloads.put(payload.getNodePath(), payload);
		});

		// 从注册中心更新缓存节点信息
		synchronized (this) {
			for(String nodePath : this.client.getChildren().forPath(this.basePath)) {
				if(this.payloads.containsKey(nodePath)) {
					byte[] buffer = this.client.getData().forPath(fullPath(nodePath));
					SnowflakePayload payload = this.nodeDataSerializer.deserialize(buffer);
					this.payloads.put(payload.getNodePath(), payload);
				}
			}
		}

	}

	@Override
	public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {

		TreeCacheEvent.Type eventType = event.getType();
		ChildData childData = event.getData();

		// 注册中心节点发生变化，更新缓存
		if(childData != null && this.payloads.containsKey(childData.getPath())) {
			synchronized (this) {
				if(eventType == NODE_ADDED || eventType == NODE_UPDATED) {
					SnowflakePayload payload = this.nodeDataSerializer.deserialize(childData.getData());
					this.payloads.put(payload.getNodePath(), payload);
				}
				else if(eventType == NODE_REMOVED) {
					SnowflakePayload payload = this.payloads.get(childData.getPath());
					SnowflakePayload original = original(payload.getCenterId(), payload.getWorkerId());
					this.payloads.put(original.getNodePath(), original);
				}
			}
		}

	}

	@Override
	public void destroy() throws Exception {

		// 关闭节点监听
		if(this.treeCache != null) {
			this.treeCache.close();
		}

	}

	@Override
	public SnowflakeWorker build() {

		while (true) {

			Iterator<SnowflakePayload> iterator = this.payloads.values().iterator();

			while(iterator.hasNext()) {

				SnowflakePayload payload = iterator.next();
				long currentTime = System.currentTimeMillis();

				// 跳过不可用节点
				if(payload.isRegister() && payload.getExpiredAt() >= currentTime) {
					continue;
				}

				String fullPath = fullPath(payload.getNodePath());
				String lockPath = lockPath(payload.getNodePath());
				InterProcessMutex lock = new InterProcessMutex(this.client, lockPath);
				try {
					if(lock.acquire(0, TimeUnit.MICROSECONDS)) {
						if(this.client.checkExists().forPath(fullPath) != null && this.nodeDataSerializer.deserialize(this.client.getData().forPath(fullPath)).getUpdateAt() == payload.getUpdateAt()) {
							payload.setRegister(true);
							payload.setBeginAt(currentTime);
							payload.setExpiredAt(currentTime + this.liveTimeMillis);
							payload.setUpdateAt(currentTime);
							this.consumer.consume(payload);
							this.client.setData().forPath(fullPath, this.nodeDataSerializer.serialize(payload));
							log.info("Subject payload : " + payload);
							return new SnowflakeWorker(payload);
						} else {
							payload.setRegister(true);
							payload.setBeginAt(currentTime);
							payload.setExpiredAt(currentTime + this.liveTimeMillis);
							payload.setCreateAt(currentTime);
							payload.setUpdateAt(currentTime);
							this.consumer.consume(payload);
							this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(fullPath, this.nodeDataSerializer.serialize(payload));
							log.info("Register payload : " + payload);
							return new SnowflakeWorker(payload);
						}
					}
				} catch (Exception e) {
					log.warn(e.getMessage(), e);
				} finally {
					try {
						lock.release();
					} catch (Exception e) {}
				}

			}

			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				throw new RuntimeException(e.getMessage(), e);
			}

			log.info("Unable to find available payload with iterator, try again!");

		}

	}

	private SnowflakePayload original(int centerId, int workerId) {

		// 创建初始节点信息
		SnowflakePayload payload = new SnowflakePayload();
		payload.setCenterId(centerId);
		payload.setWorkerId(workerId);
		payload.setBasePath(this.basePath);
		payload.setNodePath(this.nodePathSerializer.serialize(new Node(centerId, workerId)));
		payload.setRegister(false);

		return payload;

	}

	private String fullPath(String nodePath) {
		return new StringBuilder(this.basePath).append("/").append(nodePath).toString();
	}

	private String lockPath(String nodePath) {
		return new StringBuilder(this.basePath).append("/").append("lock").append("/").append(nodePath).toString();
	}

}
