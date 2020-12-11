package org.lushen.mrh.boot.autoconfigure.support.enums;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 枚举基础类
 * 
 * @author hlm
 * @param <E>
 */
@JsonSerialize(using=GenericEnumJacksonSerializer.class)
@JsonDeserialize(using=GenericEnumJacksonDeserializer.class)
public interface GenericEnum<E extends Enum<E>> {

	/**
	 * 获取枚举值
	 * 
	 * @return
	 */
	public Integer toValue();

	/**
	 * 获取枚举名称
	 * 
	 * @return
	 */
	public String toName();

	/**
	 * 是否等于当前枚举值
	 * 
	 * @param value
	 * @return
	 */
	default boolean isEquals(Integer value) {
		return value == null? false : toValue().intValue() == value.intValue();
	}

	/**
	 * 转换枚举值为枚举类型
	 * 
	 * @param actualClass
	 * @param value
	 * @return
	 */
	public static <S extends GenericEnum<?>> S fromValue(Class<S> actualClass, String value) {
		return StringUtils.isBlank(value)? null:fromValue(actualClass, Integer.valueOf(value));
	}

	/**
	 * 转换枚举值为枚举类型
	 * 
	 * @param actualClass
	 * @param value
	 * @return
	 */
	public static <S extends GenericEnum<?>> S fromValue(Class<S> actualClass, Integer value) {
		if(value != null) {
			for(S contant : actualClass.getEnumConstants()) {
				if(contant.isEquals(value)) {
					return contant;
				}
			}
			throw new RuntimeException(String.format("值%s没有对应的枚举类型!", value));
		}
		return null;
	}

}
