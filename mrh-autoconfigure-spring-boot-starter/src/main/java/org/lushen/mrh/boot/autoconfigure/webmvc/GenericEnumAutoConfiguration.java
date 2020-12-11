package org.lushen.mrh.boot.autoconfigure.webmvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.support.enums.GenericEnum;
import org.lushen.mrh.boot.autoconfigure.support.enums.GenericEnumMvcConverterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link GenericEnum} mvc 转换器 自动配置
 * 
 * @author hlm
 */
@Configuration(proxyBeanMethods=false)
@ConditionalOnWebApplication(type=Type.SERVLET)
public class GenericEnumAutoConfiguration {

	private final Log log = LogFactory.getLog(GenericEnumAutoConfiguration.class);

	@Bean
	public GenericEnumMvcConverterFactory genericEnumMvcConverterFactory() {
		log.info("Initialize bean " + GenericEnumMvcConverterFactory.class);
		return new GenericEnumMvcConverterFactory();
	}

}
