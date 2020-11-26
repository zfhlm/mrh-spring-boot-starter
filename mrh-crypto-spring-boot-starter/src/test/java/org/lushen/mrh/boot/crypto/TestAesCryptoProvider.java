package org.lushen.mrh.boot.crypto;

import org.lushen.mrh.boot.crypto.aes.AesCryptoProvider;
import org.lushen.mrh.boot.crypto.aes.AesProperties;

public class TestAesCryptoProvider {

	public static void main(String[] args) throws Exception {

		AesProperties properties = new AesProperties();
		properties.setKey("123456");
		AesCryptoProvider provider = new AesCryptoProvider(properties);

		String str = "abc";
		String str2 = provider.encrypt(str);
		
		System.out.println(str2);
		System.out.println(provider.decrypt(str2));

	}

}
