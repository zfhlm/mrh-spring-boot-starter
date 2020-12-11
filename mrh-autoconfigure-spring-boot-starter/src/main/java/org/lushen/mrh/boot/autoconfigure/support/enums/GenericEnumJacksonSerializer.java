package org.lushen.mrh.boot.autoconfigure.support.enums;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Jackson 枚举类型序列化转换器
 * 
 * @author hlm
 */
@SuppressWarnings("rawtypes")
public class GenericEnumJacksonSerializer extends JsonSerializer<GenericEnum> {

	private static final String UNDER_LINE = "_";

	private static final String NAME_SUFFIX = "Name";

	@Override
	public void serialize(GenericEnum basicEnum, JsonGenerator generator, SerializerProvider provider) throws IOException {
		if(basicEnum == null) {
			generator.writeNull();
		} else {
			//写出枚举值
			generator.writeNumber(basicEnum.toValue());
			//写出枚举名称键值
			String currentName = generator.getOutputContext().getCurrentName();
			if(currentName != null) {
				generator.writeStringField(withSuffixName(currentName), basicEnum.toName());
			}
		}
	}

	private String withSuffixName(String currentName) {

		//生成名称
		String name = currentName;
		if(StringUtils.contains(name, UNDER_LINE)) {
			name = StringUtils.join(name, UNDER_LINE, NAME_SUFFIX);
		} else {
			name = StringUtils.join(name, NAME_SUFFIX);
		}

		//处理大小写问题
		if(StringUtils.isAllUpperCase(currentName)) {
			return StringUtils.upperCase(name);
		}
		else {
			return name;
		}

	}

}
