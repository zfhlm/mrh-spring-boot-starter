package org.lushen.mrh.boot.crypto;

import org.lushen.mrh.boot.crypto.des.DesCryptoProvider;
import org.lushen.mrh.boot.crypto.des.DesProperties;

public class TestDesCryptoProvider {

	public static void main(String[] args) throws Exception {

		DesProperties properties = new DesProperties();
		properties.setKey("123456");
		DesCryptoProvider provider = new DesCryptoProvider(properties);

		String str = "abc";
		String str2 = provider.encrypt(str);
		System.out.println(str2);
		System.out.println(provider.decrypt(str2));

	}

}
