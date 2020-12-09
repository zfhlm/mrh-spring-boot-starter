package org.lushen.mrh.boot.sequence.snowflake.factory;

import org.lushen.mrh.boot.sequence.snowflake.SnowflakeFactory;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeWorker;
import org.springframework.beans.factory.InitializingBean;

/**
 * 使用redis作为注册中心
 * 
 * @author hlm
 */
@Deprecated
public class SnowflakeRedisFactory implements SnowflakeFactory, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public SnowflakeWorker build() {
		return null;
	}

}
