package org.lushen.mrh.boot.sequence;

/**
 * 序列生成器
 * 
 * @author hlm
 */
public interface SequenceGenerator {

	/**
	 * 生成唯一序列号
	 * 
	 * @return
	 */
	public Long generate();

}
