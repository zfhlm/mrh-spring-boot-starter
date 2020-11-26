package org.lushen.mrh.boot.jpa.example;

import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.jpa.component.JpaComponent;
import org.lushen.mrh.boot.jpa.condition.JpaCondition;
import org.lushen.mrh.boot.jpa.order.JpaAscOrder;
import org.lushen.mrh.boot.jpa.order.JpaDescOrder;
import org.lushen.mrh.boot.jpa.order.JpaOrder;
import org.lushen.mrh.boot.jpa.proxy.CglibProxyProvider;
import org.lushen.mrh.boot.jpa.proxy.invoke.ProxyInstance;
import org.lushen.mrh.boot.jpa.where.JpaWhere;
import org.lushen.mrh.boot.jpa.where.JpaWhereImpl;

/**
 * 默认实现
 * 
 * @author hlm
 * @param <E>
 */
public final class JpaExampleImpl<E> extends JpaComponent<E> implements JpaExample<E> {

	private final Class<E> domainClass;

	private boolean distinct;

	private List<JpaCondition<E>> conditions;

	private List<JpaOrder<E>> orders;

	JpaExampleImpl(Class<E> domainClass) {
		super(new ProxyInstance<E>(domainClass, new CglibProxyProvider()));
		this.domainClass = domainClass;
	}

	@Override
	public JpaExample<E> distinct() {
		this.distinct = true;
		return this;
	}

	@Override
	public JpaWhere<E> whereAnd() {
		this.conditions = new ArrayList<JpaCondition<E>>();
		return new JpaWhereImpl<E>(domainClass, getProxyInvoker(), conditions);
	}

	@Override
	public JpaWhere<E> whereOr() {
		return whereAnd().or();
	}

	@Override
	public <T> JpaExample<E> orderByDesc(Function<E, T> function) {
		if(this.orders == null) {
			this.orders = new ArrayList<>();
		}
		this.orders.add(new JpaDescOrder<E>(getProxyInvoker(), function));
		return this;
	}

	@Override
	public <T> JpaExample<E> orderByAsc(Function<E, T> function) {
		if(this.orders == null) {
			this.orders = new ArrayList<>();
		}
		this.orders.add(new JpaAscOrder<E>(getProxyInvoker(), function));
		return this;
	}

	@Override
	public boolean isDistinct() {
		return this.distinct;
	}

	@Override
	public List<JpaCondition<E>> getConditions() {
		return conditions == null? Collections.emptyList() : Collections.unmodifiableList(this.conditions);
	}

	@Override
	public List<JpaOrder<E>> getOrders() {
		return orders == null? Collections.emptyList() : Collections.unmodifiableList(this.orders);
	}

	@Override
	public String toString() {
		List<String> list = new ArrayList<String>();
		if(distinct) {
			list.add("distinct");
		}
		list.add("from");
		list.add(domainClass.getSimpleName());
		if(CollectionUtils.isNotEmpty(conditions)) {
			list.add("where");
			list.add(StringUtils.join(conditions, " and "));
		}
		if(CollectionUtils.isNotEmpty(orders)) {
			list.add("order by");
			list.add("(");
			list.add(StringUtils.join(orders, SPACE));
			list.add(")");
		}
		return StringUtils.join(list, SPACE);
	}

}
