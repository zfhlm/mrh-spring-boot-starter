package org.lushen.mrh.boot.autoconfigure.support;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 远程ip地址工具类
 * 
 * @author hlm
 */
public class InetAddressUtils {

	private static final String UNKNOWN = "unknown";

	private static final String X_FORWARDED_FOR = "x-forwarded-for";

	private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";

	private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

	private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

	private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

	private static final String X_REAL_IP = "X-Real-IP";

	/**
	 * 获取远程客户端IP真实地址
	 * 
	 * @param request
	 * @return
	 */
	public static final String getClientIp(HttpServletRequest request) {
		return getClientIp(name -> request.getHeader(name), () -> request.getRemoteAddr());
	}

	/**
	 * 获取远程客户端IP真实地址
	 * 
	 * @param headerForName
	 * @param headerForDefault
	 * @return
	 */
	public static final String getClientIp(Function<String, String> headerForName, Supplier<String> headerForDefault) {

		String ip = headerForName.apply(X_FORWARDED_FOR);
		if (StringUtils.isNotBlank(ip) && ! StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
			int index = ip.indexOf(',');
			if(index != -1) {
				return ip.substring(0, index);
			}
		}

		ip = headerForName.apply(PROXY_CLIENT_IP);
		if(StringUtils.isNotBlank(ip) && ! StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
			return ip;
		}

		ip = headerForName.apply(WL_PROXY_CLIENT_IP);
		if(StringUtils.isNotBlank(ip) && ! StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
			return ip;
		}

		ip = headerForName.apply(HTTP_CLIENT_IP);
		if(StringUtils.isNotBlank(ip) && ! StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
			return ip;
		}

		ip = headerForName.apply(HTTP_X_FORWARDED_FOR);
		if(StringUtils.isNotBlank(ip) && ! StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
			return ip;
		}

		ip = headerForName.apply(X_REAL_IP);
		if(StringUtils.isNotBlank(ip) && ! StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
			return ip;
		}

		return headerForDefault.get();
	}

}
