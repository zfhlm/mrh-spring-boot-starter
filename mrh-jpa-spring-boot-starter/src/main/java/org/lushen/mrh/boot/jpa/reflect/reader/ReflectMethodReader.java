package org.lushen.mrh.boot.jpa.reflect.reader;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import org.lushen.mrh.boot.jpa.reflect.ClassMethodReader;

/**
 * 反射读取方法工具
 * 
 * @author helm
 */
public class ReflectMethodReader implements ClassMethodReader {

	@Override
	public Method[] methods(Class<?> clazz) {
		if(clazz == null) {
			throw new IllegalArgumentException("Class can not be null!");
		}
		return clazz.getMethods();
	}

	@Override
	public Method[] readMethods(Class<?> clazz) {
		return methods(clazz, descriptor -> descriptor.getReadMethod());
	}

	@Override
	public Method[] writeMethods(Class<?> clazz) {
		return methods(clazz, descriptor -> descriptor.getWriteMethod());
	}

	@Override
	public Method readMethod(Class<?> clazz, Field field) {
		try {
			return new PropertyDescriptor(field.getName(), clazz).getReadMethod();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Method writeMethod(Class<?> clazz, Field field) {
		try {
			return new PropertyDescriptor(field.getName(), clazz).getWriteMethod();
		} catch (Exception e) {
			return null;
		}
	}

	private Method[] methods(Class<?> clazz, Function<PropertyDescriptor, Method> func) {
		if(clazz == null) {
			throw new IllegalArgumentException("Class can not be null!");
		}
		return Arrays.stream(clazz.getDeclaredFields()).map(field -> {
			try {
				return func.apply(new PropertyDescriptor(field.getName(), clazz));
			} catch (Exception ex) {
				return null;
			}
		}).filter(Objects::nonNull).toArray(len -> new Method[len]);
	}

}
