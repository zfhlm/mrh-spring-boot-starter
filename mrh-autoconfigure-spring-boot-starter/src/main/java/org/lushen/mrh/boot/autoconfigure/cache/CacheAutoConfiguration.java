package org.lushen.mrh.boot.autoconfigure.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 缓存自动配置
 * 
 * @author hlm
 */
@Configuration
@Import({
	RedisCacheAutoConfiguration.class
})
@ConditionalOnBean(CacheManager.class)
public class CacheAutoConfiguration {

	private final Log log = LogFactory.getLog(CacheAutoConfiguration.class);

	@Bean(CacheKeyGenerator.CACHE_KEY_GENERATOR)
	public CacheKeyGenerator cacheKeyGenerator() {
		log.info("Initialize bean " + CacheKeyGenerator.class);
		return new CacheKeyGenerator();
	}

}
