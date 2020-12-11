package org.lushen.mrh.boot.dfs.support;

/**
 * 图片编码
 * 
 * @author hlm
 * @param <F>
 * @param <T>
 */
public interface ImageEncoder<F, T> {

	/**
	 * 编码
	 * 
	 * @param image
	 * @return
	 */
	public T encode(F image);

}
