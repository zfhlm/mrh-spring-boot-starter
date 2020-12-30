package org.lushen.mrh.boot.sequence.single;

import org.lushen.mrh.boot.sequence.SequenceGenerator;

/**
 * 推特snowflake 序列ID生成器
 * 
 * @author hlm
 */
public class SnowflakeSequenceGenerator implements SequenceGenerator {

	public static final long twepoch = 1606355403673L;

	public static final long workerIdBits = 5L;

	public static final long centerIdBits = 5L;

	public static final long maxWorkerId = -1L ^ (-1L << workerIdBits);

	public static final long maxCenterId = -1L ^ (-1L << centerIdBits);

	public static final long sequenceBits = 12L;

	public static final long workerIdShift = sequenceBits;

	public static final long datacenterIdShift = sequenceBits + workerIdBits;

	public static final long timestampLeftShift = sequenceBits + workerIdBits + centerIdBits;

	public static final long sequenceMask = -1L ^ (-1L << sequenceBits);

	private int centerId;

	private int workerId;

	private long sequence = 0L;

	private long lastTimestamp;

	private long expiredAt;

	public SnowflakeSequenceGenerator(int centerId, int workerId) {
		this(centerId, workerId, System.currentTimeMillis(), Long.MAX_VALUE);
	}

	public SnowflakeSequenceGenerator(int centerId, int workerId, long lastTimestamp, long expiredAt) {
		super();
		if (centerId > maxCenterId || centerId < 0) {
			throw new IllegalArgumentException(String.format("centerId can't be greater than %d or less than 0", maxCenterId));
		}
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", maxWorkerId));
		}
		this.centerId = centerId;
		this.workerId = workerId;
		this.lastTimestamp = lastTimestamp;
		this.expiredAt = expiredAt;
	}

	@Override
	public synchronized Long generate() {

		long timestamp = timeGen();

		// 发生时钟回拨
		if (timestamp < lastTimestamp) {
			throw new RuntimeException("snowflake clock move back!");
		}

		// 当前毫秒产生的ID不足，阻塞到下一毫秒
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}

		// 已到过期时间
		if(expiredAt <= timestamp) {
			throw new RuntimeException("snowflake expire at " + this.expiredAt + "!");
		}

		// 更新最后一次时间
		lastTimestamp = timestamp;

		// 生成并返回唯一序列
		return ((timestamp - twepoch) << timestampLeftShift) | (centerId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
	}

	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

}
