package org.lushen.mrh.boot.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * 自定义查询接口工厂bean
 * 
 * @author hlm
 */
public class JpaExampleRepositoryFactoryBean<T extends Repository<S,ID>,S,ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {  

	public JpaExampleRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new JpaExampleRepositoryFactory(entityManager);
	}

}
