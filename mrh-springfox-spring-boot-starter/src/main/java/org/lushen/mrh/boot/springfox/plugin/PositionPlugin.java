package org.lushen.mrh.boot.springfox.plugin;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.core.Ordered;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

/**
 * 排序扩展，3.0.0 官方bug暂时无法生效
 * 
 * @author hlm
 */
public class PositionPlugin implements ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin, OperationBuilderPlugin, Ordered {

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(OperationContext context) {
		
	}

	@Override
	public void apply(ModelPropertyContext context) {
		context.getAnnotatedElement().filter(e -> e instanceof Field).map(e -> (Field)e).ifPresent(field -> {
			Field[] fields = FieldUtils.getAllFields(field.getDeclaringClass());
			for(int index=0; index<fields.length; index++) {
				if(StringUtils.equals(fields[index].getName(), field.getName())) {
					context.getSpecificationBuilder().position(index);
				}
			}
		});
	}

	@Override
	public void apply(ParameterExpansionContext context) {

	}

}
