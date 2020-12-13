package org.lushen.mrh.boot.autoconfigure.webmvc.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * rest body 切面
 * 
 * @author hlm
 */
@ControllerAdvice
public interface ResponseAdvice extends ResponseBodyAdvice<Object> {

}
