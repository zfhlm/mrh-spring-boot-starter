package org.lushen.mrh.boot.jpa.condition.than;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * 大于 匹配查询条件对象
 * 
 * @author hlm
 * @param <E>
 * @param <S>
 */
public class JpaMoreThanCondition<E, S extends Comparable<S>> implements JpaCondition<E> {

	private String singular;

	private S value;

	public JpaMoreThanCondition(String singular, S value) {
		super();
		this.singular = singular;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<S> path = root.get(singular);
		return criteriaBuilder.greaterThan(path, value);
	}

	@Override
	public String toString() {
		return StringUtils.join(singular, ">", value);
	}

}
