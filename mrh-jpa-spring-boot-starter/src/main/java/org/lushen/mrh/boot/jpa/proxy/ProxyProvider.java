package org.lushen.mrh.boot.jpa.proxy;

/**
 * 代理对象生成器
 * 
 * @author helm
 */
public interface ProxyProvider {

	/**
	 * 生成代理对象
	 * 
	 * @param proxyClass
	 * @param proxyHandler
	 * @return
	 * @throws Exception
	 */
	public <T> T proxy(Class<T> proxyClass, ProxyHandler proxyHandler) throws Exception;

}
