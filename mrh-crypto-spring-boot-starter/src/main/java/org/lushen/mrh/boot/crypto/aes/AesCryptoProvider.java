package org.lushen.mrh.boot.crypto.aes;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.crypto.CryptoException;
import org.lushen.mrh.boot.crypto.CryptoProvider;

/**
 * AES双向加密工具类
 * 
 * @author hlm
 */
public class AesCryptoProvider implements CryptoProvider {

	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	private static final String SECURE_RANDOM = "SHA1PRNG";

	private static final String KEY_ALGORITHM = "AES";

	private byte[] key;

	public AesCryptoProvider(AesProperties properties) {
		super();
		if(properties == null) {
			throw new IllegalArgumentException("properties can't be null !");
		}
		if(StringUtils.isBlank(properties.getKey())) {
			throw new IllegalArgumentException("key can't be null !");
		}
		this.key = properties.getKey().getBytes();
	}

	@Override
	public byte[] encrypt(byte[] buffer) throws CryptoException {

		if(buffer == null) {
			throw new CryptoException("buffer is null.");
		}

		try {

			KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM);
			secureRandom.setSeed(this.key);
			keyGenerator.init(128, secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
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

			KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM);
			secureRandom.setSeed(this.key);
			keyGenerator.init(128, secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] result = cipher.doFinal(Base64.decodeBase64(buffer));

			return result;

		} catch (Exception e) {

			throw new CryptoException(e.getMessage(), e);

		}

	}

}
