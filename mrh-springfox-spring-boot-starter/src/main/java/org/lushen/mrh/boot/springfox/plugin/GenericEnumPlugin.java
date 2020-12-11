package org.lushen.mrh.boot.springfox.plugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.autoconfigure.support.enums.GenericEnum;

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
public class GenericEnumPlugin implements ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin {

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		Optional.ofNullable(context.getFieldType().getErasedType()).map(this::description).ifPresent(description -> {
			context.getRequestParameterBuilder().description(description);
		});
	}

	@Override
	public void apply(ModelPropertyContext context) {
		context.getAnnotatedElement().map(e -> ((Field)e).getType()).map(this::description).ifPresent(description -> {
			context.getSpecificationBuilder().description(description);
		});
	}

	private String description(Class<?> filedType) {
		if(filedType != null && GenericEnum.class.isAssignableFrom(filedType) && Enum.class.isAssignableFrom(filedType)) {
			GenericEnum<?>[] contants = (GenericEnum<?>[])filedType.getEnumConstants();
			return Arrays.stream(contants).map(e -> StringUtils.join(e.toValue(), ":", e.toName())).collect(Collectors.joining(", "));
		} else {
			return null;
		}
	}

}
