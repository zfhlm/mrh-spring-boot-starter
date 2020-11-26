package org.lushen.mrh.boot.crypto.des;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.crypto.CryptoException;
import org.lushen.mrh.boot.crypto.CryptoProvider;

/**
 * des 加解密工具
 * 
 * @author hlm
 */
public class DesCryptoProvider implements CryptoProvider {

	private final static String DES = "DES";

	private byte[] key;

	public DesCryptoProvider(DesProperties properties) {
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
		this.key = StringUtils.rightPad(properties.getKey(), 24, '0').getBytes();
	}

	@Override
	public byte[] encrypt(byte[] buffer) throws CryptoException {

		if(buffer == null) {
			throw new CryptoException("buffer is null.");
		}

		try {

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(new DESKeySpec(this.key));

			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
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

			DESKeySpec dks = new DESKeySpec(this.key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
			byte[] bytes = cipher.doFinal(Base64.decodeBase64(buffer));

			return bytes;

		} catch (Exception e) {

			throw new CryptoException(e.getMessage(), e);

		}

	}

}
