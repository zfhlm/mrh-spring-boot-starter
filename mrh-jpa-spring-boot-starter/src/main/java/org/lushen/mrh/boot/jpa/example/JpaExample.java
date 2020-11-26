package org.lushen.mrh.boot.jpa.example;

import java.util.List;
import java.util.function.Function;

import org.lushen.mrh.boot.jpa.condition.JpaCondition;
import org.lushen.mrh.boot.jpa.order.JpaOrder;
import org.lushen.mrh.boot.jpa.where.JpaWhere;

/**
 * jpa 动态嵌套条件查询对象
 * 
 * @author hlm
 * @param <E>
 */
public interface JpaExample<E> {

	/**
	 * 创建 example对象
	 * 
	 * @return
	 */
	public static <E> JpaExample<E> from(Class<E> domainClass) {
		return new JpaExampleImpl<E>(domainClass);
	}

	/**
	 * distinct
	 * 
	 * @return
	 */
	JpaExample<E> distinct();

	/**
	 * where and条件查询对象
	 * 
	 * @return
	 */
	JpaWhere<E> whereAnd();

	/**
	 * where or条件查询对象
	 * 
	 * @return
	 */
	JpaWhere<E> whereOr();

	/**
	 * desc 排序条件
	 * 
	 * @param function
	 * @return
	 */
	<T> JpaExample<E> orderByDesc(Function<E, T> function);

	/**
	 * asc 排序条件
	 * 
	 * @param function
	 * @return
	 */
	<T> JpaExample<E> orderByAsc(Function<E, T> function);

	/**
	 * 是否distinct
	 * 
	 * @return
	 */
	boolean isDistinct();

	/**
	 * where 条件表达式集
	 * 
	 * @return
	 */
	List<JpaCondition<E>> getConditions();

	/**
	 * order by 条件集
	 * 
	 * @return
	 */
	List<JpaOrder<E>> getOrders();

}
