package org.lushen.mrh.boot.jpa.proxy.invoke;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import org.lushen.mrh.boot.jpa.proxy.ProxyHandler;
import org.lushen.mrh.boot.jpa.proxy.ProxyProvider;

/**
 * 动态代理调用接口实现
 * 
 * @author helm
 * @param <T>
 */
public class ProxyInstance<T> implements ProxyInvoker<T>, ProxyHandler {

	private final Queue<ProxyInvocation<T>> invocationQueue = new LinkedList<ProxyInvocation<T>>();

	private final Class<T> proxyClass;

	private final T instance;

	public ProxyInstance(Class<T> proxyClass, ProxyProvider proxyProvider) {
		super();
		try {
			this.proxyClass = proxyClass;
			this.instance = proxyProvider.proxy(proxyClass, this);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Class<?> proxyClass, Object instance, Method method, Object[] args) throws Throwable {
		this.invocationQueue.add(new ProxyInvocation<T>(this.proxyClass, (T)instance, method, args));
		return null;
	}

	@Override
	public T instance() {
		return this.instance;
	}

	@Override
	public Class<T> proxyClass() {
		return this.proxyClass;
	}

	@Override
	public ProxyInvocation<T> invoke(Function<T, ?> function) {
		try {
			function.apply(this.instance);
			return this.invocationQueue.poll();
		} finally {
			this.invocationQueue.clear();
		}
	}

	@Override
	public ProxyInvocation<T> invoke(ToIntFunction<T> function) {
		try {
			function.applyAsInt(this.instance);
			return this.invocationQueue.poll();
		} finally {
			this.invocationQueue.clear();
		}
	}

	@Override
	public ProxyInvocation<T> invoke(ToLongFunction<T> function) {
		try {
			function.applyAsLong(this.instance);
			return this.invocationQueue.poll();
		} finally {
			this.invocationQueue.clear();
		}
	}

	@Override
	public ProxyInvocation<T> invoke(ToDoubleFunction<T> function) {
		try {
			function.applyAsDouble(this.instance);
			return this.invocationQueue.poll();
		} finally {
			this.invocationQueue.clear();
		}
	}

	@Override
	public ProxyInvocation<T> invoke(Consumer<T> consumer) {
		try {
			consumer.accept(this.instance);
			return this.invocationQueue.poll();
		} finally {
			this.invocationQueue.clear();
		}
	}

}
