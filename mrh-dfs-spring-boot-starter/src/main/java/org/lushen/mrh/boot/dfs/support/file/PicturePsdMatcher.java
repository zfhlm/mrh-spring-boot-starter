package org.lushen.mrh.boot.dfs.support.file;

/**
 * psd图片类型
 * 
 * @author hlm
 */
public class PicturePsdMatcher extends AbstractFileMatcher {

	public PicturePsdMatcher() {
		super(new int[]{0x38, 0x42, 0x50, 0x53});
	}

}
