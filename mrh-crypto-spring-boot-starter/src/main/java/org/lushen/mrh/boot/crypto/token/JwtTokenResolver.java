package org.lushen.mrh.boot.crypto.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.crypto.TokenException;
import org.lushen.mrh.boot.crypto.TokenObject;
import org.lushen.mrh.boot.crypto.TokenResolver;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * jwt 令牌处理器
 * 
 * @author hlm
 */
public class JwtTokenResolver implements TokenResolver, InitializingBean {

	private static final String JWT_LOCADATE = "Jwt-Locadate";

	private static final String JWT_MESSAGE = "Jwt-Message";

	private ObjectMapper objectMapper = new ObjectMapper();

	private JwtProperties properties;

	public JwtTokenResolver(JwtProperties properties) {
		super();
		this.properties = properties;
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

			// 超时时间
			if(tokenObject.getExpires() == null) {
				tokenObject.setExpires(new Date(System.currentTimeMillis() + properties.getDefaultTimeoutSecond()*1000L));
			}

			// 令牌信息
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put(JWT_LOCADATE, System.currentTimeMillis());
			claims.put(JWT_MESSAGE, this.objectMapper.writeValueAsString(tokenObject));

			// 创建令牌
			JwtBuilder builder = Jwts.builder();
			builder.setClaims(claims);
			builder.setSubject(tokenObject.getSubject());
			builder.setExpiration(tokenObject.getExpires());
			builder.signWith(properties.getSignature(), properties.getSecret());
			String token = builder.compact();

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

			// 解析令牌
			JwtParser jwtParser = Jwts.parser().setSigningKey(properties.getSecret());
			Claims claims = jwtParser.parseClaimsJws(token).getBody();

			// 解析令牌信息
			return this.objectMapper.readValue((String)claims.get(JWT_MESSAGE), clazz);

		} catch(ExpiredJwtException e) {

			throw new TokenException.TokenExpiredException(e);

		} catch(Exception e) {

			throw new TokenException(e);

		}

	}

}
