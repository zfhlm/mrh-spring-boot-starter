package org.lushen.mrh.boot.autoconfigure.support.error;

import org.lushen.mrh.boot.autoconfigure.support.error.GenericStatus;

/**
 * 通用荷载异常定义
 * 
 * @author hlm
 */
public class GenericPayloadException extends GenericException {

	private static final long serialVersionUID = 293171710710195664L;

	private Object payload;

	public GenericPayloadException(GenericStatus status, Throwable cause) {
		super(status, cause);
	}

	public GenericPayloadException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericPayloadException(String message) {
		super(message);
	}

	public GenericPayloadException(GenericStatus status, Object payload, Throwable cause) {
		super(status, cause);
		this.payload = payload;
	}

	public GenericPayloadException(String message, Object payload, Throwable cause) {
		super(message, cause);
		this.payload = payload;
	}

	public GenericPayloadException(String message, Object payload) {
		super(message);
		this.payload = payload;
	}

	public Object getPayload() {
		return payload;
	}

}
