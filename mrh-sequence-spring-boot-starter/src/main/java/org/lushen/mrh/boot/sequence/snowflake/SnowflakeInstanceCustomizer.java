package org.lushen.mrh.boot.sequence.snowflake;

import org.lushen.mrh.boot.sequence.registry.SequenceInstance;
import org.lushen.mrh.boot.sequence.registry.SequenceInstanceCustomizer;

/**
 * snowflake 注册信息回调实现
 * 
 * @author hlm
 */
public class SnowflakeInstanceCustomizer implements SequenceInstanceCustomizer<SnowflakePayload> {

	@Override
	public void customize(SequenceInstance<SnowflakePayload> instance) {
		// nothing to do default
	}

}
