package org.lushen.mrh.boot.autoconfigure.support;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;

/**
 * 异常工具类
 * 
 * @author hlm
 */
public class ThrowableUtils {

	private ThrowableUtils() {}

	/**
	 * 转换异常对象为异常信息
	 * 
	 * @param cause
	 * @return
	 */
	public static final String toString(Throwable cause) {
		if(cause == null) {
			return StringUtils.EMPTY;
		} else {
			StringWriter out = new StringWriter();
			cause.printStackTrace(new PrintWriter(out));
			return out.toString();
		}
	}

	/**
	 * 获取最源头的异常
	 * 
	 * @param cause
	 * @return
	 */
	public static final Throwable lastCause(Throwable cause) {
		Throwable last = cause.getCause();
		return (last == null? cause : lastCause(cause));
	}

}
