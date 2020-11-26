package org.lushen.mrh.boot.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * 自定义查询接口bean工厂
 * 
 * @author hlm
 */
public class JpaExampleRepositoryFactory extends JpaRepositoryFactory  {

	public JpaExampleRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
		if(JpaExampleRepository.class.isAssignableFrom(information.getRepositoryInterface())){
			return new JpaExampleRepositoryImpl<Object, Serializable>((Class<Object>)information.getDomainType(), entityManager);
		} else {
			return super.getTargetRepository(information, entityManager);
		}
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		if(JpaExampleRepository.class.isAssignableFrom(metadata.getRepositoryInterface())){
			return JpaExampleRepository.class;
		} else {
			return super.getRepositoryBaseClass(metadata);
		}
	}

}