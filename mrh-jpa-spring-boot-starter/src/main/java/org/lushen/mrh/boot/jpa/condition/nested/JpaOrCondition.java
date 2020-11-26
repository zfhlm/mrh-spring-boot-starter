package org.lushen.mrh.boot.jpa.condition.nested;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;

/**
 * or 嵌套 多条件查询对象
 * 
 * @author hlm
 * @param <E>
 */
public class JpaOrCondition<E> implements JpaCondition<E> {

	private List<JpaCondition<E>> conditions;

	public JpaOrCondition(List<JpaCondition<E>> conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		if(conditions != null && ! conditions.isEmpty()) {
			List<Predicate> predicates = conditions.stream().map(e -> e.toPredicate(root, criteriaBuilder)).filter(e -> e != null).collect(Collectors.toList());
			if(! predicates.isEmpty()) {
				return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
			}
		}
		return null;
	}

	@Override
	public String toString() {
		if(CollectionUtils.isEmpty(this.conditions)) {
			return StringUtils.EMPTY;
		} else {
			return StringUtils.join(" ( ", StringUtils.join(this.conditions, " or "), " ) ");
		}
	}

}
