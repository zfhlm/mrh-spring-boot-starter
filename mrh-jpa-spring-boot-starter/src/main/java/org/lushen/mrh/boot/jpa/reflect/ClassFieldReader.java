package org.lushen.mrh.boot.jpa.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.lushen.mrh.boot.jpa.proxy.invoke.ProxyInvocation;

/**
 * field reader
 * 
 * @author helm
 */
public interface ClassFieldReader {

	/**
	 * 根据get方法获取field name
	 * 
	 * @param invocation
	 * @return
	 */
	default String nameForGetter(ProxyInvocation<?> invocation) {
		Field field = withGetter(invocation);
		return (field == null? null : field.getName());
	}

	/**
	 * 根据get方法获取field name
	 * 
	 * @param clazz
	 * @param method
	 * @return
	 */
	default String nameForGetter(Class<?> clazz, Method method) {
		Field field = withGetter(clazz, method);
		return (field == null? null : field.getName());
	}

	/**
	 * 根据get方法获取field
	 * 
	 * @param invocation
	 * @return
	 */
	default Field withGetter(ProxyInvocation<?> invocation) {
		return withGetter(invocation.getProxyClass(), invocation.getMethod());
	}

	/**
	 * 根据get方法获取field
	 * 
	 * @param clazz
	 * @param method
	 * @return
	 */
	public Field withGetter(Class<?> clazz, Method method);

	/**
	 * 根据set方法获取field name
	 * 
	 * @param invocation
	 * @return
	 */
	default String nameForSetter(ProxyInvocation<?> invocation) {
		Field field = withSetter(invocation);
		return (field == null? null : field.getName());
	}

	/**
	 * 根据set方法获取field name
	 * 
	 * @param clazz
	 * @param method
	 * @return
	 */
	default String nameForSetter(Class<?> clazz, Method method) {
		Field field = withSetter(clazz, method);
		return (field == null? null : field.getName());
	}

	/**
	 * 根据set方法获取field
	 * 
	 * @param invocation
	 * @return
	 */
	default Field withSetter(ProxyInvocation<?> invocation) {
		return withSetter(invocation.getProxyClass(), invocation.getMethod());
	}

	/**
	 * 根据set方法获取field
	 * 
	 * @param clazz
	 * @param method
	 * @return
	 */
	public Field withSetter(Class<?> clazz, Method method);

	/**
	 * 获取所有 field
	 * 
	 * @param clazz
	 * @return
	 */
	public Field[] fields(Class<?> clazz);

}
