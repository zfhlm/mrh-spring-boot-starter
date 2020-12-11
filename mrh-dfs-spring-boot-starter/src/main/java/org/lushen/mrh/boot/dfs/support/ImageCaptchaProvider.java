package org.lushen.mrh.boot.dfs.support;

/**
 * 图片验证码生成器
 * 
 * @author hlm
 */
public interface ImageCaptchaProvider {

	/**
	 * 生成验证码
	 * 
	 * @return
	 */
	public ICaptcha create();

	/**
	 * 验证码信息对象
	 * 
	 * @author hlm
	 */
	public static class ICaptcha {

		private final byte[] buffer;	// 图片buffer

		private final String text;		// 图片包含文本信息

		public ICaptcha(byte[] buffer, String text) {
			super();
			this.buffer = buffer;
			this.text = text;
		}

		public byte[] getBuffer() {
			return buffer;
		}

		public String getText() {
			return text;
		}

	}

}
