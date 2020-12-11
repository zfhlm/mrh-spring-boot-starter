package org.lushen.mrh.boot.autoconfigure.cache.redis;

import static java.util.stream.Collectors.toMap;
import static org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheAutoConfiguration.ENABLED;
import static org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheAutoConfiguration.NAMESPACE;
import static org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheAutoConfiguration.TRUE;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheProperties.RedisCacheDefaultProperties;
import org.lushen.mrh.boot.autoconfigure.cache.redis.RedisCacheProperties.RedisCachePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis 缓存自动配置
 * 
 * @author hlm
 */
@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties
@ConditionalOnProperty(prefix=NAMESPACE, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
public class RedisCacheAutoConfiguration {

	private final Log log = LogFactory.getLog(RedisCacheAutoConfiguration.class);

	static final String NAMESPACE = "org.lushen.mrh.cache.redis";

	static final String ENABLED = "enabled";

	static final String TRUE = "true";

	/**
	 * 注入缓存配置信息
	 */
	@Bean
	@ConfigurationProperties(prefix=NAMESPACE)
	public RedisCacheProperties redisCacheProperties() {
		return new RedisCacheProperties();
	}

	/**
	 * 注册自定义缓存管理器bean
	 */
	@Bean
	@ConditionalOnMissingBean(RedisCacheManager.class)
	public RedisCacheManager redisCacheManager(@Autowired RedisConnectionFactory connectionFactory, @Autowired RedisCacheProperties properties) {
		log.info("Initialize bean " + RedisCacheManager.class);
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
				config = config.computePrefixWith(CacheKeyPrefix.prefixed(properties.getKeyPrefix()));
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
