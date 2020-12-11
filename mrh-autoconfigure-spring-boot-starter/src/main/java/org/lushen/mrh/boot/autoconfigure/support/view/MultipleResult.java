package org.lushen.mrh.boot.autoconfigure.support.view;

import java.util.Collection;

/**
 * 集合数据 状态码对象
 * 
 * @author hlm
 */
public final class MultipleResult extends GenericResult {

	private Object datas;

	public MultipleResult(int errcode, String errmsg) {
		super(errcode, errmsg);
	}

	public MultipleResult(int errcode, String errmsg, Collection<?> datas) {
		super(errcode, errmsg);
		this.datas = datas;
	}

	public MultipleResult(int errcode, String errmsg, Object[] datas) {
		super(errcode, errmsg);
		this.datas = datas;
	}

	/**
	 * 设置数据集
	 * 
	 * @param datas
	 */
	public void setDatas(Object datas) {
		this.datas = datas;
	}

	/**
	 * 获取数据集
	 * 
	 * @return
	 */
	public Object getDatas() {
		return datas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[datas=");
		builder.append(datas);
		builder.append(", errcode=");
		builder.append(errcode);
		builder.append(", errmsg=");
		builder.append(errmsg);
		builder.append("]");
		return builder.toString();
	}

}
