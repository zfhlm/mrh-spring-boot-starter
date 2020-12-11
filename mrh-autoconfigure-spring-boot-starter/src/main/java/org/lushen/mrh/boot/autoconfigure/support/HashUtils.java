package org.lushen.mrh.boot.autoconfigure.support;

/**
 * 字符串hash工具类
 * 
 * @author hlm
 */
public final class HashUtils {

	private HashUtils() {}

	/**
	 * BKDR hash
	 */
	public static final int bkdrHash(char[] value) {
		if(value == null) {
			throw new IllegalArgumentException("value is null.");
		}
		int hash = 0;
		char val[] = value;
		for (int i = 0; i < value.length; i++) {
			hash = 31 * hash + val[i];
		}
		return hash & 0x7FFFFFFF;
	}

	/**
	 * AP hash
	 */
	public static final int apHash(char[] value) {
		if(value == null) {
			throw new IllegalArgumentException("value is null.");
		}
		int hash = 0;
		for (int i = 0; i < value.length; i++) {
			if ((i & 1) == 0) {
				hash ^= ((hash << 7) ^ value[i] ^ (hash >> 3));
			} else {
				hash ^= (~((hash << 11) ^ value[i] ^ (hash >> 5)));
			}
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * DJB hash
	 */
	public static final int djbHash(char[] value) {
		if(value == null) {
			throw new IllegalArgumentException("value is null.");
		}
		int hash = 5381;
		for (int i = 0; i < value.length; i++) {
			hash += (hash << 5) + value[i];
		}
		return (hash & 0x7FFFFFFF);
	}

}
