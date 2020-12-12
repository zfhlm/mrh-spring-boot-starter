package org.lushen.mrh.boot.springfox.plugin;

import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.springfox.annotation.Doc;
import org.lushen.mrh.boot.springfox.annotation.DocHidden;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import springfox.bean.validators.plugins.Validators;
import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelBuilderPlugin;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.ApiListingContext;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

/**
 * 自定义注解 {@link Doc} 和 {@link DocHidden} 文档扩展组件
 * 
 * @author hlm
 */
public class DocPlugin implements ApiListingBuilderPlugin, OperationBuilderPlugin, ModelBuilderPlugin, ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin, Ordered {

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 10000;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	// 分组配置
	@Override
	public void apply(ApiListingContext apiListingContext) {
		Class<?> controllerClass = apiListingContext.getResourceGroup().getControllerClass().orElse(null);
		Optional.ofNullable(AnnotationUtils.findAnnotation(controllerClass, Doc.class)).map(Doc::value).filter(StringUtils::isNotBlank).ifPresent(value -> {
			ApiListingBuilder builder = apiListingContext.apiListingBuilder();
			builder.tagNames(Collections.singleton(value));
			builder.description(StringUtils.substringAfterLast(controllerClass.getTypeName(), "."));
		});
	}

	//  接口配置
	@Override
	public void apply(OperationContext context) {
		context.findAnnotation(Doc.class).map(Doc::value).filter(StringUtils::isNotBlank).ifPresent(value -> {
			context.operationBuilder().summary(value);
		});
		context.findControllerAnnotation(Doc.class).map(Doc::value).filter(StringUtils::isNotBlank).ifPresent(value -> {
			context.operationBuilder().tags(Collections.singleton(value));
		});
		context.findAnnotation(DocHidden.class).ifPresent(hidden -> {
			context.operationBuilder().hidden(hidden.value());
		});
	}

	// 参数model配置
	@Override
	public void apply(ModelContext context) {
		Optional.ofNullable(AnnotationUtils.findAnnotation(context.getType().getErasedType(), Doc.class)).ifPresent(doc -> {
			// 不显示注释
			//context.getModelSpecificationBuilder().facets(f -> f.description(doc.value()));
		});
	}

	// 表单参数配置
	@Override
	public void apply(ParameterExpansionContext context) {
		context.findAnnotation(Doc.class).ifPresent(doc -> {
			context.getRequestParameterBuilder().description(doc.value());
		});
		context.findAnnotation(DocHidden.class).ifPresent(hidden -> {
			context.getRequestParameterBuilder().hidden(hidden.value());
		});
	}

	// JSON参数配置
	@Override
	public void apply(ModelPropertyContext context) {
		Validators.annotationFromField(context, Doc.class).ifPresent(doc -> {
			context.getSpecificationBuilder().description(doc.value());
		});
		Validators.annotationFromField(context, DocHidden.class).ifPresent(hidden -> {
			context.getSpecificationBuilder().isHidden(hidden.value());
		});
	}

}
