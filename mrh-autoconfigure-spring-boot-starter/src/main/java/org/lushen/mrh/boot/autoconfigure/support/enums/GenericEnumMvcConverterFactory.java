package org.lushen.mrh.boot.autoconfigure.support.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC 枚举参数转换器工厂
 * 
 * @author hlm
 */
public class GenericEnumMvcConverterFactory implements ConverterFactory<String, GenericEnum<?>>, WebMvcConfigurer {

	@Override
	public <T extends GenericEnum<?>> Converter<String, T> getConverter(Class<T> targetType) {
		return (source -> GenericEnum.fromValue(targetType, source));
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(this);
	}

}
