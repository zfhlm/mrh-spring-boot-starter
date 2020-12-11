package org.lushen.mrh.boot.dfs.support.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.lushen.mrh.boot.dfs.support.FileTypeMatcher;

/**
 * 读取文件字节进行匹配，抽离匹配方法实现
 * 
 * @author hlm
 */
public abstract class AbstractFileMatcher implements FileTypeMatcher {

	private int[] signature;

	private int offset;

	private int len;

	protected AbstractFileMatcher(int[] signature) {
		this(signature, 0, signature.length);
	}

	protected AbstractFileMatcher(int[] signature, int offset, int len) {
		super();
		this.signature = signature;
		this.offset = offset;
		this.len = len;
	}

	@Override
	public boolean matches(File file) {
		if(this.len < 0) {
			return false;
		} else if(this.len == 0) {
			return matching(new byte[0]);
		} else {
			try(RandomAccessFile accessFile = new RandomAccessFile(file, "r")) {
				byte[] signature = new byte[this.len];
				accessFile.read(signature, this.offset, this.len);
				return matching(signature);
			} catch (Exception e) {
				return false;
			}
		}
	}

	@Override
	public boolean matches(byte[] buffer) {
		if(buffer == null || buffer.length < this.len) {
			return false;
		} else {
			return matching(Arrays.copyOfRange(buffer, this.offset, this.offset+this.len));
		}
	}

	private boolean matching(byte[] signature) {
		if(signature.length != this.signature.length) {
			return false;
		}
		for(int index=0; index<signature.length; index++) {
			if((byte)this.signature[index] != signature[index]) {
				return false;
			}
		}
		return true;
	}

}
