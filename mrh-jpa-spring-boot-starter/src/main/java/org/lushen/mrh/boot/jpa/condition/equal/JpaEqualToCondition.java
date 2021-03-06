package org.lushen.mrh.boot.jpa.condition.equal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * 等于 匹配 查询条件对象
 * 
 * @author hlm
 * @param <E>
 */
public class JpaEqualToCondition<E> implements JpaCondition<E> {

	private String singular;

	private Object value;

	public JpaEqualToCondition(String singular, Object value) {
		super();
		this.singular = singular;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<?> path = root.get(singular);
		return criteriaBuilder.equal(path, value);
	}

	@Override
	public String toString() {
		return StringUtils.join(singular, "=", value);
	}

}
