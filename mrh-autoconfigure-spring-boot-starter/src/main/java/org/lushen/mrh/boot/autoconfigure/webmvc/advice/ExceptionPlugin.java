package org.lushen.mrh.boot.autoconfigure.webmvc.advice;

import org.lushen.mrh.support.generic.view.GenericResult;
import org.springframework.core.Ordered;
import org.springframework.plugin.core.Plugin;

/**
 * 异常处理插件
 * 
 * @author hlm
 */
public interface ExceptionPlugin extends Plugin<Throwable>, Ordered {

	@Override
	default int getOrder() {
		return 0;
	}

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
