package org.lushen.mrh.boot.sequence.snowflake;

import org.lushen.mrh.boot.sequence.registry.SequenceInstance;
import org.lushen.mrh.boot.sequence.registry.SequenceInstanceSerializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * snowflake 节点信息 序列化和反序列化实现
 * 
 * @author hlm
 */
public class SnowflakeInstanceSerializer implements SequenceInstanceSerializer<SnowflakeInstancePayload> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(SequenceInstance<SnowflakeInstancePayload> instance) throws Exception {
		return this.objectMapper.writeValueAsBytes(instance);
	}

	@Override
	public SequenceInstance<SnowflakeInstancePayload> deserialize(byte[] buffer) throws Exception {
		return this.objectMapper.readValue(buffer, new TypeReference<SequenceInstance<SnowflakeInstancePayload>>(){});
	}

}
