package org.lushen.mrh.boot.dfs.support.file;

/**
 * PNG图片类型
 * 
 * @author hlm
 */
public class PicturePngMatcher extends AbstractFileMatcher {

	public PicturePngMatcher() {
		super(new int[]{0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A});
	}

}
