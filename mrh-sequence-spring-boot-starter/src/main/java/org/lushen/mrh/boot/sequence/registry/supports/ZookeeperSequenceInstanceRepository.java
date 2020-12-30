package org.lushen.mrh.boot.sequence.registry.supports;

import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.NODE_ADDED;
import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.NODE_REMOVED;
import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.NODE_UPDATED;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.lushen.mrh.boot.sequence.registry.SequenceInstance;
import org.lushen.mrh.boot.sequence.registry.SequenceInstanceCustomizer;
import org.lushen.mrh.boot.sequence.registry.SequenceInstanceRepository;
import org.lushen.mrh.boot.sequence.registry.SequenceInstanceSerializer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * zookeeper 序列实例持久化实现
 * 
 * @author hlm
 * @param <T>
 */
public class ZookeeperSequenceInstanceRepository<T> implements SequenceInstanceRepository<T>, TreeCacheListener, InitializingBean, DisposableBean {

	private final Map<String, SequenceInstance<T>> cache = new HashMap<String, SequenceInstance<T>>();

	private SequenceInstanceSerializer<T> instanceSerializer;

	private SequenceInstanceCustomizer<T> instanceCustomizer;

	private CuratorFramework client;

	private TreeCache treeCache;

	private String basePath;

	public ZookeeperSequenceInstanceRepository(
			SequenceInstanceSerializer<T> instanceSerializer,
			SequenceInstanceCustomizer<T> instanceCustomizer, 
			CuratorFramework client, 
			String basePath) {
		super();
		this.instanceSerializer = instanceSerializer;
		this.instanceCustomizer = instanceCustomizer;
		this.client = client;
		this.basePath = basePath;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		// 创建监听路径
		String watchPath = watchPath();
		if(this.client.checkExists().forPath(watchPath) == null) {
			this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(watchPath);
		}

		// 创建监听器
		this.treeCache = new TreeCache(this.client, watchPath);
		this.treeCache.getListenable().addListener(this);
		this.treeCache.start();

		// 从注册中心拉取到本地缓存
		synchronized (this) {
			for(String nodePath : this.client.getChildren().forPath(watchPath)) {
				byte[] buffer = this.client.getData().forPath(fullPath(nodePath));
				SequenceInstance<T> instance = this.instanceSerializer.deserialize(buffer);
				this.cache.put(instance.getId(), instance);
			}
		}

	}

	@Override
	public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {

		// 注册中心节点发生变化，更新缓存
		TreeCacheEvent.Type eventType = event.getType();
		ChildData childData = event.getData();
		if(childData != null && ! StringUtils.equals(childData.getPath(), watchPath()) ) {
			synchronized (this) {
				if(eventType == NODE_ADDED || eventType == NODE_UPDATED) {
					SequenceInstance<T> instance = this.instanceSerializer.deserialize(childData.getData());
					this.cache.put(instance.getId(), instance);
				}
				else if(eventType == NODE_REMOVED) {
					SequenceInstance<T> instance = this.instanceSerializer.deserialize(childData.getData());
					this.cache.remove(instance.getId());
				}
			}
		}

	}

	@Override
	public void destroy() throws Exception {

		// 关闭节点监听
		if(this.treeCache != null) {
			this.treeCache.close();
			this.treeCache = null;
		}

	}

	@Override
	public SequenceInstance<T> findById(String id) throws Exception {
		SequenceInstance<T> instance = this.cache.get(id);
		return (instance == null? null : instance.clone());
	}

	@Override
	public void save(SequenceInstance<T> instance) throws Exception {

		// 序列化实例信息
		this.instanceCustomizer.customize(instance);
		byte[] buffer = this.instanceSerializer.serialize(instance);

		// 存储实例信息
		this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(fullPath(instance.getId()), buffer);
		this.cache.put(instance.getId(), instance);

	}

	@Override
	public void update(SequenceInstance<T> instance) throws Exception {

		// 本地缓存版本号验证
		SequenceInstance<T> cacheInstance = this.cache.get(instance.getId());
		if(cacheInstance == null) {
			throw new RuntimeException("Unable to find cache instance.");
		}
		if(instance.getVersion() != cacheInstance.getVersion()) {
			throw new RuntimeException("Unable to update instance, expired version.");
		}

		// 分布式锁
		InterProcessMutex lock = new InterProcessMutex(this.client, lockPath(instance.getId()));
		try {
			if(lock.acquire(0, TimeUnit.MICROSECONDS)) {

				// 获取远程实例信息验证版本号
				SequenceInstance<T> remoteInstance = this.instanceSerializer.deserialize(this.client.getData().forPath(fullPath(instance.getId())));
				if(remoteInstance == null || instance.getVersion() != cacheInstance.getVersion()) {
					throw new RuntimeException("Unable to update instance, expired version.");
				}

				// 序列化实例信息
				this.instanceCustomizer.customize(instance);
				byte[] buffer = this.instanceSerializer.serialize(instance);

				// 更新实例信息
				this.client.setData().forPath(fullPath(instance.getId()), buffer);
				this.cache.put(instance.getId(), instance);

			}
		} finally {
			lock.release();
		}

	}

	@Override
	public void remove(String id) throws Exception {

		// 删除指定实例
		this.client.delete().forPath(fullPath(id));
		this.cache.remove(id);

	}

	@Override
	public void trancate() throws Exception {

		// 清除所有实例
		this.client.delete().forPath(this.basePath);
		this.cache.clear();

	}

	private String watchPath() {
		return new StringBuilder(this.basePath).append("/").append("node").toString();
	}

	private String fullPath(String nodePath) {
		return new StringBuilder(watchPath()).append("/").append(nodePath).toString();
	}

	private String lockPath(String nodePath) {
		return new StringBuilder(this.basePath).append("/").append("lock").append("/").append(nodePath).toString();
	}

}
