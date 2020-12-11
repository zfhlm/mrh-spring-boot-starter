package org.lushen.mrh.boot.dfs.support.file;

/**
 * zip压缩文件类型
 * 
 * @author hlm
 */
public class CompressZipMatcher extends AbstractFileMatcher {

	public CompressZipMatcher() {
		super(new int[]{0x50, 0x4B, 0x03, 0x04});
	}

}
