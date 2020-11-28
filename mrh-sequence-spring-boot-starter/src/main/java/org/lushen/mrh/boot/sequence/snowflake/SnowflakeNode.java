package org.lushen.mrh.boot.sequence.snowflake;

import java.io.Serializable;

/**
 * snowflake 节点信息
 * 
 * @author hlm
 */
public class SnowflakeNode implements Serializable {

	private static final long serialVersionUID = -4668943158201315989L;

	private int centerId;			// 数据中心ID

	private int workerId;			// 记录中心ID

	public SnowflakeNode() {
		super();
	}

	public SnowflakeNode(int centerId, int workerId) {
		super();
		this.centerId = centerId;
		this.workerId = workerId;
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

	@Override
	public String toString() {
		return "SnowflakeNode [centerId=" + centerId + ", workerId=" + workerId + "]";
	}

}
