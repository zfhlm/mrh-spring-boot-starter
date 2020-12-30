package org.lushen.mrh.boot.sequence.snowflake;

import java.io.Serializable;

/**
 * snowflake 注册负载信息
 * 
 * @author hlm
 */
public class SnowflakePayload implements Serializable {

	private static final long serialVersionUID = -3216891116277674535L;

	private int centerId;			// 数据中心ID

	private int workerId;			// 记录中心ID

	private long effectAt;			// 生效时间

	private long expiredAt;			// 过期时间

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

	public long getEffectAt() {
		return effectAt;
	}

	public void setEffectAt(long effectAt) {
		this.effectAt = effectAt;
	}

	public long getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(long expiredAt) {
		this.expiredAt = expiredAt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[centerId=");
		builder.append(centerId);
		builder.append(", workerId=");
		builder.append(workerId);
		builder.append(", effectAt=");
		builder.append(effectAt);
		builder.append(", expiredAt=");
		builder.append(expiredAt);
		builder.append("]");
		return builder.toString();
	}

}
