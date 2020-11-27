package org.lushen.mrh.boot.autoconfigure;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.core.env.Environment;

/**
 * Spring Environment holder
 * 
 * @author hlm
 */
public class ApplicationEnvironment {

	private static final AtomicReference<Environment> environmentHolder = new AtomicReference<Environment>();

	/**
	 * 获取 Spring Environment 
	 * 
	 * @return
	 */
	public static final Environment getEnvironment() {
		return environmentHolder.get();
	}

	/**
	 * 设置 Spring Environment 
	 * 
	 * @param environment
	 */
	static final void setEnvironment(Environment environment) {
		environmentHolder.set(environment);
	}

}
