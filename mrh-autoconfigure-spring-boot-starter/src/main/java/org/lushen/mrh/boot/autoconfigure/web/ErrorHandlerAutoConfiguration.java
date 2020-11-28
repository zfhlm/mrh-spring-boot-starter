package org.lushen.mrh.boot.autoconfigure.web;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.web.plugin.GenericExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.web.plugin.ThrowablePlugin;
import org.lushen.mrh.support.generic.view.GenericResult;
import org.lushen.mrh.support.util.CloseableUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ConditionalOnClass(GenericResult.class)
@ConditionalOnWebApplication(type=Type.SERVLET)
public class ErrorHandlerAutoConfiguration {

	@Bean
	public GenericExceptionPlugin genericExceptionPlugin() {
		return new GenericExceptionPlugin();
	}

	@Bean
	public ThrowablePlugin throwablePlugin() {
		return new ThrowablePlugin();
	}

	@ControllerAdvice
	@EnablePluginRegistries(ErrorHandlerPlugin.class)
	public class ErrorHandler implements InitializingBean {

		private final Log log = LogFactory.getLog(ErrorHandler.class);

		@Autowired
		private PluginRegistry<ErrorHandlerPlugin, Throwable> registry;

		@Autowired
		private ObjectMapper objectMapper;

		@ExceptionHandler(Throwable.class)
		public void errorHandler(Throwable cause, HttpServletResponse response) throws Exception {
			GenericResult result = registry.getPluginFor(cause).handle(cause);
			OutputStream out = null;
			try {
				response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
				out = response.getOutputStream();
				out.write(objectMapper.writeValueAsBytes(result));
				out.flush();
				out.close();
			} finally {
				CloseableUtils.close(out);
			}
		}

		@Override
		public void afterPropertiesSet() throws Exception {
			registry.getPlugins().forEach(plugin -> {
				log.info("Registry plugin [ " + plugin.nameForPlugin() + " ]");
			});
		}

	}

}
