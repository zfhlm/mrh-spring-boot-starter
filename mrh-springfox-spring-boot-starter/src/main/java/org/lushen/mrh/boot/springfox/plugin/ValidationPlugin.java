package org.lushen.mrh.boot.springfox.plugin;

import javax.validation.constraints.NotEmpty;

import springfox.bean.validators.plugins.Validators;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

/**
 * validation 文档扩展组件
 * 
 * @author hlm
 */
public class ValidationPlugin implements ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin {

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		RequestParameterBuilder builder = context.getRequestParameterBuilder();
		context.findAnnotation(NotEmpty.class).ifPresent(e -> builder.required(Boolean.TRUE));
	}

	@Override
	public void apply(ModelPropertyContext context) {
		PropertySpecificationBuilder builder = context.getSpecificationBuilder();
		Validators.annotationFromField(context, NotEmpty.class).ifPresent(e -> builder.required(Boolean.TRUE));
	}

}
