package org.lushen.mrh.boot.crypto.none;

import org.lushen.mrh.boot.crypto.CryptoException;
import org.lushen.mrh.boot.crypto.CryptoProvider;

/**
 * 无加解密实现
 * 
 * @author hlm
 */
public class NoneCryptoProvider implements CryptoProvider {

	@Override
	public byte[] encrypt(byte[] buffer) throws CryptoException {
		return buffer;
	}

	@Override
	public byte[] decrypt(byte[] buffer) throws CryptoException {
		return buffer;
	}

}
