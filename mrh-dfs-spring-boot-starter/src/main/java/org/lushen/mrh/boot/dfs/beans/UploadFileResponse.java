package org.lushen.mrh.boot.dfs.beans;

/**
 * 文件上传响应对象
 * 
 * @author hlm
 */
public final class UploadFileResponse {

	private final String identity;

	public UploadFileResponse(String identity) {
		super();
		this.identity = identity;
	}

	public String getIdentity() {
		return identity;
	}

}
