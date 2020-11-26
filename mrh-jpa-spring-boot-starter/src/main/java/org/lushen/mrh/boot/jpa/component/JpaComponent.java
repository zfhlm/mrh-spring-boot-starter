package org.lushen.mrh.boot.jpa.component;

import java.util.function.Function;

import org.lushen.mrh.boot.jpa.proxy.invoke.ProxyInvocation;
import org.lushen.mrh.boot.jpa.proxy.invoke.ProxyInvoker;
import org.lushen.mrh.boot.jpa.reflect.ClassFieldReader;
import org.lushen.mrh.boot.jpa.reflect.reader.CacheFieldReader;
import org.lushen.mrh.boot.jpa.reflect.reader.ReflectFieldReader;
import org.lushen.mrh.boot.jpa.reflect.reader.RequireFieldReader;

/**
 * jpa 扩展组件
 * 
 * @author helm
 */
public abstract class JpaComponent<E> {

	private static final ClassFieldReader FIELD_READER = new RequireFieldReader(new CacheFieldReader(new ReflectFieldReader()));

	private ProxyInvoker<E> proxyInvoker;

	protected JpaComponent(ProxyInvoker<E> proxyInvoker) {
		super();
		this.proxyInvoker = proxyInvoker;
	}

	protected ProxyInvoker<E> getProxyInvoker() {
		return proxyInvoker;
	}

	protected String nameForGetter(Function<E, ?> function) {
		ProxyInvocation<E> invocation = proxyInvoker.invoke(function);
		return FIELD_READER.nameForGetter(invocation);
	}

}
