package org.lushen.mrh.boot.dfs.support;

/**
 * 图片编解码
 * 
 * @author hlm
 * @param <F>
 * @param <T>
 */
public interface ImageCodec<F, T> extends ImageEncoder<F, T> {

	/**
	 * 解码
	 * 
	 * @param image
	 * @return
	 */
	public F decode(T image);

}
