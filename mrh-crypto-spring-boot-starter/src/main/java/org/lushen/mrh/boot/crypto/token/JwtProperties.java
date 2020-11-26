package org.lushen.mrh.boot.crypto.token;

import org.springframework.beans.factory.InitializingBean;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * jwt 生成配置
 * 
 * @author hlm
 */
public class JwtProperties implements InitializingBean {

	private SignatureAlgorithm signature = SignatureAlgorithm.HS512;	//加密方式

	private String secret;												//加密秘钥

	private Integer defaultTimeoutSecond = 60*60*2;						//默认超过时间(秒)，默认30分钟

	public SignatureAlgorithm getSignature() {
		return signature;
	}

	public void setSignature(SignatureAlgorithm signature) {
		this.signature = signature;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getDefaultTimeoutSecond() {
		return defaultTimeoutSecond;
	}

	public void setDefaultTimeoutSecond(Integer defaultTimeoutSecond) {
		this.defaultTimeoutSecond = defaultTimeoutSecond;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(this.signature == null) {
			throw new IllegalArgumentException("signature can't be null !");
		}
		if(this.signature == null) {
			throw new IllegalArgumentException("secret can't be null !");
		}
		if(this.signature == null) {
			throw new IllegalArgumentException("defaultTimeoutSecond can't be null !");
		}
		if(this.defaultTimeoutSecond <= 0) {
			throw new IllegalArgumentException("defaultTimeoutSecond can't be less than or equals zero !");
		}
	}

}
