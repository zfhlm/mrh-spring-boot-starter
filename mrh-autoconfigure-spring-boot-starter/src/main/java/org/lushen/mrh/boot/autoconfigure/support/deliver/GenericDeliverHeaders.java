package org.lushen.mrh.boot.autoconfigure.support.deliver;

import org.apache.commons.lang3.StringUtils;

/**
 * 自定义请求响应 header
 * 
 * @author hlm
 */
public class GenericDeliverHeaders {

	private GenericDeliverHeaders() {}

	/**
	 * 请求头名称前缀
	 */
	public static final String REQUEST_DELIVER_HEADER_PREFIX = "Request-Deliver-";

	/**
	 * 响应头名称前缀
	 */
	public static final String RESPONSE_DELIVER_HEADER_PREFIX = "Response-Deliver-";

	/**
	 * 是否请求头
	 * 
	 * @param name
	 * @return
	 */
	public static final boolean isRequestDeliverHeader(String name) {
		return StringUtils.startsWithIgnoreCase(name, REQUEST_DELIVER_HEADER_PREFIX);
	}

	/**
	 * 是否响应头
	 * 
	 * @param name
	 * @return
	 */
	public static final boolean isResponseDeliverHeader(String name) {
		return StringUtils.startsWithIgnoreCase(name, RESPONSE_DELIVER_HEADER_PREFIX);
	}

}
