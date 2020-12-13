package org.lushen.mrh.boot.autoconfigure.webmvc.advice;

import org.lushen.mrh.boot.autoconfigure.support.view.GenericResult;
import org.springframework.core.Ordered;
import org.springframework.plugin.core.Plugin;

/**
 * 异常处理插件
 * 
 * @author hlm
 */
public interface ExceptionPlugin extends Plugin<Throwable>, Ordered {

	/**
	 * 插件名称
	 * 
	 * @return
	 */
	default String getName() {
		return getClass().getName();
	}

	/**
	 * 插件权重
	 */
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

}
