package org.lushen.mrh.boot.jpa;

import java.util.List;

import org.lushen.mrh.boot.jpa.example.JpaExample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

/**
 * jpa 自定义查询接口
 * 
 * @author hlm
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface JpaExampleRepository<T, ID> extends CrudRepository<T, ID>, JpaSpecificationExecutor<T> {

	T findWithId(ID id);
	
	T findOne(@Nullable JpaExample<T> example);

	T findFirst(@Nullable JpaExample<T> example);

	List<T> findAll(@Nullable JpaExample<T> example);

	Page<T> findPage(@Nullable JpaExample<T> example, Pageable pageable);

	long count(@Nullable JpaExample<T> example);

}
