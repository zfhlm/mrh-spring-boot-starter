package org.lushen.mrh.boot.dfs.support.file;

/**
 * BMP图片类型
 * 
 * @author hlm
 */
public class PictureBmpMatcher extends AbstractFileMatcher {

	public PictureBmpMatcher() {
		super(new int[]{0x42, 0x4D});
	}

}
