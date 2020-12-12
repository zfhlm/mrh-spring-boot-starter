package org.lushen.mrh.boot.autoconfigure.support.runtime;

/**
 * 获取当前运行时启动类
 * 
 * @author hlm
 */
public class JavaCommandLoader implements RuntimeLoader<Class<?>> {

	private static final String SUN_JAVA_COMMAND = "sun.java.command";

	@Override
	public Class<?> load() throws Exception {
		String command = System.getProperty(SUN_JAVA_COMMAND);
		if(command == null) {
			throw new RuntimeException("No value for system property " + SUN_JAVA_COMMAND);
		}
		return Class.forName(command);
	}

}
