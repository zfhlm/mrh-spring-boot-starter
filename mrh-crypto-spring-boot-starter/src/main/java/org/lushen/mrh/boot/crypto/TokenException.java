package org.lushen.mrh.boot.crypto;

/**
 * 令牌处理异常
 * 
 * @author hlm
 */
public class TokenException extends Exception {

	private static final long serialVersionUID = 3156013436270961981L;

	public TokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenException(String message) {
		super(message);
	}

	public TokenException(Throwable cause) {
		super(cause);
	}

	/**
	 * 令牌超时异常
	 * 
	 * @author hlm
	 */
	public static final class TokenExpiredException extends TokenException {

		private static final long serialVersionUID = 4185473174414439671L;

		public TokenExpiredException(Throwable cause) {
			super(cause);
		}

		public TokenExpiredException(String message, Throwable cause) {
			super(message, cause);
		}

		public TokenExpiredException(String message) {
			super(message);
		}

	}

}
