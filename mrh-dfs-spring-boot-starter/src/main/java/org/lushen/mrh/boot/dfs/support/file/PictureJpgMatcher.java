package org.lushen.mrh.boot.dfs.support.file;

/**
 * JPG图片类型
 * 
 * @author hlm
 */
public class PictureJpgMatcher extends AbstractFileMatcher {

	public PictureJpgMatcher() {
		super(new int[]{0xFF, 0xD8, 0xFF});
	}

}
