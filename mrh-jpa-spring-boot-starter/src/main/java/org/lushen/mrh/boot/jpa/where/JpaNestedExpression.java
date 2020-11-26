package org.lushen.mrh.boot.jpa.where;

/**
 * where 嵌套 方法定义接口
 * 
 * @author hlm
 * @param <X>
 */
public interface JpaNestedExpression<X extends JpaNestedExpression<X>> {

	/**
	 * or 条件
	 * 
	 * @return
	 */
	X or();

	/**
	 * and 条件
	 * 
	 * @return
	 */
	X and();

}
