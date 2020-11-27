package org.lushen.mrh.boot.autoconfigure.cache.redis;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * redis 缓存配置
 * 
 * @author hlm
 */
public class RedisCacheProperties {

	private RedisCacheDefaultProperties defaultCache;			// 默认缓存配置

	private List<RedisCacheInitialProperties> initialCaches;	// 指定名称缓存配置

	private RedisCachePair valuePair = RedisCachePair.JDK;		// 缓存value序列化方式

	private boolean enableTransactions = true;					// 是否开启事务

	private boolean allowInFlightCacheCreation = true;			// 是否创建不存在缓存

	public RedisCacheDefaultProperties getDefaultCache() {
		return defaultCache;
	}

	public void setDefaultCache(RedisCacheDefaultProperties defaultCache) {
		this.defaultCache = defaultCache;
	}

	public List<RedisCacheInitialProperties> getInitialCaches() {
		return (initialCaches == null? Collections.emptyList() : initialCaches);
	}

	public void setInitialCaches(List<RedisCacheInitialProperties> initialCaches) {
		this.initialCaches = initialCaches;
	}

	public RedisCachePair getValuePair() {
		return valuePair;
	}

	public void setValuePair(RedisCachePair valuePair) {
		this.valuePair = valuePair;
	}

	public boolean isEnableTransactions() {
		return enableTransactions;
	}

	public void setEnableTransactions(boolean enableTransactions) {
		this.enableTransactions = enableTransactions;
	}

	public boolean isAllowInFlightCacheCreation() {
		return allowInFlightCacheCreation;
	}

	public void setAllowInFlightCacheCreation(boolean allowInFlightCacheCreation) {
		this.allowInFlightCacheCreation = allowInFlightCacheCreation;
	}

	/**
	 * redis cache 序列化方式
	 * 
	 * @author hlm
	 */
	public static enum RedisCachePair {

		/**
		 * 使用 Java 序列化
		 */
		JDK,

		/**
		 * 使用 Json 序列化
		 */
		JSON,

	}

	/**
	 * redis 缓存全局配置
	 * 
	 * @author hlm
	 */
	public static class RedisCacheDefaultProperties {

		private boolean useKeyPrefix = true;		// 是否使用前缀

		private String keyPrefix;					// 缓存key前缀

		private boolean cacheNullValues = true;		// 是否缓存空值

		private Duration timeToLive;				// 缓存有效时间

		public boolean isUseKeyPrefix() {
			return useKeyPrefix;
		}

		public void setUseKeyPrefix(boolean useKeyPrefix) {
			this.useKeyPrefix = useKeyPrefix;
		}

		public String getKeyPrefix() {
			return keyPrefix;
		}

		public void setKeyPrefix(String keyPrefix) {
			this.keyPrefix = keyPrefix;
		}

		public boolean isCacheNullValues() {
			return cacheNullValues;
		}

		public void setCacheNullValues(boolean cacheNullValues) {
			this.cacheNullValues = cacheNullValues;
		}

		public Duration getTimeToLive() {
			return timeToLive;
		}

		public void setTimeToLive(Duration timeToLive) {
			this.timeToLive = timeToLive;
		}

	}

	/**
	 * redis 指定名称缓存配置
	 * 
	 * @author hlm
	 */
	public static class RedisCacheInitialProperties extends RedisCacheDefaultProperties {

		private String name;			// 缓存名称

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
