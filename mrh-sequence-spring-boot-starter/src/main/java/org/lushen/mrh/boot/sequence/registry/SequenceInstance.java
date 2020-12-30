package org.lushen.mrh.boot.sequence.registry;

import java.io.Serializable;

/**
 * 序列实例信息
 * 
 * @author hlm
 * @param <T>
 */
public class SequenceInstance<T> implements Serializable {

	private static final long serialVersionUID = 6475888494514697768L;

	private String id;				// 实例信息ID

	private String name;			// 实例信息名称

	private String address;			// 实例信息address

	private Integer port;			// 实例信息IP

	private int version;			// 实例信息版本号

	private T payload;				// 自定义负载信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	@Override
	public SequenceInstance<T> clone() {
		SequenceInstance<T> instance = new SequenceInstance<>();
		instance.setId(this.id);
		instance.setName(this.name);
		instance.setAddress(this.address);
		instance.setPort(this.port);
		instance.setVersion(this.version);
		instance.setPayload(this.payload);
		return instance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", port=");
		builder.append(port);
		builder.append(", version=");
		builder.append(version);
		builder.append(", payload=");
		builder.append(payload);
		builder.append("]");
		return builder.toString();
	}

}
