package org.lushen.mrh.boot.autoconfigure.support.view;

/**
 * 简单数据 状态码对象
 * 
 * @author hlm
 */
public final class SingleResult extends GenericResult {

	private Object data;

	public SingleResult(int errcode, String errmsg, Object data) {
		super(errcode, errmsg);
		this.data = data;
	}

	/**
	 * 设置数据对象
	 * 
	 * @return
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 获取数据对象
	 * 
	 * @return
	 */
	public Object getData() {
		return data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[data=");
		builder.append(data);
		builder.append(", errcode=");
		builder.append(errcode);
		builder.append(", errmsg=");
		builder.append(errmsg);
		builder.append("]");
		return builder.toString();
	}

}
