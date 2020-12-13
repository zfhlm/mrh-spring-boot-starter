package org.lushen.mrh.boot.autoconfigure.webmvc.advice;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
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

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * plugin 全局异常处理器
 * 
 * @author hlm
 */
public class ExceptionPluginAdvice implements InitializingBean, ExceptionAdvice {

	private final Log log = LogFactory.getLog(ExceptionPluginAdvice.class);

	@Autowired
	private PluginRegistry<ExceptionPlugin, Throwable> registry;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
		registry.getPlugins().forEach(plugin -> log.info("Plugin [" + plugin.getName() + "]"));
	}

	@Override
	public ResponseEntity<byte[]> errorHandler(Throwable cause, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GenericResult result = registry.getPluginFor(cause).orElseThrow(RuntimeException::new).handle(cause);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
		return new ResponseEntity<byte[]>(this.objectMapper.writeValueAsBytes(result), headers, HttpStatus.OK);
	}

}
