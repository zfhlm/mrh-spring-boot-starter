package org.lushen.mrh.boot.jpa.condition.isnull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * is null 匹配条件对象
 * 
 * @author hlm
 * @param <E>
 */
public class JpaIsNullCondition<E> implements JpaCondition<E> {

	private String singular;

	public JpaIsNullCondition(String singular) {
		super();
		this.singular = singular;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<?> path = root.get(singular);
		return criteriaBuilder.isNull(path);
	}

	@Override
	public String toString() {
		return StringUtils.join(this.singular, " is null ");
	}

}
