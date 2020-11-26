package org.lushen.mrh.sequence.single;

import java.util.UUID;

import org.lushen.mrh.sequence.KeyGenerator;

/**
 * UUID key生成器
 * 
 * @author hlm
 */
public class UuidKeyGenerator implements KeyGenerator {

	@Override
	public String generate() {
		char[] arr = new char[32];
		String uuid = UUID.randomUUID().toString();
		for(int i=0, off = 0; i<uuid.length(); i++) {
			char ch = uuid.charAt(i);
			if(ch != '-') {
				arr[off++] = ch;
			}
		}
		return new String(arr);
	}

}
