package org.lushen.mrh.boot.sequence.registry;

/**
 * 序列实例持久化接口
 * 
 * @author hlm
 * @param <T>
 */
public interface SequenceInstanceRepository<T> {

	/**
	 * 查询实例信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SequenceInstance<T> findById(String id) throws Exception;

	/**
	 * 保存实例信息
	 * 
	 * @param instance
	 * @throws Exception 
	 */
	public void save(SequenceInstance<T> instance) throws Exception;

	/**
	 * 更新实例信息
	 * 
	 * @param instance
	 * @throws Exception 
	 */
	public void update(SequenceInstance<T> instance) throws Exception;

	/**
	 * 移除实例信息
	 * 
	 * @param id
	 * @throws Exception 
	 */
	public void remove(String id) throws Exception;

	/**
	 * 清空所有实例信息
	 * 
	 * @throws Exception 
	 */
	public void trancate() throws Exception;

}
