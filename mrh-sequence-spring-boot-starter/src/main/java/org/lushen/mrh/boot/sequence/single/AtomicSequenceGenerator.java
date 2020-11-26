package org.lushen.mrh.boot.sequence.single;

import java.util.concurrent.atomic.AtomicLong;

import org.lushen.mrh.boot.sequence.SequenceGenerator;

/**
 * 递增序列生成器
 * 
 * @author hlm
 */
public class AtomicSequenceGenerator implements SequenceGenerator {

	private final AtomicLong atomic;

	public AtomicSequenceGenerator() {
		this(new AtomicLong(0));
	}

	public AtomicSequenceGenerator(AtomicLong atomic) {
		super();
		this.atomic = atomic;
	}

	@Override
	public Long generate() {
		return this.atomic.getAndIncrement();
	}

}
