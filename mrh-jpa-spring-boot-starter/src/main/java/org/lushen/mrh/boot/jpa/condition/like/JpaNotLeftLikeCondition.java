package org.lushen.mrh.boot.jpa.condition.like;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * 左模糊不匹配查询条件对象
 * 
 * @author hlm
 * @param <E>
 */
public class JpaNotLeftLikeCondition<E> implements JpaCondition<E> {

	private String singular;

	private String value;

	public JpaNotLeftLikeCondition(String singular, String value) {
		super();
		this.singular = singular;
		this.value = new StringBuilder().append("%").append(value).toString();;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<String> path = root.get(singular);
		return criteriaBuilder.notLike(path, value);
	}

	@Override
	public String toString() {
		return StringUtils.join(singular, " not like ", "%", value);
	}

}
