package org.lushen.mrh.boot.autoconfigure.jdbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * jdbc 声明式事务自动配置，方法或类上面包含{@link Transactional}注解，发生任意异常回滚事务
 * 
 * @author hlm
 */
@Configuration
@ConditionalOnBean(org.springframework.transaction.PlatformTransactionManager.class)
public class JdbcTransactionAutoConfiguration {

	@Bean
	public TransactionInterceptor txAdvice(DataSourceTransactionManager txManager){
		List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Throwable.class)); 
		TransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
		MatchAlwaysTransactionAttributeSource transactionAttributeSource = new MatchAlwaysTransactionAttributeSource();
		transactionAttributeSource.setTransactionAttribute(transactionAttribute);
		return new TransactionInterceptor(txManager, transactionAttributeSource) ;
	}

	@Bean
	public PointcutAdvisor txPointcutAdvisor(@Autowired TransactionInterceptor txAdvice){
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setAdvice(txAdvice);
		advisor.setPointcut(new Pointcut() {
			@Override
			public MethodMatcher getMethodMatcher() {
				return new AnnotationClassOrMethodMatcher(Transactional.class);
			}
			@Override
			public ClassFilter getClassFilter() {
				return ClassFilter.TRUE;
			}
		});
		return advisor;
	}

	private class AnnotationClassOrMethodMatcher extends StaticMethodMatcher {

		private final ClassFilter classFilter;

		private final MethodMatcher methodMatcher;

		public AnnotationClassOrMethodMatcher(Class<? extends Annotation> annotationType) {
			super();
			this.classFilter = new AnnotationClassFilter(annotationType);
			this.methodMatcher = new AnnotationMethodMatcher(annotationType);
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			return this.methodMatcher.matches(method, targetClass) || this.classFilter.matches(targetClass);
		}

	}

}