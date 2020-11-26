package org.lushen.mrh.boot.jpa.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * method reader
 * 
 * @author helm
 */
public interface ClassMethodReader {

	public Method[] methods(Class<?> clazz);

	public Method[] readMethods(Class<?> clazz);

	public Method[] writeMethods(Class<?> clazz);

	public Method readMethod(Class<?> clazz, Field field);

	public Method writeMethod(Class<?> clazz, Field field);

}
