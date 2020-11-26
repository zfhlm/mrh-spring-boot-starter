package org.lushen.mrh.boot.jpa.order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * jpa 排序条件对象
 * 
 * @author hlm
 * @param <E>
 */
public interface JpaOrder<E> {

	/**
	 * 转换为jpa order
	 * 
	 * @param root
	 * @param criteriaBuilder
	 * @return
	 */
	public Order toOrder(Root<E> root, CriteriaBuilder criteriaBuilder);

}
