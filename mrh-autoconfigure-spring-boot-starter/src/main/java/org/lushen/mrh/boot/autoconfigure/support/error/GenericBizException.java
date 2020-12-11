package org.lushen.mrh.boot.autoconfigure.support.error;

/**
 * 通用业务异常
 * 
 * @author hlm
 */
public class GenericBizException extends GenericPayloadException {

	private static final long serialVersionUID = -416295988413297932L;

	public GenericBizException(GenericStatus status, Throwable cause) {
		super(status, cause);
	}

	public GenericBizException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericBizException(String message) {
		super(message);
	}

	public GenericBizException(GenericStatus status, Object payload, Throwable cause) {
		super(status, payload, cause);
	}

	public GenericBizException(String message, Object payload, Throwable cause) {
		super(message, payload, cause);
	}

	public GenericBizException(String message, Object payload) {
		super(message, payload);
	}

}
