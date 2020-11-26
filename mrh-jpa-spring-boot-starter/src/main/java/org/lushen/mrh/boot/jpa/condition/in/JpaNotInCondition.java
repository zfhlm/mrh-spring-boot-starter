package org.lushen.mrh.boot.jpa.condition.in;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * not in 匹配查询条件对象
 * 
 * @author hlm
 * @param <E>
 * @param <S>
 */
public class JpaNotInCondition<E, S> implements JpaCondition<E> {

	private String singular;

	private List<S> values;

	public JpaNotInCondition(String singular, List<S> values) {
		super();
		this.singular = singular;
		this.values = values;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<?> path = root.get(singular);
		return criteriaBuilder.not(path.in(values));
	}

	@Override
	public String toString() {
		return StringUtils.join(singular, " not in (", StringUtils.join(values, ", "), ")");
	}

}
