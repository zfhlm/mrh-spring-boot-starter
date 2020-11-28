package org.lushen.mrh.boot.autoconfigure.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 缓存自动配置
 * 
 * @author hlm
 */
@Configuration
@Import(RedisCacheAutoConfiguration.class)
@ConditionalOnBean(ProxyCachingConfiguration.class)
public class CacheAutoConfiguration extends CachingConfigurerSupport {

	private final Log log = LogFactory.getLog(CacheAutoConfiguration.class);

	@Override
	public KeyGenerator keyGenerator() {
		log.info("Initialize bean " + CacheKeyGenerator.class);
		return new CacheKeyGenerator();
	}

}
