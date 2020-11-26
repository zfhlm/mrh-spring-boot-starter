package org.lushen.mrh.boot.jpa.condition.in;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * in 匹配查询条件查询对象
 * 
 * @author hlm
 * @param <E>
 * @param <S>
 */
public class JpaInCondition<E, S> implements JpaCondition<E> {

	private String singular;

	private List<S> values;

	public JpaInCondition(String singular, List<S> values) {
		super();
		this.singular = singular;
		this.values = values;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<S> path = root.get(singular);
		In<S> in = criteriaBuilder.in(path);
		for(S value : values) {
			in.value(value);
		}
		return in;
	}

	@Override
	public String toString() {
		return StringUtils.join(singular, " in (", StringUtils.join(values, ", "), ")");
	}

}
