package org.lushen.mrh.boot.jpa.reflect.reader;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

import org.lushen.mrh.boot.jpa.reflect.ClassFieldReader;

/**
 * 缓存属性读取工具类
 * 
 * @author helm
 */
public class CacheFieldReader implements ClassFieldReader {

	private final Map<AnnotatedElement, Object> cache = new WeakHashMap<AnnotatedElement, Object>();

	private final ClassFieldReader classFieldReader;

	public CacheFieldReader(ClassFieldReader classFieldReader) {
		super();
		this.classFieldReader = classFieldReader;
	}

	@Override
	public Field[] fields(Class<?> clazz) {
		return (Field[]) cache.computeIfAbsent(clazz, e -> this.classFieldReader.fields(clazz));
	}

	@Override
	public Field withGetter(Class<?> clazz, Method method) {
		return (Field) cache.computeIfAbsent(method, e -> this.classFieldReader.withGetter(clazz, method));
	}

	@Override
	public Field withSetter(Class<?> clazz, Method method) {
		return (Field) cache.computeIfAbsent(method, e -> this.classFieldReader.withSetter(clazz, method));
	}

}
