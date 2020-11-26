package org.lushen.mrh.sequence.snowflake;

/**
 * snowflake 信息到注册中心之前调用此接口
 * 
 * @author hlm
 */
public interface SnowflakeConsumer {

	public void consume(SnowflakePayload payload);

}
