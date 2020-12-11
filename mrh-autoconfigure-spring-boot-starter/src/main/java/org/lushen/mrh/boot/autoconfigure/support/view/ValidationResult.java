package org.lushen.mrh.boot.autoconfigure.support.view;

import java.util.List;

/**
 * 验证信息 状态码对象
 * 
 * @author hlm
 */
public class ValidationResult extends GenericResult {

	private final List<Message> messages;

	public ValidationResult(int errcode, String errmsg, List<Message> messages) {
		super(errcode, errmsg);
		this.messages = messages;
	}

	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * 参数验证错误字段信息
	 * 
	 * @author hlm
	 */
	public static class Message {

		private String name;	//参数名称

		private String message;	//错误信息

		public Message(String name, String message) {
			super();
			this.name = name;
			this.message = message;
		}

		public String getName() {
			return name;
		}

		public String getMessage() {
			return message;
		}

	}

}
