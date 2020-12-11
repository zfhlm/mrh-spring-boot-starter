package org.lushen.mrh.boot.autoconfigure.support.deliver;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.EnumerationUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * deliver header 工具类
 * 
 * @author hlm
 */
public final class GenericDeliverUtils {

	private GenericDeliverUtils() {}

	/**
	 * 获取所有 deliver 请求头
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, Collection<String>> collectDeliverHeader(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		return collectDeliverHeader(headers.keySet().iterator(), name -> headers.getValuesAsList(name));
	}

	/**
	 * 获取所有 deliver 请求头
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, Collection<String>> collectDeliverHeader(WebRequest request) {
		return collectDeliverHeader(request.getHeaderNames(), name -> Arrays.asList(request.getHeaderValues(name)));
	}

	/**
	 * 获取所有 deliver 请求头
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, Collection<String>> collectDeliverHeader(HttpServletRequest request) {
		return collectDeliverHeader(IteratorUtils.asIterator(request.getHeaderNames()), name -> EnumerationUtils.toList(request.getHeaders(name)));
	}

	/**
	 * 获取所有 deliver 请求头
	 * 
	 * @param nameIterator
	 * @param headerValueFun
	 * @return
	 */
	private static final Map<String, Collection<String>> collectDeliverHeader(Iterator<String> nameIterator, Function<String, Collection<String>> headerValueFun) {
		Map<String, Collection<String>> deliverHeaders = new HashMap<String, Collection<String>>();
		while(nameIterator.hasNext()) {
			String name = nameIterator.next();
			if(GenericDeliverHeaders.isRequestDeliverHeader(name)) {
				deliverHeaders.put(name, headerValueFun.apply(name));
			}
		}
		return deliverHeaders;
	}

}
