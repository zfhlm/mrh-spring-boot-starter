package org.lushen.mrh.boot.autoconfigure.webmvc.advice;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.autoconfigure.support.error.GenericStatus;
import org.lushen.mrh.boot.autoconfigure.support.runtime.BootManifestLoader;
import org.lushen.mrh.boot.autoconfigure.support.runtime.JavaCommandLoader;
import org.lushen.mrh.boot.autoconfigure.support.view.GenericResult;
import org.lushen.mrh.boot.autoconfigure.support.view.MultipleResult;
import org.lushen.mrh.boot.autoconfigure.support.view.SingleResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * rest body 切面
 * 
 * @author hlm
 */
@ControllerAdvice
public class RestResponseBodyAdvice implements ResponseBodyAdvice<Object>, InitializingBean {

	protected String basePackage;

	@Override
	public void afterPropertiesSet() throws Exception {
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
		else if(body instanceof Collection) {
			return new MultipleResult(status.getErrcode(), status.getErrmsg(), (Collection<?>)body);
		}
		else if(body instanceof Object[]) {
			return new MultipleResult(status.getErrcode(), status.getErrmsg(), (Object[])body);
		}
		else {
			return new SingleResult(status.getErrcode(), status.getErrmsg(), body);
		}
	}

}
