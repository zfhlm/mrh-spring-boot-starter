package org.lushen.mrh.boot.autoconfigure.web;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.web.plugin.BindExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.web.plugin.GenericExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.web.plugin.HttpMessageNotReadableExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.web.plugin.MethodArgumentNotValidExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.web.plugin.MissingRequestHeaderExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.web.plugin.NoHandlerFoundExceptionPlugin;
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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ConditionalOnClass(GenericResult.class)
@ConditionalOnWebApplication(type=Type.SERVLET)
public class ErrorHandlerAutoConfiguration {

	@ControllerAdvice
	@EnablePluginRegistries(ErrorHandlerPlugin.class)
	public static class ErrorHandler implements InitializingBean {

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
			log.info("Initialize error handler.");
			registry.getPlugins().forEach(plugin -> log.info("Plugin [" + plugin.nameForPlugin() + "]"));
		}

	}

	@Bean
	public GenericExceptionPlugin genericExceptionPlugin() {
		return new GenericExceptionPlugin();
	}

	@Bean
	public ThrowablePlugin throwablePlugin() {
		return new ThrowablePlugin();
	}

	@Configuration
	@ConditionalOnClass(BindException.class)
	public static class BindExceptionPluginConfiguration {

		@Bean
		public BindExceptionPlugin bindExceptionPlugin() {
			return new BindExceptionPlugin();
		}

	}

	@Configuration
	@ConditionalOnClass(MethodArgumentNotValidException.class)
	public static class MethodArgumentNotValidPluginConfiguration {

		@Bean
		public MethodArgumentNotValidExceptionPlugin methodArgumentNotValidExceptionPlugin() {
			return new MethodArgumentNotValidExceptionPlugin();
		}

	}

	@Configuration
	@ConditionalOnClass(MissingRequestHeaderException.class)
	public static class MissingRequestHeaderPluginConfiguration {

		@Bean
		public MissingRequestHeaderExceptionPlugin missingRequestHeaderExceptionPlugin() {
			return new MissingRequestHeaderExceptionPlugin();
		}

	}

	@Configuration
	@ConditionalOnClass(NoHandlerFoundException.class)
	public static class NoHandlerFoundPluginConfiguration {

		@Bean
		public NoHandlerFoundExceptionPlugin noHandlerFoundExceptionPlugin() {
			return new NoHandlerFoundExceptionPlugin();
		}

	}

	@Configuration
	@ConditionalOnClass(HttpMessageNotReadableException.class)
	public static class HttpMessageNotReadablePluginConfiguration {

		@Bean
		public HttpMessageNotReadableExceptionPlugin httpMessageNotReadableExceptionPlugin() {
			return new HttpMessageNotReadableExceptionPlugin();
		}

	}

}
