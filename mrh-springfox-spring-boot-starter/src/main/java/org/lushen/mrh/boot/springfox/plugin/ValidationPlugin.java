package org.lushen.mrh.boot.springfox.plugin;

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
	public void apply(ModelPropertyContext context) {
		context.getAnnotatedElement().ifPresent(element -> {
			if(element.isAnnotationPresent(javax.validation.constraints.NotEmpty.class)) {
				context.getSpecificationBuilder().required(Boolean.TRUE);
			}
		});
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		if(context.findAnnotation(javax.validation.constraints.NotEmpty.class).isPresent()) {
			context.getRequestParameterBuilder().required(Boolean.TRUE);
		}
	}

}
