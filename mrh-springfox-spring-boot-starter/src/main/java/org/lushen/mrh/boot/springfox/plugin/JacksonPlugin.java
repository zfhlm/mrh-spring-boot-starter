package org.lushen.mrh.boot.springfox.plugin;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.builders.ExampleBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

/**
 * jackson 文档扩展组件
 * 
 * @author hlm
 */
public class JacksonPlugin implements ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin {

	private ObjectMapper objectMapper;

	public JacksonPlugin(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(ModelPropertyContext context) {
		JsonFormat format = context.getAnnotatedElement().map(e -> e.getAnnotation(JsonFormat.class)).orElse(null);
		if(format != null && StringUtils.isNotBlank(format.pattern())) {
			Object example = new SimpleDateFormat(format.pattern()).format(new Date());
			context.getSpecificationBuilder().example(example);
		} else {
			DateFormat dateFormat = objectMapper.getDateFormat();
			Field field = (Field)context.getAnnotatedElement().orElse(null);
			if(dateFormat != null && field != null) {
				if(Date.class.isAssignableFrom(field.getType())) {
					Object exampleValue = dateFormat.format(new Date());
					context.getSpecificationBuilder().example(exampleValue);
				}
			}
		}
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		JsonFormat format = context.findAnnotation(JsonFormat.class).orElse(null);
		if(format != null && StringUtils.isNotBlank(format.pattern())) {
			Object example = new SimpleDateFormat(format.pattern()).format(new Date());
			context.getRequestParameterBuilder().example(new ExampleBuilder().value(example).build());
		} else {
			DateFormat dateFormat = objectMapper.getDateFormat();
			Class<?> filedType = context.getFieldType().getErasedType();
			if(dateFormat != null && filedType != null) {
				if(Date.class.isAssignableFrom(filedType)) {
					Object example = dateFormat.format(new Date());
					context.getRequestParameterBuilder().example(new ExampleBuilder().value(example).build());
				}
			}
		}
	}

}
