package org.lushen.mrh.boot.crypto.aes;

/**
 * AES加密配置
 * 
 * @author hlm
 */
public class AesProperties {

	private String key;	//加解密秘钥

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
