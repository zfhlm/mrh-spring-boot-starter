package org.lushen.mrh.sequence.snowflake;

/**
 * snowflake worker 工厂
 * 
 * @author hlm
 */
public interface SnowflakeFactory {

	/**
	 * 生成方法
	 * 
	 * @return not null forever
	 */
	public SnowflakeWorker build();

}
