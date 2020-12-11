package org.lushen.mrh.boot.dfs.support.file;

/**
 * rar压缩文件类型
 * 
 * @author hlm
 */
public class CompressRarMatcher extends AbstractFileMatcher {

	public CompressRarMatcher() {
		super(new int[]{0x52, 0x61, 0x72, 0x21});
	}

}
