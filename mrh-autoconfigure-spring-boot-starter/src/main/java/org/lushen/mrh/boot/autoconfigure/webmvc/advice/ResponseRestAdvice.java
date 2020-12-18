package org.lushen.mrh.boot.autoconfigure.webmvc.advice;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.autoconfigure.support.error.GenericStatus;
import org.lushen.mrh.boot.autoconfigure.support.page.Page;
import org.lushen.mrh.boot.autoconfigure.support.runtime.BootManifestLoader;
import org.lushen.mrh.boot.autoconfigure.support.runtime.JavaCommandLoader;
import org.lushen.mrh.boot.autoconfigure.support.view.GenericResult;
import org.lushen.mrh.boot.autoconfigure.support.view.MultipleResult;
import org.lushen.mrh.boot.autoconfigure.support.view.PageableResult;
import org.lushen.mrh.boot.autoconfigure.support.view.SingleResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

/**
 * rest body 切面
 * 
 * @author hlm
 */
public class ResponseRestAdvice implements ResponseAdvice, InitializingBean {

	protected String basePackage;	// 切面生效的包名

	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化为启动类所在包及其子包
		Optional.ofNullable(new BootManifestLoader().load().getStartClass()).ifPresent(startClass -> {
			try {
				basePackage = Class.forName(startClass).getPackage().getName();
			} catch (Exception e) {}
		});
		if(basePackage == null) {
			basePackage = new JavaCommandLoader().load().getPackage().getName();
		}
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return StringUtils.startsWith(returnType.getContainingClass().getPackage().getName(), this.basePackage);
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		GenericStatus status = GenericStatus.SUCCESS;
		if(body == null) {
			return new GenericResult(status.getErrcode(), status.getErrmsg());
		}
		else if(body instanceof GenericResult) {
			return body;
		}
		else if(body instanceof Collection) {
			return new MultipleResult(status.getErrcode(), status.getErrmsg(), (Collection<?>)body);
		}
		else if(body instanceof Object[]) {
			return new MultipleResult(status.getErrcode(), status.getErrmsg(), (Object[])body);
		}
		else if(body instanceof Page) {
			Page<?> page = (Page<?>) body;
			return new PageableResult(status.getErrcode(), status.getErrmsg(), page.getDatas(), page.getTotals());
		}
		else {
			return new SingleResult(status.getErrcode(), status.getErrmsg(), body);
		}
	}

}
