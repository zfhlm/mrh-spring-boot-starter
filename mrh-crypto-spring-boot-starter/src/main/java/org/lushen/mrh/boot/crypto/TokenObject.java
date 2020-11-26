package org.lushen.mrh.boot.crypto;

import java.util.Date;

/**
 * 令牌信息对象
 * 
 * @author hlm
 */
public abstract class TokenObject {

	private String subject;

	private Date expires;

	/**
	 * 获取主题
	 * 
	 * @return
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 获取到期时间
	 * 
	 * @return
	 */
	public Date getExpires() {
		return this.expires;
	}

	/**
	 * 设置到期时间
	 * 
	 * @param expires
	 */
	public void setExpires(Date expires) {
		this.expires = expires;
	}

}
