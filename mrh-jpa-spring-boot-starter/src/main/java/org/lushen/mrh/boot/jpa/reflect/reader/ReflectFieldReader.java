package org.lushen.mrh.boot.jpa.reflect.reader;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.lushen.mrh.boot.jpa.reflect.ClassFieldReader;

/**
 * 反射字段属性读取工具类
 * 
 * @author helm
 */
public class ReflectFieldReader implements ClassFieldReader {

	@Override
	public Field[] fields(Class<?> clazz) {
		if(clazz == null) {
			throw new IllegalArgumentException("Class can not be null!");
		}
		return FieldUtils.getAllFields(clazz);
	}

	@Override
	public Field withGetter(Class<?> clazz, Method method) {
		return field(clazz, method, descriptor -> descriptor.getReadMethod());
	}

	@Override
	public Field withSetter(Class<?> clazz, Method method) {
		return field(clazz, method, descriptor -> descriptor.getWriteMethod());
	}

	private Field field(Class<?> clazz, Method method, Function<PropertyDescriptor, Method> func) {
		if(clazz == null) {
			throw new IllegalArgumentException("Class can not be null!");
		}
		if(method == null) {
			throw new IllegalArgumentException("Method can not be null!");
		}
		for(Field field : fields(clazz)) {
			try {
				PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
				Method currentMethod = func.apply(descriptor);
				if(currentMethod != null && StringUtils.equals(method.toGenericString(), currentMethod.toGenericString())) {
					return field;
				}
			} catch (Exception ex) {}
		}
		return null;
	}

}
