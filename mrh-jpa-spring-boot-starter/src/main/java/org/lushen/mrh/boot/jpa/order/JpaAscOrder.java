package org.lushen.mrh.boot.jpa.order;

import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.lushen.mrh.boot.jpa.component.JpaComponent;
import org.lushen.mrh.boot.jpa.proxy.invoke.ProxyInvoker;

/**
 * jpa 升序排序对象
 * 
 * @author hlm
 * @param <E>
 */
public class JpaAscOrder<E> extends JpaComponent<E> implements JpaOrder<E> {

	private String singular;

	public JpaAscOrder(ProxyInvoker<E> proxyInvoker, Function<E, ?> function) {
		super(proxyInvoker);
		this.singular = nameForGetter(function);
	}

	/**
	 * 转换为jpa order
	 * 
	 * @param root
	 * @return
	 */
	public Order toOrder(Root<E> root, CriteriaBuilder criteriaBuilder) {
		Path<?> path = root.get(singular);
		Order order = new OrderImpl(path, true);
		return order;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(OrderByAsc, singular=");
		builder.append(singular);
		builder.append(")");
		return builder.toString();
	}

}
