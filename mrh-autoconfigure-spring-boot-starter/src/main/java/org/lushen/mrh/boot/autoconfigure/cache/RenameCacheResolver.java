package org.lushen.mrh.boot.autoconfigure.cache;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;

/**
 * 重命名 cacheName 缓存处理器
 * 
 * @author hlm
 */
public abstract class RenameCacheResolver extends AbstractCacheResolver {

	public RenameCacheResolver() {
		super();
	}

	public RenameCacheResolver(CacheManager cacheManager) {
		super(cacheManager);
	}

	@Override
	protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
		Set<String> cacheNames = context.getOperation().getCacheNames();
		if(cacheNames == null || cacheNames.isEmpty()) {
			return cacheNames;
		} else {
			Object target = context.getTarget();
			Method method = context.getMethod();
			Object[] args = context.getArgs();
			return cacheNames.stream().map(cacheName -> rename(cacheName, target, method, args)).collect(Collectors.toList());
		}
	}

	/**
	 * cacheName 处理转换
	 * 
	 * @param cacheName
	 * @param target
	 * @param method
	 * @param args
	 * @return not null
	 */
	protected abstract String rename(String cacheName, Object target, Method method, Object[] args);

}
