package org.lushen.mrh.boot.crypto;

import org.lushen.mrh.boot.crypto.des3.Des3CryptoProvider;
import org.lushen.mrh.boot.crypto.des3.Des3Properties;

public class TestDes3CryptoProvider {

	public static void main(String[] args) throws Exception {

		Des3Properties properties = new Des3Properties();
		properties.setIv("123456");
		properties.setKey("123456");
		Des3CryptoProvider provider = new Des3CryptoProvider(properties);

		String str = "abc";
		String str2 = provider.encrypt(str);
		System.out.println(str2);
		System.out.println(provider.decrypt(str2));

	}

}
