package org.lushen.mrh.boot.autoconfigure.cache;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.EnumerationUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 缓存key生成器
 * 
 * @author hlm
 */
public class CacheKeyGenerator implements KeyGenerator {

	public static final String CACHE_KEY_GENERATOR = "org.lushen.mrh.autoconfigure.cache.key.generator";

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return toString(params);
	}

	private String toString(Object arg) {
		if(arg == null) {
			return "null";
		}
		if(arg instanceof String) {
			return StringEscapeUtils.escapeJava((String)arg);
		}
		else if(arg instanceof Date) {
			return new SimpleDateFormat("yyyyMMddHHmmssSSS").format((Date)arg);
		}
		else if(arg instanceof Object[]) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(Arrays.stream((Object[])arg).map(param -> toString(param)).collect(Collectors.joining(",")));
			sb.append("]");
			return sb.toString();
		}
		else if(arg instanceof Collection) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(((Collection<?>)arg).stream().map(param -> toString(param)).collect(Collectors.joining(",")));
			sb.append("]");
			return sb.toString();
		}
		else if(arg instanceof Map) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(((Map<?,?>)arg).entrySet().stream().map(param -> toString(param)).collect(Collectors.joining(",")));
			sb.append("]");
			return sb.toString();
		}
		else if(arg instanceof Map.Entry) {
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			sb.append(toString(((Map.Entry<?,?>) arg).getKey()));
			sb.append("=");
			sb.append(toString(((Map.Entry<?,?>) arg).getValue()));
			sb.append(")");
			return sb.toString();
		}
		else if(arg instanceof Enumeration) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(EnumerationUtils.toList((Enumeration<?>)arg).stream().map(param -> toString(param)).collect(Collectors.joining(",")));
			sb.append("]");
			return sb.toString();
		}
		else {
			return String.valueOf(arg);
		}
	}

}
