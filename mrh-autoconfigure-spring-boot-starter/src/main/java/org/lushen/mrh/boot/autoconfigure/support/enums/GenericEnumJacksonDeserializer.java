package org.lushen.mrh.boot.autoconfigure.support.enums;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

/**
 * Jackson 枚举类型反序列化转换器
 * 
 * @author hlm
 */
public class GenericEnumJacksonDeserializer extends JsonDeserializer<GenericEnumJacksonDeserializer.BasicNullEnum> implements ContextualDeserializer {

	@Override
	public GenericEnumJacksonDeserializer.BasicNullEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return null;
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctx, BeanProperty property) throws JsonMappingException {

		//获取反序列化目标字段类型
		Class<?> propertyClass = property.getType().getRawClass();

		//如果是枚举类型
		if(GenericEnum.class.isAssignableFrom(propertyClass)) {

			return new JsonDeserializer<GenericEnum<?>>() {

				@SuppressWarnings("unchecked")
				@Override
				public GenericEnum<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {

					Class<GenericEnum<?>> actualClass = (Class<GenericEnum<?>>) propertyClass;
					JsonToken token = parser.getCurrentToken();

					//null
					if(token == JsonToken.VALUE_NULL) {
						return null;
					}
					//int
					else if(token == JsonToken.VALUE_NUMBER_INT) {
						return GenericEnum.fromValue(actualClass, parser.getIntValue());
					}
					//string
					else if(token == JsonToken.VALUE_STRING) {
						return GenericEnum.fromValue(actualClass, parser.getValueAsString());
					}
					// other
					return null;

				}

			};

		}

		return null;
	}

	public static enum BasicNullEnum implements GenericEnum<BasicNullEnum> {;
		@Override
		public Integer toValue() {
			return null;
		}
		@Override
		public String toName() {
			return null;
		}
	}

}
