package org.lushen.mrh.boot.dfs.support.file;

/**
 * tif图片类型
 * 
 * @author hlm
 */
public class PictureTiffMatcher extends AbstractFileMatcher {

	public PictureTiffMatcher() {
		super(new int[]{0x49, 0x49, 0x2A, 0x00});
	}

}
