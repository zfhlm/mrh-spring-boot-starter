package org.lushen.mrh.boot.jpa.proxy.invoke;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * 调用接口定义
 * 
 * @author helm
 * @param <T>
 */
public interface ProxyInvoker<T> {

	/**
	 * 代理对象
	 * 
	 * @return
	 */
	T instance();

	/**
	 * 代理对象实际类型
	 * 
	 * @return
	 */
	Class<T> proxyClass();

	/**
	 * 获取调用方法相关信息
	 * 
	 * @param function
	 * @return
	 */
	ProxyInvocation<T> invoke(Function<T, ?> function);

	/**
	 * 获取调用方法相关信息
	 * 
	 * @param function
	 * @return
	 */
	ProxyInvocation<T> invoke(ToIntFunction<T> function);

	/**
	 * 获取调用方法相关信息
	 * 
	 * @param function
	 * @return
	 */
	ProxyInvocation<T> invoke(ToLongFunction<T> function);

	/**
	 * 获取调用方法相关信息
	 * 
	 * @param function
	 * @return
	 */
	ProxyInvocation<T> invoke(ToDoubleFunction<T> function);

	/**
	 * 获取调用方法相关信息
	 * 
	 * @param consumer
	 * @return
	 */
	ProxyInvocation<T> invoke(Consumer<T> consumer);

}
