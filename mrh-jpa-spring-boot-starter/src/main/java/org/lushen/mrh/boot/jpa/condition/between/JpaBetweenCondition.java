package org.lushen.mrh.boot.jpa.condition.between;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * 范围 匹配查询条件对象
 * 
 * @author hlm
 * @param <E>
 * @param <S>
 */
public class JpaBetweenCondition<E, S extends Comparable<S>> implements JpaCondition<E> {

	private String singular;

	private S begin;

	private S end;

	public JpaBetweenCondition(String singular, S begin, S end) {
		super();
		this.singular = singular;
		this.begin = begin;
		this.end = end;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<S> path = root.get(singular);
		return criteriaBuilder.between(path, begin, end);
	}

	@Override
	public String toString() {
		return StringUtils.join(singular, " between ", begin, " and ", end);
	}

}
