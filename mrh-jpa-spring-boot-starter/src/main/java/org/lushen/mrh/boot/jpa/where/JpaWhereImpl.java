package org.lushen.mrh.boot.jpa.where;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.component.JpaComponent;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;
import org.lushen.mrh.boot.jpa.condition.between.JpaBetweenCondition;
import org.lushen.mrh.boot.jpa.condition.equal.JpaEqualCondition;
import org.lushen.mrh.boot.jpa.condition.equal.JpaEqualToCondition;
import org.lushen.mrh.boot.jpa.condition.equal.JpaNotEqualCondition;
import org.lushen.mrh.boot.jpa.condition.equal.JpaNotEqualToCondition;
import org.lushen.mrh.boot.jpa.condition.in.JpaInCondition;
import org.lushen.mrh.boot.jpa.condition.in.JpaNotInCondition;
import org.lushen.mrh.boot.jpa.condition.isnull.JpaIsNotNullCondition;
import org.lushen.mrh.boot.jpa.condition.isnull.JpaIsNullCondition;
import org.lushen.mrh.boot.jpa.condition.like.JpaFullLikeCondition;
import org.lushen.mrh.boot.jpa.condition.like.JpaLeftLikeCondition;
import org.lushen.mrh.boot.jpa.condition.like.JpaNotFullLikeCondition;
import org.lushen.mrh.boot.jpa.condition.like.JpaNotLeftLikeCondition;
import org.lushen.mrh.boot.jpa.condition.like.JpaNotRightLikeCondition;
import org.lushen.mrh.boot.jpa.condition.like.JpaRightLikeCondition;
import org.lushen.mrh.boot.jpa.condition.nested.JpaAndCondition;
import org.lushen.mrh.boot.jpa.condition.nested.JpaOrCondition;
import org.lushen.mrh.boot.jpa.condition.than.JpaLessThanCondition;
import org.lushen.mrh.boot.jpa.condition.than.JpaLessThanOrEqualCondition;
import org.lushen.mrh.boot.jpa.condition.than.JpaMoreThanCondition;
import org.lushen.mrh.boot.jpa.condition.than.JpaMoreThanOrEqualCondition;
import org.lushen.mrh.boot.jpa.proxy.invoke.ProxyInvoker;

/**
 * jpa where 默认实现
 * 
 * @author hlm
 * @param <E>
 */
public final class JpaWhereImpl<E> extends JpaComponent<E> implements JpaWhere<E> {

	private final Class<E> domainClass;

	private final List<JpaCondition<E>> conditions;

	public JpaWhereImpl(Class<E> domainClass, ProxyInvoker<E> proxyInvoker, List<JpaCondition<E>> conditions) {
		super(proxyInvoker);
		this.domainClass = domainClass;
		this.conditions = conditions;
	}

	@Override
	public <T extends Comparable<T>> JpaWhere<E> moreThan(Function<E, T> function, T value) {
		conditions.add(new JpaMoreThanCondition<E, T>(nameForGetter(function), value));
		return this;
	}

	@Override
	public <T extends Comparable<T>> JpaWhere<E> moreThanOrEqual(Function<E, T> function, T value) {
		conditions.add(new JpaMoreThanOrEqualCondition<E, T>(nameForGetter(function), value));
		return this;
	}

	@Override
	public <T extends Comparable<T>> JpaWhere<E> lessThan(Function<E, T> function, T value) {
		conditions.add(new JpaLessThanCondition<E, T>(nameForGetter(function), value));
		return this;
	}

	@Override
	public <T extends Comparable<T>> JpaWhere<E> lessThanOrEqual(Function<E, T> function, T value) {
		conditions.add(new JpaLessThanOrEqualCondition<E, T>(nameForGetter(function), value));
		return this;
	}

	@Override
	public JpaWhere<E> leftLike(Function<E, ?> function, String value) {
		conditions.add(new JpaLeftLikeCondition<E>(nameForGetter(function), value));
		return this;
	}

	@Override
	public JpaWhere<E> rightLike(Function<E, ?> function, String value) {
		conditions.add(new JpaRightLikeCondition<E>(nameForGetter(function), value));
		return this;
	}

	@Override
	public JpaWhere<E> fullLike(Function<E, ?> function, String value) {
		conditions.add(new JpaFullLikeCondition<E>(nameForGetter(function), value));
		return this;
	}

	@Override
	public JpaWhere<E> notLeftLike(Function<E, ?> function, String value) {
		conditions.add(new JpaNotLeftLikeCondition<E>(nameForGetter(function), value));
		return this;
	}

	@Override
	public JpaWhere<E> notRightLike(Function<E, ?> function, String value) {
		conditions.add(new JpaNotRightLikeCondition<E>(nameForGetter(function), value));
		return this;
	}

	@Override
	public JpaWhere<E> notFullLike(Function<E, ?> function, String value) {
		conditions.add(new JpaNotFullLikeCondition<E>(nameForGetter(function), value));
		return this;
	}

	@Override
	public <T> JpaWhere<E> isNull(Function<E, T> function) {
		conditions.add(new JpaIsNullCondition<E>(nameForGetter(function)));
		return this;
	}

	@Override
	public <T> JpaWhere<E> isNotNull(Function<E, T> function) {
		conditions.add(new JpaIsNotNullCondition<E>(nameForGetter(function)));
		return this;
	}

	@Override
	public <T> JpaWhere<E> in(Function<E, T> function, List<T> values) {
		conditions.add(new JpaInCondition<E, T>(nameForGetter(function), values));
		return this;
	}

	@Override
	public <T> JpaWhere<E> notIn(Function<E, T> function, List<T> values) {
		conditions.add(new JpaNotInCondition<E, T>(nameForGetter(function), values));
		return this;
	}

	@Override
	public <T> JpaWhere<E> equal(Function<E, T> function, T value) {
		conditions.add(new JpaEqualToCondition<E>(nameForGetter(function), value));
		return this;
	}

	@Override
	public <T> JpaWhere<E> notEqual(Function<E, T> function, T value) {
		conditions.add(new JpaNotEqualToCondition<E>(nameForGetter(function), value));
		return this;
	}

	@Override
	public <T> JpaWhere<E> equal(Function<E, T> function, Function<E, T> otherFunction) {
		conditions.add(new JpaEqualCondition<E>(nameForGetter(function), nameForGetter(otherFunction)));
		return this;
	}

	@Override
	public <T> JpaWhere<E> notEqual(Function<E, T> function, Function<E, T> otherFunction) {
		conditions.add(new JpaNotEqualCondition<E>(nameForGetter(function), nameForGetter(otherFunction)));
		return this;
	}

	@Override
	public <T extends Comparable<T>> JpaWhere<E> between(Function<E, T> function, T begin, T end) {
		conditions.add(new JpaBetweenCondition<E, T>(nameForGetter(function), begin, end));
		return this;
	}

	@Override
	public JpaWhere<E> or() {
		List<JpaCondition<E>> conditionsInOr = new ArrayList<JpaCondition<E>>();
		conditions.add(new JpaOrCondition<E>(conditionsInOr));
		return new JpaWhereImpl<E>(domainClass, getProxyInvoker(), conditionsInOr);
	}

	@Override
	public JpaWhere<E> and() {
		List<JpaCondition<E>> conditionsInAnd = new ArrayList<JpaCondition<E>>();
		conditions.add(new JpaAndCondition<E>(conditionsInAnd));
		return new JpaWhereImpl<E>(domainClass, getProxyInvoker(), conditionsInAnd);
	}

	@Override
	public String toString() {
		return new StringBuilder("where ").append(StringUtils.join(conditions, " and ")).toString();
	}

}
