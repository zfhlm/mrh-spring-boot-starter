package org.lushen.mrh.boot.sequence.single;

import org.lushen.mrh.boot.sequence.KeyGenerator;
import org.lushen.mrh.boot.sequence.SequenceGenerator;

/**
 * 序列key生成器
 * 
 * @author hlm
 */
public class Sequence2KeyGenerator implements KeyGenerator {

	private SequenceGenerator generator;

	public Sequence2KeyGenerator(SequenceGenerator generator) {
		super();
		this.generator = generator;
	}

	@Override
	public String generate() {
		return String.valueOf(this.generator.generate());
	}

}
