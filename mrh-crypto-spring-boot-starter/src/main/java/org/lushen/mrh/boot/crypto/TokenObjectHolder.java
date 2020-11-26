package org.lushen.mrh.boot.crypto;

import java.util.Optional;

/**
 * 令牌对象线程容器
 * 
 * @author hlm
 */
@SuppressWarnings("unchecked")
public final class TokenObjectHolder {

	private TokenObjectHolder() {
		super();
	}

	private static final ThreadLocal<TokenObject> credentialsHolder = new ThreadLocal<TokenObject>();

	/**
	 * 保存令牌对象到上下文
	 */
	public static final <T extends TokenObject> void set(T credentials) {
		credentialsHolder.set(credentials);
	}

	/**
	 * 获取上下文令牌对象
	 */
	public static final <T extends TokenObject> Optional<T> get() {
		return Optional.ofNullable((T)credentialsHolder.get());
	}

	/**
	 * 清除上下文令牌对象
	 */
	public static final void clear() {
		credentialsHolder.remove();
	}

}
