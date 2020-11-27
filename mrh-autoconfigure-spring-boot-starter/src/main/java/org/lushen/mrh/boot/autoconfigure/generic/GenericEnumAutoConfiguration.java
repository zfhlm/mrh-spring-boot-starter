package org.lushen.mrh.boot.autoconfigure.generic;

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

	@Bean
	public GenericEnumMvcConverterFactory genericEnumMvcConverterFactory() {
		return new GenericEnumMvcConverterFactory();
	}

}
