package org.lushen.mrh.boot.crypto.md5;

import java.security.MessageDigest;

import org.lushen.mrh.boot.crypto.CryptoException;
import org.lushen.mrh.boot.crypto.CryptoProvider;

/**
 * MD5加密实现
 * 
 * @author hlm
 */
public class Md5CryptoProvider implements CryptoProvider {

	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	@Override
	public byte[] encrypt(byte[] buffer) throws CryptoException {

		if(buffer == null) {
			throw new IllegalArgumentException("buffer is null.");
		}

		try {

			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(buffer);

			byte[] bytes = digest.digest();
			char[] chars = new char[bytes.length * 2];
			for (int i=0, k=0 ; i < bytes.length; i++) {
				byte byte0 = bytes[i];
				chars[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
				chars[k++] = HEX_DIGITS[byte0 & 0xf];
			}

			return new String(chars).getBytes();

		} catch (Exception e) {

			throw new CryptoException(e.getMessage(), e);

		}

	}

	@Override
	public byte[] decrypt(byte[] buffer) throws CryptoException {
		throw new CryptoException("Not support method.");
	}

}
