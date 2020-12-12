package org.lushen.mrh.boot.autoconfigure.support.page;

import java.util.List;

/**
 * 分页数据接口
 * 
 * @author hlm
 * @param <E>
 */
public interface Page<E> {

	/**
	 * 当前分页数据
	 * 
	 * @return
	 */
	public List<E> getDatas();

	/**
	 * 总记录条数
	 * 
	 * @return
	 */
	public long getTotals();

}
