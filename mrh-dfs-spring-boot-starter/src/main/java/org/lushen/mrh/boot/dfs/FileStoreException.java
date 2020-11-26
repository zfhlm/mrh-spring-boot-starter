package org.lushen.mrh.boot.dfs;

/**
 * 文件存储异常
 * 
 * @author hlm
 */
public class FileStoreException extends Exception {

	private static final long serialVersionUID = -8670285069333823114L;

	public FileStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileStoreException(String message) {
		super(message);
	}

	public FileStoreException(Throwable cause) {
		super(cause);
	}
}
