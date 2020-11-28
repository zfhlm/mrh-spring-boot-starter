package org.lushen.mrh.boot.autoconfigure.web;

import org.lushen.mrh.support.generic.view.GenericResult;
import org.springframework.plugin.core.Plugin;

/**
 * 异常处理插件
 * 
 * @author hlm
 */
public interface ErrorHandlerPlugin extends Plugin<Throwable> {

	/**
	 * 转换异常为通用视图对象
	 * 
	 * @param cause
	 * @return
	 */
	public GenericResult handle(Throwable cause);

	/**
	 * 获取插件名称
	 * 
	 * @return
	 */
	default String nameForPlugin() {
		return getClass().getName();
	}

}
