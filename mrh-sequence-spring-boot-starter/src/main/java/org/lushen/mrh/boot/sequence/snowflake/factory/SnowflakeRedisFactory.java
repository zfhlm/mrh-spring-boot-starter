package org.lushen.mrh.boot.sequence.snowflake.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeCustomizer;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeFactory;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeProperties;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeWorker;
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

	private final String basePath;

	private final Long liveTimeMillis;

	private final SnowflakeCustomizer customizer;

	private final RedisConnectionFactory connectionFactory;

	public SnowflakeRedisFactory(RedisConnectionFactory connectionFactory, SnowflakeProperties properties, SnowflakeCustomizer customizer) {
		super();
		this.connectionFactory = connectionFactory;
		this.basePath = properties.getBasePath();
		this.liveTimeMillis = properties.getLiveTimeMillis();
		this.customizer = customizer;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public SnowflakeWorker build() {

		RedisConnection connection = this.connectionFactory.getConnection();

		connection.close();

		return null;
	}

}
