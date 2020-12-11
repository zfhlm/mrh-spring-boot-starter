package org.lushen.mrh.boot.dfs.support.file;

/**
 * git图片类型
 * 
 * @author hlm
 */
public class PictureGifMatcher extends AbstractFileMatcher {

	public PictureGifMatcher() {
		super(new int[]{0x47, 0x49, 0x46, 0x38});
	}

}
