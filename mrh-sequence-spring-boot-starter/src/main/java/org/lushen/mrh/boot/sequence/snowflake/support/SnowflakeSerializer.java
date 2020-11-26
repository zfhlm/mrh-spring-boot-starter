package org.lushen.mrh.boot.sequence.snowflake.support;

/**
 * snowflake 序列化接口
 * 
 * @author hlm
 * @param <F>
 * @param <T>
 */
interface SnowflakeSerializer<F, T> {

	/**
	 * 序列化
	 * 
	 * @param payload
	 * @return
	 */
	public T serialize(F payload);

	/**
	 * 反序列化
	 * 
	 * @param buffer
	 * @return
	 */
	public F deserialize(T buffer);

}
