package org.lushen.mrh.boot.autoconfigure.support.error;

import java.io.Serializable;

/**
 * 状态码对象
 * 
 * @author hlm
 */
public final class GenericStatus implements Serializable {

	public static final GenericStatus BUSINESS = new GenericStatus(-1, "请求服务系统繁忙，请稍后再试!");

	public static final GenericStatus SUCCESS = new GenericStatus(0, "请求服务成功!");

	public static final GenericStatus BAD_REQUEST = new GenericStatus(400, "请求服务方式错误!");

	public static final GenericStatus FORBIDDEN = new GenericStatus(403, "请求服务用户权限不足!");

	public static final GenericStatus NOT_FOUND = new GenericStatus(404, "请求服务路径不存在!");

	public static final GenericStatus METHOD_NOT_ALLOW = new GenericStatus(405, "请求服务方法错误!");

	public static final GenericStatus NOT_ACCEPTABLE = new GenericStatus(406, "请求主体内容错误!");

	public static final GenericStatus REQUEST_TIMEOUT = new GenericStatus(408, "请求服务超时!");

	public static final GenericStatus ERROR = new GenericStatus(500, "请求服务系统错误，请稍后再试!");

	public static final GenericStatus BAD_GATEWAY = new GenericStatus(502, "请求服务网关错误!");

	public static final GenericStatus SERVICE_UNAVAILABLE = new GenericStatus(503, "请求服务不可用!");

	public static final GenericStatus GATEWAY_TIMEOUT = new GenericStatus(504, "请求服务不可用!");

	public static final GenericStatus NOT_LOGIN = new GenericStatus(601, "未登录用户!");

	public static final GenericStatus EXPIRED_LOGIN = new GenericStatus(602, "登录信息已过期!");

	public static final GenericStatus NOT_AVAILABLE = new GenericStatus(603, "请求服务暂时不可用!");

	public static final GenericStatus VALID_PARAM_ERROR = new GenericStatus(604, "参数验证错误!");

	public static final GenericStatus BIND_PARAM_ERROR = new GenericStatus(604, "参数绑定错误!");

	private static final long serialVersionUID = 5771739795772431871L;

	private final int errcode;

	private final String errmsg;

	public GenericStatus(int errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[errcode=");
		builder.append(errcode);
		builder.append(", errmsg=");
		builder.append(errmsg);
		builder.append("]");
		return builder.toString();
	}

}
