package org.lushen.mrh.boot.jpa.where;

import java.util.List;
import java.util.function.Function;

/**
 * jpa where in/not-in 方法定义接口
 * 
 * @author hlm
 * @param <E>
 * @param <X>
 */
@SuppressWarnings("unchecked")
public interface JpaInOrNotExpression<E, X extends JpaInOrNotExpression<E, X>> {
	
	/**
	 * in 条件匹配，values == null 或者 value.isEmpty() 不生效
	 * 
	 * @param function
	 * @param values
	 * @return
	 */
	default <T> X inIgnoreEmpty(Function<E, T> function, List<T> values) {
		if(values != null && ! values.isEmpty()) {
			in(function, values);
		}
		return (X) this;
	}

	/**
	 * in 条件匹配
	 * 
	 * @param function
	 * @param values
	 * @return
	 */
	<T> X in(Function<E, T> function, List<T> values);

	/**
	 * not in 条件匹配
	 * 
	 * @param function
	 * @param values
	 * @return
	 */
	<T> X notIn(Function<E, T> function, List<T> values);

}
