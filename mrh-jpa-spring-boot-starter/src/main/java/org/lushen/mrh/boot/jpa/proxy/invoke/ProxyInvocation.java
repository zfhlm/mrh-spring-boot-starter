package org.lushen.mrh.boot.jpa.proxy.invoke;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 代理方法调用关联信息
 * 
 * @author helm
 * @param <T>
 */
public class ProxyInvocation<T> {

	private final long threadId = Thread.currentThread().getId();

	private final Class<T> proxyClass;

	private final T instance;

	private final Method method;

	private final Object[] args;

	public ProxyInvocation(Class<T> proxyClass, T instance, Method method, Object[] args) {
		super();
		this.proxyClass = proxyClass;
		this.instance = instance;
		this.method = method;
		this.args = args;
	}

	public long getThreadId() {
		return threadId;
	}

	public Class<T> getProxyClass() {
		return proxyClass;
	}

	public T getInstance() {
		return instance;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProxyInvocation [threadId=");
		builder.append(threadId);
		builder.append(", proxyClass=");
		builder.append(proxyClass);
		builder.append(", instance=");
		builder.append(instance);
		builder.append(", method=");
		builder.append(method);
		builder.append(", args=");
		builder.append(Arrays.toString(args));
		builder.append("]");
		return builder.toString();
	}

}
