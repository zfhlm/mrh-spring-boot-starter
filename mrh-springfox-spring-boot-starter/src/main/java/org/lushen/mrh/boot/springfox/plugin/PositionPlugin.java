package org.lushen.mrh.boot.springfox.plugin;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

/**
 * 根据 Java bean fields 下标进行排序
 * 
 * @author hlm
 */
public class PositionPlugin implements ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin {

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
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
