package org.lushen.mrh.boot.autoconfigure.cache.redis;

import static java.util.stream.Collectors.toMap;
import static org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheAutoConfiguration.ENABLED;
import static org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheAutoConfiguration.NAMESPACE;
import static org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheAutoConfiguration.TRUE;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheProperties.RedisCacheDefaultProperties;
import org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheProperties.RedisCachePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.interceptor.CacheAspectSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis 缓存配置
 * 
 * @author hlm
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnBean({CacheAspectSupport.class, RedisConnectionFactory.class})
@ConditionalOnProperty(prefix=NAMESPACE, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
public class RedisCacheAutoConfiguration {

	static final String NAMESPACE = "org.lushen.mrh.cache.redis";

	static final String ENABLED = "enabled";

	static final String TRUE = "true";

	@Bean
	@ConfigurationProperties(prefix=NAMESPACE)
	public RedisCacheProperties redisCacheProperties() {
		return new RedisCacheProperties();
	}

	@Bean
	@ConditionalOnMissingBean(RedisCacheManager.class)
	public RedisCacheManager redisCacheManager(@Autowired RedisConnectionFactory connectionFactory, @Autowired RedisCacheProperties properties) {
		RedisCacheManagerBuilder builder = RedisCacheManager.builder(connectionFactory);
		builder.cacheDefaults(apply(properties.getDefaultCache(), properties.getValuePair()));
		builder.withInitialCacheConfigurations(properties.getInitialCaches().stream().collect(toMap(e -> e.getName(), e -> apply(e, properties.getValuePair()))));
		if(properties.isEnableTransactions()) {
			builder.transactionAware();
		}
		if( ! properties.isAllowInFlightCacheCreation() ) {
			builder.disableCreateOnMissingCache();
		}
		return builder.build();
	}

	private RedisCacheConfiguration apply(RedisCacheDefaultProperties properties, RedisCachePair valuePair) {

		// default setups
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		if(valuePair == RedisCachePair.JSON) {
			config = config.serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.json()));
		} else {
			config = config.serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.java()));
		}

		// other setups
		if(properties != null) {
			if( ! properties.isUseKeyPrefix() ) {
				config = config.disableKeyPrefix();
			}
			if(StringUtils.isNotBlank(properties.getKeyPrefix())) {
				config = config.prefixKeysWith(properties.getKeyPrefix());
			} else {
				config = config.disableKeyPrefix();
			}
			if(properties.getTimeToLive() != null) {
				config = config.entryTtl(properties.getTimeToLive());
			}
			if( ! properties.isCacheNullValues() ) {
				config = config.disableCachingNullValues();
			}
		}

		return config;
	}

}
