package org.lushen.mrh.sequence.snowflake.factory;

import static org.lushen.mrh.sequence.snowflake.SnowflakeWorker.maxCenterId;
import static org.lushen.mrh.sequence.snowflake.SnowflakeWorker.maxWorkerId;

import java.util.LinkedHashMap;
import java.util.stream.IntStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.sequence.snowflake.SnowflakeConsumer;
import org.lushen.mrh.sequence.snowflake.SnowflakeFactory;
import org.lushen.mrh.sequence.snowflake.SnowflakeProperties;
import org.lushen.mrh.sequence.snowflake.SnowflakeWorker;
import org.lushen.mrh.sequence.snowflake.support.NodeDataSerializer;
import org.lushen.mrh.sequence.snowflake.support.NodePathSerializer;
import org.lushen.mrh.sequence.snowflake.support.NodePathSerializer.Node;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 使用redis作为注册中心
 * 
 * @author hlm
 */
public class SnowflakeRedisFactory implements SnowflakeFactory, InitializingBean {

	private final Log log = LogFactory.getLog(getClass());

	private final NodePathSerializer nodePathSerializer = new NodePathSerializer();

	private final NodeDataSerializer nodeDataSerializer = new NodeDataSerializer();

	private final LinkedHashMap<String, Node> nodes = new LinkedHashMap<String, Node>();

	private final String basePath;

	private final Long liveTimeMillis;

	private final SnowflakeConsumer consumer;

	private final RedisConnectionFactory connectionFactory;

	public SnowflakeRedisFactory(RedisConnectionFactory connectionFactory, SnowflakeProperties properties, SnowflakeConsumer consumer) {
		super();
		this.connectionFactory = connectionFactory;
		this.basePath = properties.getBasePath();
		this.liveTimeMillis = properties.getLiveTimeMillis();
		this.consumer = consumer;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		// 创建节点信息
		IntStream.range(0, (int)((maxCenterId+1)*(maxWorkerId+1))).forEach(seed -> {
			Node node = new Node((int)(seed/maxCenterId), (int)(seed%maxWorkerId));
			String nodePath = this.nodePathSerializer.serialize(node);
			this.nodes.put(nodePath, node);
		});

	}

	@Override
	public SnowflakeWorker build() {

		RedisConnection connection = this.connectionFactory.getConnection();

		connection.close();

		return null;
	}

}
