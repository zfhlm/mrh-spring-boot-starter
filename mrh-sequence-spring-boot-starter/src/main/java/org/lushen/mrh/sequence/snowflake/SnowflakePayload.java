package org.lushen.mrh.sequence.snowflake;

import java.io.Serializable;

/**
 * snowflake 注册信息
 * 
 * @author hlm
 */
public final class SnowflakePayload implements Serializable {

	private static final long serialVersionUID = -3216891116277674535L;

	private String id;				// 当前实例ID

	private String name;			// 当前实例名称

	private String address;			// 当前实例address

	private Integer port;			// 当前实例IP

	private String basePath;		// 当前根路径

	private String nodePath;		// 当前子路径

	private int centerId;			// 数据中心ID

	private int workerId;			// 记录中心ID

	private boolean isRegister;		// 是否已注册
	
	private long beginAt;			// 开始时间

	private long expiredAt;			// 过期时间

	private long createAt;			// 创建时间

	private long updateAt;			// 最后一次修改时间

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

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public int getCenterId() {
		return centerId;
	}

	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public boolean isRegister() {
		return isRegister;
	}

	public void setRegister(boolean isRegister) {
		this.isRegister = isRegister;
	}

	public long getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(long beginAt) {
		this.beginAt = beginAt;
	}

	public long getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(long expiredAt) {
		this.expiredAt = expiredAt;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	public long getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(long updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SnowflakePayload [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", port=");
		builder.append(port);
		builder.append(", basePath=");
		builder.append(basePath);
		builder.append(", nodePath=");
		builder.append(nodePath);
		builder.append(", centerId=");
		builder.append(centerId);
		builder.append(", workerId=");
		builder.append(workerId);
		builder.append(", isRegister=");
		builder.append(isRegister);
		builder.append(", beginAt=");
		builder.append(beginAt);
		builder.append(", expiredAt=");
		builder.append(expiredAt);
		builder.append(", createAt=");
		builder.append(createAt);
		builder.append(", updateAt=");
		builder.append(updateAt);
		builder.append("]");
		return builder.toString();
	}

}
