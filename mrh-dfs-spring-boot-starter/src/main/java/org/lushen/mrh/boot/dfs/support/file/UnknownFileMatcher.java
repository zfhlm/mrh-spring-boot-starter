package org.lushen.mrh.boot.dfs.support.file;

import java.io.File;

import org.lushen.mrh.boot.dfs.support.FileTypeMatcher;

/**
 * 未知文件类型
 * 
 * @author hlm
 */
public class UnknownFileMatcher implements FileTypeMatcher {

	@Override
	public boolean matches(File file) {
		return false;
	}

	@Override
	public boolean matches(byte[] buffer) {
		return false;
	}

}
