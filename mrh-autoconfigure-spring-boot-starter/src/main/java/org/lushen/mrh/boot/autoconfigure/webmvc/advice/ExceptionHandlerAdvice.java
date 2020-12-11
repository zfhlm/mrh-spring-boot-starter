package org.lushen.mrh.boot.autoconfigure.webmvc.advice;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.support.view.GenericResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 全局异常处理器
 * 
 * @author hlm
 */
@ControllerAdvice
public class ExceptionHandlerAdvice implements InitializingBean {

	private final Log log = LogFactory.getLog(ExceptionHandlerAdvice.class);

	@Autowired
	private PluginRegistry<ExceptionPlugin, Throwable> registry;

	@Autowired
	private ObjectMapper objectMapper;

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<byte[]> errorHandler(Throwable cause, HttpServletResponse response) throws Exception {
		GenericResult result = registry.getPluginFor(cause).orElseThrow(RuntimeException::new).handle(cause);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
		return new ResponseEntity<byte[]>(this.objectMapper.writeValueAsBytes(result), headers, HttpStatus.OK);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		registry.getPlugins().forEach(plugin -> log.info("Plugin [" + plugin.nameForPlugin() + "]"));
	}

}
