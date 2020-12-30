package org.lushen.mrh.boot.sequence.registry;

/**
 * 实例信息注册回调接口
 * 
 * @author hlm
 */
public interface SequenceInstanceCustomizer<T> {

	/**
	 * 回调处理方法
	 * 
	 * @param instance
	 */
	public void customize(SequenceInstance<T> instance);

}
