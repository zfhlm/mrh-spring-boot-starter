package org.lushen.mrh.boot.autoconfigure.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.support.generic.enums.GenericEnum;
import org.lushen.mrh.support.generic.enums.GenericEnumMvcConverterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link GenericEnum} mvc 转换器 自动配置
 * 
 * @author hlm
 */
@Configuration
@ConditionalOnClass(GenericEnum.class)
@ConditionalOnWebApplication(type=Type.SERVLET)
public class GenericEnumAutoConfiguration {

	private final Log log = LogFactory.getLog(GenericEnumAutoConfiguration.class);

	@Bean
	public GenericEnumMvcConverterFactory genericEnumMvcConverterFactory() {
		log.info("Initialize bean " + GenericEnumMvcConverterFactory.class);
		return new GenericEnumMvcConverterFactory();
	}

}
