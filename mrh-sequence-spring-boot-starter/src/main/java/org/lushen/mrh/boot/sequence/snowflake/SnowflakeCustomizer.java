package org.lushen.mrh.boot.sequence.snowflake;

/**
 * snowflake 信息到注册中心之前调用此接口
 * 
 * @author hlm
 */
public interface SnowflakeCustomizer {

	public void customize(SnowflakePayload payload);

}
