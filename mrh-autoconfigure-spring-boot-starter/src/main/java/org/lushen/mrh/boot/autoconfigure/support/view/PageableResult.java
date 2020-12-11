package org.lushen.mrh.boot.autoconfigure.support.view;

import java.util.List;

/**
 * 分页数据 状态码对象
 * 
 * @author hlm
 */
public final class PageableResult extends GenericResult {

	private List<?> datas;

	private long total;

	public PageableResult(int errcode, String errmsg, List<?> datas, long total) {
		super(errcode, errmsg);
		this.datas = datas;
		this.total = total;
	}

	/**
	 * 设置当前分页数据列表
	 * 
	 * @return
	 */
	public void setDatas(List<?> datas) {
		this.datas = datas;
	}

	/**
	 * 设置符合条件记录总数
	 * 
	 * @return
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 获取当前分页数据列表
	 * 
	 * @return
	 */
	public List<?> getDatas() {
		return datas;
	}

	/**
	 * 获取符合条件记录总数
	 * 
	 * @return
	 */
	public long getTotal() {
		return total;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[datas=");
		builder.append(datas);
		builder.append(", total=");
		builder.append(total);
		builder.append(", errcode=");
		builder.append(errcode);
		builder.append(", errmsg=");
		builder.append(errmsg);
		builder.append("]");
		return builder.toString();
	}

}
