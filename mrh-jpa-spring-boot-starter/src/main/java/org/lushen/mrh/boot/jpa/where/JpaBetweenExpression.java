package org.lushen.mrh.boot.jpa.where;

import java.util.function.Function;

/**
 * jpa where between 方法定义接口
 * 
 * @author hlm
 * @param <E>
 * @param <X>
 */
@SuppressWarnings("unchecked")
public interface JpaBetweenExpression<E, X extends JpaBetweenExpression<E, X>> {

	/**
	 * 范围 条件匹配
	 * 
	 * @param function
	 * @param begin
	 * @param end
	 * @return
	 */
	<T extends Comparable<T>> X between(Function<E, T> function, T begin, T end);

	/**
	 * 范围 条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param begin
	 * @param end
	 * @return
	 */
	default <T extends Comparable<T>> X betweenIgnoreNull(Function<E, T> function, T begin, T end) {
		if(begin != null && end != null) {
			between(function, begin, end);
		}
		return (X) this;
	}

}
