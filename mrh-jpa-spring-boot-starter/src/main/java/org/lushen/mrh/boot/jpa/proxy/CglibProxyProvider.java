package org.lushen.mrh.boot.jpa.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * cglib动态代理对象生成器
 * 
 * @author helm
 */
@SuppressWarnings("unchecked")
public class CglibProxyProvider implements ProxyProvider {

	@Override
	public <T> T proxy(Class<T> proxyClass, ProxyHandler proxyHandler) throws Exception {
		if(proxyClass == null) {
			throw new IllegalArgumentException("proxyClass is null.");
		}
		if(proxyHandler == null) {
			throw new IllegalArgumentException("proxyHandler is null.");
		}
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(proxyClass);
		enhancer.setCallback( new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				return proxyHandler.invoke(proxyClass, obj, method, args);
			}
		});
		return (T)enhancer.create();
	}

}
