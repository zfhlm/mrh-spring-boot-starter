package org.lushen.mrh.boot.crypto.token;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.crypto.CryptoProvider;
import org.lushen.mrh.boot.crypto.TokenException;
import org.lushen.mrh.boot.crypto.TokenObject;
import org.lushen.mrh.boot.crypto.TokenResolver;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 双向加解密令牌处理器
 * 
 * @author hlm
 */
public class CryptoTokenResolver implements TokenResolver, InitializingBean {

	private ObjectMapper objectMapper = new ObjectMapper();

	private CryptoProvider cryptoProvider;

	public CryptoTokenResolver(CryptoProvider cryptoProvider) {
		super();
		this.cryptoProvider = cryptoProvider;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public <T extends TokenObject> String create(T tokenObject) throws TokenException {

		if(tokenObject == null) {
			throw new TokenException("token object is null.");
		}

		try {

			String message = this.objectMapper.writeValueAsString(tokenObject);
			String token = this.cryptoProvider.encrypt(message);

			return token;

		} catch(Exception e) {

			throw new TokenException(e);

		}

	}

	@Override
	public <T extends TokenObject> T parse(Class<T> clazz, String token) throws TokenException {

		if(clazz == null) {
			throw new TokenException("credentialsClass is null.");
		}
		if(StringUtils.isBlank(token)) {
			throw new TokenException("json web token is blank.");
		}

		try {

			String message = this.cryptoProvider.decrypt(token);
			T tokenObject = this.objectMapper.readValue(message, clazz);

			if(tokenObject.getExpires().before(new Date())) {
				throw new TokenException("token is expired!");
			}

			return tokenObject;

		} catch(TokenException e) {

			throw e;

		} catch(Exception e) {

			throw new TokenException(e);

		}

	}

}
