package org.lushen.mrh.boot.sequence.registry.supports;

import org.lushen.mrh.boot.sequence.registry.SequenceInstance;
import org.lushen.mrh.boot.sequence.registry.SequenceInstanceRepository;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * redis 序列实例持久化实现
 * 
 * @author hlm
 * @param <T>
 */
public class RedisSequenceInstanceRepository<T> implements SequenceInstanceRepository<T>, InitializingBean, DisposableBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void destroy() throws Exception {
		
	}

	@Override
	public SequenceInstance<T> findById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(SequenceInstance<T> instance) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SequenceInstance<T> instance) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trancate() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
