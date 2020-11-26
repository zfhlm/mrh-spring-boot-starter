package org.lushen.mrh.boot.crypto;

/**
 * 加解密接口
 * 
 * @author hlm
 */
public interface CryptoProvider {

	/**
	 * 加密
	 * 
	 * @param text
	 * @return
	 * @throws CryptoException
	 */
	default String encrypt(String text) throws CryptoException {
		if(text == null) {
			throw new CryptoException("text is null.");
		}
		byte[] buffer = encrypt(text.getBytes());
		return (buffer == null? null : new String(buffer));
	}

	/**
	 * 加密
	 * 
	 * @param buffer
	 * @return
	 * @throws CryptoException
	 */
	public byte[] encrypt(byte[] buffer) throws CryptoException;

	/**
	 * 解密
	 * 
	 * @param text
	 * @return
	 * @throws CryptoException
	 */
	default String decrypt(String text) throws CryptoException {
		if(text == null) {
			throw new CryptoException("text is null.");
		}
		byte[] buffer = decrypt(text.getBytes());
		return (buffer == null? null : new String(buffer));
	}

	/**
	 * 解密
	 * 
	 * @param buffer
	 * @return
	 * @throws CryptoException
	 */
	public byte[] decrypt(byte[] buffer) throws CryptoException;

}
