package org.lushen.mrh.boot.autoconfigure.webmvc.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理器
 * 
 * @author hlm
 */
@ControllerAdvice
public interface ExceptionAdvice {

	/**
	 * 异常转换处理
	 * 
	 * @param cause
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<byte[]> errorHandler(Throwable cause, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
