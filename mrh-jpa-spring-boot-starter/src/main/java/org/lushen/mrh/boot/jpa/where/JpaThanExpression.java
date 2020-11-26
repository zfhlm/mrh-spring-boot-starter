package org.lushen.mrh.boot.jpa.where;

import java.util.function.Function;

/**
 * jpa where 比较方法定义接口
 * 
 * @author hlm
 * @param <E>
 * @param <X>
 */
@SuppressWarnings("unchecked")
public interface JpaThanExpression<E, X extends JpaThanExpression<E, X>> {

	/**
	 * 大于 条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	<T extends Comparable<T>> X moreThan(Function<E, T> function, T value);

	/**
	 * 大于等于 条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	<T extends Comparable<T>> X moreThanOrEqual(Function<E, T> function, T value);

	/**
	 * 小于 条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	<T extends Comparable<T>> X lessThan(Function<E, T> function, T value);

	/**
	 * 小于等于 条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	<T extends Comparable<T>> X lessThanOrEqual(Function<E, T> function, T value);

	/**
	 * 大于 条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default <T extends Comparable<T>> X moreThanIgnoreNull(Function<E, T> function, T value) {
		if(value != null) {
			moreThan(function, value);
		}
		return (X) this;
	}

	/**
	 * 大于等于 条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default <T extends Comparable<T>> X moreThanOrEqualIgnoreNull(Function<E, T> function, T value) {
		if(value != null) {
			moreThanOrEqual(function, value);
		}
		return (X) this;
	}

	/**
	 * 小于 条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default <T extends Comparable<T>> X lessThanIgnoreNull(Function<E, T> function, T value) {
		if(value != null) {
			lessThan(function, value);
		}
		return (X) this;
	}

	/**
	 * 小于等于 条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default <T extends Comparable<T>> X lessThanOrEqualIgnoreNull(Function<E, T> function, T value) {
		if(value != null) {
			lessThanOrEqual(function, value);
		}
		return (X) this;
	}

}
