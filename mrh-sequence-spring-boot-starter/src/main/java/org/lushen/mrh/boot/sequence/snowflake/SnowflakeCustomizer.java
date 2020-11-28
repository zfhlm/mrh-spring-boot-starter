package org.lushen.mrh.boot.sequence.snowflake;

/**
 * snowflake payload 注册或更新，前置回调接口
 * 
 * @author hlm
 */
public interface SnowflakeCustomizer {

	public void customize(SnowflakePayload payload);

}
