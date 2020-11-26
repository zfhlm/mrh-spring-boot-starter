package org.lushen.mrh.boot.jpa.proxy;

import java.lang.reflect.Method;

/**
 * 动态代理 handler
 * 
 * @author hlm
 */
public interface ProxyHandler {

	/**
	 * 回调方法
	 * 
	 * @param proxyClass 被代理类型
	 * @param instance 代理实例
	 * @param method 代理实例执行的方法
	 * @param args 执行方法的参数
	 * @return
	 * @throws Throwable
	 */
	public Object invoke(Class<?> proxyClass, Object instance, Method method, Object[] args) throws Throwable;

}
