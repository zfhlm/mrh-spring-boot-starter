package org.lushen.mrh.boot.springfox.plugin;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import springfox.bean.validators.plugins.Validators;
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

	private DateFormat dateFormat;

	public JacksonPlugin(DateFormat dateFormat) {
		super();
		this.dateFormat = dateFormat;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		Class<?> fieldType = context.getFieldType().getErasedType();
		if(Date.class.isAssignableFrom(fieldType)) {
			context.getRequestParameterBuilder().example(new ExampleBuilder().value(defaultExample()).build());
			context.findAnnotation(JsonFormat.class).ifPresent(jsonFormat -> {
				context.getRequestParameterBuilder().example(new ExampleBuilder().value(new SimpleDateFormat(jsonFormat.pattern()).format(new Date())).build());
			});
		}
	}

	@Override
	public void apply(ModelPropertyContext context) {
		Class<?> fieldType = context.getAnnotatedElement().filter(e -> e instanceof Field).map(e -> ((Field)e).getType()).orElse(null);
		if(Date.class.isAssignableFrom(fieldType)) {
			context.getSpecificationBuilder().example(defaultExample());
			Validators.annotationFromField(context, JsonFormat.class).ifPresent(format -> {
				context.getSpecificationBuilder().example(new SimpleDateFormat(format.pattern()).format(new Date()));
			});
		}
	}

	private String defaultExample() {
		if(this.dateFormat == null) {
			return null;
		} else {
			return this.dateFormat.format(new Date());
		}
	}

}
