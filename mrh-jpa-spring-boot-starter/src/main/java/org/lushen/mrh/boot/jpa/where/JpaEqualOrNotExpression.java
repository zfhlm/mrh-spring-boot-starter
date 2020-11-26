package org.lushen.mrh.boot.jpa.where;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

/**
 * jpa where 等于或不等 方法定义接口
 * 
 * @author hlm
 * @param <E>
 * @param <X>
 */
@SuppressWarnings("unchecked")
public interface JpaEqualOrNotExpression<E, X extends JpaEqualOrNotExpression<E, X>> {

	/**
	 * == 条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	<T> X equal(Function<E, T> function, T value);

	/**
	 * != 条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	<T> X notEqual(Function<E, T> function, T value);

	/**
	 * == 条件匹配
	 * 
	 * @param function
	 * @param otherFunction
	 * @return
	 */
	<T> X equal(Function<E, T> function, Function<E, T> otherFunction);

	/**
	 * != 条件匹配
	 * 
	 * @param function
	 * @param otherFunction
	 * @return
	 */
	<T> X notEqual(Function<E, T> function, Function<E, T> otherFunction);

	/**
	 * == 条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default <T> X equalIgnoreNull(Function<E, T> function, T value) {
		if(value != null) {
			equal(function, value);
		}
		return (X) this;
	}

	/**
	 * != 条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default <T> X notEqualIgnoreNull(Function<E, T> function, T value) {
		if(value != null) {
			notEqual(function, value);
		}
		return (X) this;
	}

	/**
	 * == 条件匹配，value为空字符串不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X equalIgnoreBlank(Function<E, String> function, String value) {
		if(StringUtils.isNotBlank(value)) {
			equal(function, value);
		}
		return (X) this;
	}

	/**
	 * != 条件匹配，value为空字符不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X notEqualIgnoreBlank(Function<E, String> function, String value) {
		if(StringUtils.isNotBlank(value)) {
			notEqual(function, value);
		}
		return (X) this;
	}

}
