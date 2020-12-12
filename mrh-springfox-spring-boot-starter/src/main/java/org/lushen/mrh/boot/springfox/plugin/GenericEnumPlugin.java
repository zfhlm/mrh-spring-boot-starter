package org.lushen.mrh.boot.springfox.plugin;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.autoconfigure.support.enums.GenericEnum;
import org.springframework.core.Ordered;

import springfox.documentation.builders.ExampleBuilder;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

/**
 * 通用枚举 文档扩展组件
 * 
 * @author hlm
 */
public class GenericEnumPlugin implements ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin, Ordered {

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		Class<?> fieldType = context.getFieldType().getErasedType();
		if(GenericEnum.class.isAssignableFrom(fieldType) && Enum.class.isAssignableFrom(fieldType)) {
			GenericEnum<?>[] contants = (GenericEnum<?>[])fieldType.getEnumConstants();
			context.getRequestParameterBuilder().example(new ExampleBuilder().value(example(contants)).build());
		}
	}

	@Override
	public void apply(ModelPropertyContext context) {
		
		
		AnnotatedElement annotatedElement = context.getAnnotatedElement().orElse(null);
		if(annotatedElement != null && annotatedElement instanceof Field) {
			Class<?> fieldType = ((Field)annotatedElement).getType();
			if(GenericEnum.class.isAssignableFrom(fieldType) && Enum.class.isAssignableFrom(fieldType)) {
				GenericEnum<?>[] contants = (GenericEnum<?>[])fieldType.getEnumConstants();
				context.getSpecificationBuilder().enumerationFacet(facetBuilder -> {
					facetBuilder.allowedValues(new AllowableListValues(values(contants), null));
				});
				context.getSpecificationBuilder().example(example(contants));
			}
		}
	}

	private List<String> values(GenericEnum<?>[] contants) {
		return Arrays.stream(contants).map(e -> StringUtils.join(e.toValue(), ":", e.toName())).collect(Collectors.toList());
	}

	private Integer example(GenericEnum<?>[] contants) {
		return Arrays.stream(contants).findFirst().map(e -> e.toValue()).orElse(null);
	}

}
