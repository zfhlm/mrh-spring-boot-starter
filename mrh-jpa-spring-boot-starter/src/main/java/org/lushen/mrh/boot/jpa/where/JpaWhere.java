package org.lushen.mrh.boot.jpa.where;

/**
 * jpa where
 * 
 * @author hlm
 * @param <E>
 */
public interface JpaWhere<E> extends JpaBetweenExpression<E, JpaWhere<E>>, JpaEqualOrNotExpression<E, JpaWhere<E>>, JpaInOrNotExpression<E, JpaWhere<E>>,
		JpaIsNullOrNotExpression<E, JpaWhere<E>>, JpaLikeOrNotExpression<E, JpaWhere<E>>, JpaNestedExpression<JpaWhere<E>>, JpaThanExpression<E, JpaWhere<E>> {

}
