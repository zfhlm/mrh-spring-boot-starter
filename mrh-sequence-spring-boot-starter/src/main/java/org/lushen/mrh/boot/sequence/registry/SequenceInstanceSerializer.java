package org.lushen.mrh.boot.sequence.registry;

/**
 * 序列信息序列化接口
 * 
 * @author hlm
 * @param <T>
 */
public interface SequenceInstanceSerializer<T> {

	/**
	 * 序列化
	 * 
	 * @param payload
	 * @return
	 * @throws Exception 
	 */
	public byte[] serialize(SequenceInstance<T> instance) throws Exception;

	/**
	 * 反序列化
	 * 
	 * @param buffer
	 * @return
	 * @throws Exception 
	 */
	public SequenceInstance<T> deserialize(byte[] buffer) throws Exception;

}
