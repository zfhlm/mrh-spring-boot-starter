package org.lushen.mrh.boot.crypto.des3;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.crypto.CryptoException;
import org.lushen.mrh.boot.crypto.CryptoProvider;

/**
 * 3DES 双向加解密工具
 * 
 * @author hlm
 */
public class Des3CryptoProvider implements CryptoProvider {

	private static final String KEY_ALGORITHM = "desede";

	private static final String DEFAULT_CIPHER_ALGORITHM = "desede/CBC/PKCS5Padding";

	private byte[] key;

	private byte[] iv;

	public Des3CryptoProvider(Des3Properties properties) {
		super();
		if(properties == null) {
			throw new IllegalArgumentException("properties can't be null !");
		}
		if(StringUtils.isBlank(properties.getKey())) {
			throw new IllegalArgumentException("key can't be null !");
		}
		if(StringUtils.length(properties.getKey()) > 24) {
			throw new IllegalArgumentException("key length can't be more than 24 !");
		}
		if(StringUtils.isBlank(properties.getIv())) {
			throw new IllegalArgumentException("iv can't be null !");
		}
		if(StringUtils.length(properties.getIv()) > 8) {
			throw new IllegalArgumentException("iv length can't be more than 24 !");
		}
		this.key = StringUtils.rightPad(properties.getKey(), 24, '0').getBytes();
		this.iv = StringUtils.rightPad(properties.getIv(), 8, '0').getBytes();
	}

	@Override
	public byte[] encrypt(byte[] buffer) throws CryptoException {

		if(buffer == null) {
			throw new CryptoException("buffer is null.");
		}

		try {

			DESedeKeySpec spec = new DESedeKeySpec(this.key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			Key deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			IvParameterSpec ips = new IvParameterSpec(this.iv);
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] bytes = cipher.doFinal(buffer);

			return Base64.encodeBase64(bytes);

		} catch (Exception e) {

			throw new CryptoException(e.getMessage(), e);

		}

	}

	@Override
	public byte[] decrypt(byte[] buffer) throws CryptoException {

		if(buffer == null) {
			throw new CryptoException("buffer is null.");
		}

		try {

			DESedeKeySpec spec = new DESedeKeySpec(this.key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			Key deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			IvParameterSpec ips = new IvParameterSpec(this.iv);
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			byte[] bytes = cipher.doFinal(Base64.decodeBase64(buffer));

			return bytes;

		} catch(Exception e) {

			throw new CryptoException(e.getMessage(), e);

		}
	}

}
