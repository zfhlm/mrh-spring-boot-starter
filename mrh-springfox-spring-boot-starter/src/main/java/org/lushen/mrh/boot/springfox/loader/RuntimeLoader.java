package org.lushen.mrh.boot.springfox.loader;

/**
 * 运行时信息加载器
 * 
 * @author hlm
 * @param <T>
 */
public interface RuntimeLoader<T> {

	/**
	 * 从当前运行环境加载信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public T load() throws Exception;

}
