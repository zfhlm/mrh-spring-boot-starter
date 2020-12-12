package org.lushen.mrh.boot.springfox.plugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.autoconfigure.support.enums.GenericEnum;
import org.springframework.core.Ordered;

import springfox.documentation.builders.ExampleBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

/**
 * 枚举类型 {@link GenericEnum} 文档扩展组件
 * 
 * @author hlm
 */
public class GenericEnumPlugin implements ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin, Ordered {

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 20000;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		Class<?> fieldType = context.getFieldType().getErasedType();
		if(GenericEnum.class.isAssignableFrom(fieldType)) {
			GenericEnum<?>[] contants = (GenericEnum<?>[])fieldType.getEnumConstants();
			context.getRequestParameterBuilder().description(description(context.getRequestParameterBuilder().build().getDescription(), contants));
			context.getRequestParameterBuilder().example(new ExampleBuilder().value(example(contants)).build());
		}
	}

	@Override
	public void apply(ModelPropertyContext context) {
		Class<?> fieldType = context.getAnnotatedElement().filter(e -> e instanceof Field).map(e -> ((Field)e).getType()).orElse(null);
		if(GenericEnum.class.isAssignableFrom(fieldType)) {
			GenericEnum<?>[] contants = (GenericEnum<?>[])fieldType.getEnumConstants();
			context.getSpecificationBuilder().description(description(context.getSpecificationBuilder().build().getDescription(), contants));
			context.getSpecificationBuilder().example(example(contants));
		}
	}

	private String description(String description, GenericEnum<?>[] contants) {
		String generics = Arrays.stream(contants).map(e -> StringUtils.join(e.toValue(), ":", e.toName())).collect(Collectors.joining(", "));
		return StringUtils.isBlank(description)? generics : StringUtils.join(description, "：", generics);
	}

	private Integer example(GenericEnum<?>[] contants) {
		return Arrays.stream(contants).findFirst().map(e -> e.toValue()).orElse(null);
	}

}
