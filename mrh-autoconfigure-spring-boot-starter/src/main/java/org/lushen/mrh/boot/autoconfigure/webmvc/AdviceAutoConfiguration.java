package org.lushen.mrh.boot.autoconfigure.webmvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ExceptionAdvice;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ExceptionPluginAdvice;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ResponseAdvice;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ResponseRestAdvice;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin.BindExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin.GenericExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin.HttpMessageNotReadableExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin.HttpRequestMethodNotSupportedExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin.MethodArgumentNotValidExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin.MissingRequestHeaderExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin.NoHandlerFoundExceptionPlugin;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin.ThrowablePlugin;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器自动配置
 * 
 * @author hlm
 */
@Configuration(proxyBeanMethods=false)
@ConditionalOnWebApplication(type=Type.SERVLET)
public class AdviceAutoConfiguration {
	
	private final Log log = LogFactory.getLog(getClass());

	@EnablePluginRegistries(ExceptionPlugin.class)
	@Configuration(proxyBeanMethods=false)
	public static class PluginAutoConfiguration {}

	@Bean
	@ConditionalOnMissingBean(ExceptionAdvice.class)
	public ExceptionAdvice exceptionAdvice() {
		log.info(String.format("Initialize bean %s.", ExceptionPluginAdvice.class));
		return new ExceptionPluginAdvice();
	}

	@Bean
	@ConditionalOnMissingBean(ResponseAdvice.class)
	public ResponseAdvice responseAdvice() {
		log.info(String.format("Initialize bean %s.", ResponseRestAdvice.class));
		return new ResponseRestAdvice();
	}

	@Bean
	public GenericExceptionPlugin genericExceptionPlugin() {
		return new GenericExceptionPlugin();
	}

	@Bean
	public ThrowablePlugin throwablePlugin() {
		return new ThrowablePlugin();
	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnClass(BindException.class)
	public static class BindExceptionPluginConfiguration {

		@Bean
		public BindExceptionPlugin bindExceptionPlugin() {
			return new BindExceptionPlugin();
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnClass(MethodArgumentNotValidException.class)
	public static class MethodArgumentNotValidPluginConfiguration {

		@Bean
		public MethodArgumentNotValidExceptionPlugin methodArgumentNotValidExceptionPlugin() {
			return new MethodArgumentNotValidExceptionPlugin();
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnClass(MissingRequestHeaderException.class)
	public static class MissingRequestHeaderPluginConfiguration {

		@Bean
		public MissingRequestHeaderExceptionPlugin missingRequestHeaderExceptionPlugin() {
			return new MissingRequestHeaderExceptionPlugin();
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnClass(NoHandlerFoundException.class)
	public static class NoHandlerFoundPluginConfiguration {

		@Bean
		public NoHandlerFoundExceptionPlugin noHandlerFoundExceptionPlugin() {
			return new NoHandlerFoundExceptionPlugin();
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnClass(HttpMessageNotReadableException.class)
	public static class HttpMessageNotReadablePluginConfiguration {

		@Bean
		public HttpMessageNotReadableExceptionPlugin httpMessageNotReadableExceptionPlugin() {
			return new HttpMessageNotReadableExceptionPlugin();
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnClass(HttpRequestMethodNotSupportedException.class)
	public static class HttpRequestMethodNotSupportedExceptionPluginConfiguration {

		@Bean
		public HttpRequestMethodNotSupportedExceptionPlugin httpRequestMethodNotSupportedExceptionPlugin() {
			return new HttpRequestMethodNotSupportedExceptionPlugin();
		}

	}

}
