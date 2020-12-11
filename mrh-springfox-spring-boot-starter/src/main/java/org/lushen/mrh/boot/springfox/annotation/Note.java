package org.lushen.mrh.boot.springfox.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 注释注解，编译后失效
 * 
 * @author hlm
 */
@Target({TYPE, FIELD, METHOD})
@Retention(SOURCE)
public @interface Note {

	String value() default "";

}
