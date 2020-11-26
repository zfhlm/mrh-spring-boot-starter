package org.lushen.mrh.boot.sequence.snowflake.support;

import org.lushen.mrh.boot.sequence.snowflake.SnowflakePayload;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 注册信息序列化工具
 * 
 * @author hlm
 */
public class NodeDataSerializer implements SnowflakeSerializer<SnowflakePayload, byte[]> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(SnowflakePayload payload) {
		try {
			return this.objectMapper.writeValueAsBytes(payload);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public SnowflakePayload deserialize(byte[] buffer) {
		try {
			return this.objectMapper.readValue(buffer, SnowflakePayload.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
