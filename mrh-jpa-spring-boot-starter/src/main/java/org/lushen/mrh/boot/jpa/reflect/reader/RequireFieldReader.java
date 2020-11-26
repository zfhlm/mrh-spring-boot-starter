package org.lushen.mrh.boot.jpa.reflect.reader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.lushen.mrh.boot.jpa.reflect.ClassFieldReader;

/**
 * require field reader
 * 
 * @author helm
 */
public class RequireFieldReader implements ClassFieldReader {

	private final ClassFieldReader classFieldReader;

	public RequireFieldReader(ClassFieldReader classFieldReader) {
		super();
		this.classFieldReader = classFieldReader;
	}

	@Override
	public Field[] fields(Class<?> clazz) {
		return this.classFieldReader.fields(clazz);
	}

	@Override
	public Field withGetter(Class<?> clazz, Method method) {
		Field field = this.classFieldReader.withGetter(clazz, method);
		if(field == null) {
			throw new RuntimeException("No get method can't be found!");
		}
		return field;
	}

	@Override
	public Field withSetter(Class<?> clazz, Method method) {
		Field field = this.classFieldReader.withSetter(clazz, method);
		if(field == null) {
			throw new RuntimeException("No set method can't be found!");
		}
		return field;
	}

}
