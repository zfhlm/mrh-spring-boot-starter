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
public class JpaEqualCondition<E> implements JpaCondition<E> {

	private String singular;

	private String otherSingular;

	public JpaEqualCondition(String singular, String otherSingular) {
		super();
		this.singular = singular;
		this.otherSingular = otherSingular;
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<?> path = root.get(singular);
		Path<?> otherPath = root.get(otherSingular);
		return criteriaBuilder.equal(path, otherPath);
	}

	@Override
	public String toString() {
		return StringUtils.join(singular, "=", otherSingular);
	}

}
