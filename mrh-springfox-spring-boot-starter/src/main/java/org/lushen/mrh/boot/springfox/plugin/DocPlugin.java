package org.lushen.mrh.boot.springfox.plugin;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.lushen.mrh.boot.springfox.annotation.Doc;
import org.lushen.mrh.boot.springfox.annotation.DocHidden;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import com.fasterxml.classmate.TypeResolver;

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
 * 自定义注解 文档扩展组件
 * 
 * @author hlm
 */
public class DocPlugin implements ApiListingBuilderPlugin, OperationBuilderPlugin, ModelBuilderPlugin, ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin, Ordered {

	private final TypeResolver typeResolver;

	public DocPlugin(TypeResolver typeResolver) {
		this.typeResolver = typeResolver;
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(ApiListingContext apiListingContext) {
		Class<?> controllerClass = apiListingContext.getResourceGroup().getControllerClass().orElse(null);
		Optional.ofNullable(AnnotationUtils.findAnnotation(controllerClass, Doc.class)).map(Doc::value).filter(StringUtils::isNotBlank).ifPresent(value -> {
			ApiListingBuilder builder = apiListingContext.apiListingBuilder();
			builder.tagNames(Collections.singleton(value));
			builder.description(StringUtils.substringAfterLast(controllerClass.getTypeName(), "."));
		});
	}

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

	@Override
	public void apply(ModelContext context) {
		Class<?> modelClass = typeResolver.resolve(context.getType()).getErasedType();
		Optional.ofNullable(AnnotationUtils.findAnnotation(modelClass, Doc.class)).ifPresent(doc -> {
			context.getModelSpecificationBuilder().name(doc.value());
		});
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		context.findAnnotation(Doc.class).ifPresent(doc -> {
			context.getRequestParameterBuilder().description(doc.value());
		});
		context.findAnnotation(DocHidden.class).ifPresent(hidden -> {
			context.getRequestParameterBuilder().hidden(hidden.value());
		});
	}

	@Override
	public void apply(ModelPropertyContext context) {
		context.getBeanPropertyDefinition().map(e -> e.getField()).map(e -> e.getAnnotation(Doc.class)).ifPresent(doc -> {
			context.getSpecificationBuilder().description(doc.value());
		});
		context.getBeanPropertyDefinition().map(e -> e.getField()).map(e -> e.getAnnotation(DocHidden.class)).ifPresent(hidden -> {
			context.getSpecificationBuilder().isHidden(hidden.value());
		});
		context.getBeanPropertyDefinition().map(e -> e.getField()).ifPresent(field -> {
			Field[] fields = FieldUtils.getAllFields(field.getDeclaringClass());
			for(int index=0; index<fields.length; index++) {
				if(StringUtils.equals(fields[index].getName(), field.getName())) {
					context.getSpecificationBuilder().position(index);
				}
			}
		});
	}

}
