package org.lushen.mrh.boot.autoconfigure.support;

import java.io.Closeable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * {@link Closeable} 工具类
 * 
 * @author hlm
 */
public final class CloseableUtils {

	private static final Log log = LogFactory.getLog(CloseableUtils.class);

	private CloseableUtils() {}

	/**
	 * 关闭资源
	 * 
	 * @param arg
	 */
	public static final void close(Closeable arg) {
		if(arg != null) {
			try {
				arg.close();
			} catch (Throwable e) {
				log.error(e);
			}
		}
	}

	/**
	 * 关闭资源
	 * 
	 * @param arg1
	 * @param arg2
	 */
	public static final void close(Closeable arg1, Closeable arg2) {
		close(arg1, arg2);
	}

	/**
	 * 关闭资源
	 * 
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public static final void close(Closeable arg1, Closeable arg2, Closeable arg3) {
		close(arg1, arg2, arg3);
	}

	/**
	 * 关闭资源
	 * 
	 * @param args
	 */
	public static final void close(Closeable... args) {
		if(args == null || args.length == 0) {
			return;
		}
		for(Closeable arg : args) {
			close(arg);
		}
	}

}
