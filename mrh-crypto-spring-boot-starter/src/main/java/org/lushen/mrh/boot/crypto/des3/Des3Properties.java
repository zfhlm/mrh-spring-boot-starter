package org.lushen.mrh.boot.crypto.des3;

import org.lushen.mrh.boot.crypto.des.DesProperties;

/**
 * 3DES加密配置
 * 
 * @author hlm
 */
public class Des3Properties extends DesProperties {

	private String iv;	//加解密向量

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

}
