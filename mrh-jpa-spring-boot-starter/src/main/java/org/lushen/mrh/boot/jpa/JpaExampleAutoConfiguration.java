package org.lushen.mrh.boot.jpa;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * jpa 自动配置
 * 
 * @author helm
 */
@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(PhysicalNamingStrategy.class)
@ConditionalOnBean(RepositoryConfigurationExtension.class)
public class JpaExampleAutoConfiguration {

	/**
	 * 配置命名策略，全部名称转换为大写
	 */
	@Bean
	public PhysicalNamingStrategy upCasePhysicalNamingStrategy() {
		Function<Identifier, Identifier> func = (name) -> {
			return (name == null? null : Identifier.toIdentifier(StringUtils.upperCase(name.getText())));
		};
		return new PhysicalNamingStrategy() {
			@Override
			public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
				return func.apply(name);
			}
			@Override
			public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
				return func.apply(name);
			}
			@Override
			public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
				return func.apply(name);
			}
			@Override
			public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
				return func.apply(name);
			}
			@Override
			public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
				return func.apply(name);
			}
		};
	}

}
