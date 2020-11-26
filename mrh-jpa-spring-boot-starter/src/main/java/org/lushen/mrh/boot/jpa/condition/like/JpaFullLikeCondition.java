package org.lushen.mrh.boot.jpa.condition.like;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * 左右模糊匹配查询条件对象
 * 
 * @author hlm
 * @param <E>
 */
public class JpaFullLikeCondition<E> implements JpaCondition<E> {

	private String singular;

	private String value;

	public JpaFullLikeCondition(String singular, String value) {
		super();
		this.singular = singular;
		this.value = new StringBuilder().append("%").append(value).append("%").toString();
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<String> path = root.get(singular);
		return criteriaBuilder.like(path, value);
	}

	@Override
	public String toString() {
		return StringUtils.join(singular, " like ", "%", value, "%");
	}

}
