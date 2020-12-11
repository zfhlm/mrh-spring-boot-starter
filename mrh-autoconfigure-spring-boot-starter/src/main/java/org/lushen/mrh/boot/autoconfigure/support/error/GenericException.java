package org.lushen.mrh.boot.autoconfigure.support.error;

import static org.lushen.mrh.boot.autoconfigure.support.error.GenericStatus.ERROR;

/**
 * 通用异常定义
 * 
 * @author hlm
 */
public class GenericException extends RuntimeException {

	private static final long serialVersionUID = -5015538581625910536L;

	private GenericStatus status;

	public GenericException(String message, Throwable cause) {
		super(message, cause);
		this.status = new GenericStatus(ERROR.getErrcode(), message);
	}

	public GenericException(String message) {
		super(message);
		this.status = new GenericStatus(ERROR.getErrcode(), message);
	}

	public GenericException(GenericStatus status, Throwable cause) {
		super(status.getErrmsg(), cause);
		this.status = status;
	}

	public GenericStatus getStatus() {
		return status;
	}

}
