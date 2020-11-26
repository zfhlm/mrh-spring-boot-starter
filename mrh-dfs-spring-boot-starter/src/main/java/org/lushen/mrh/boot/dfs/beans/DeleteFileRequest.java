package org.lushen.mrh.boot.dfs.beans;

/**
 * 文件删除请求对象
 * 
 * @author hlm
 */
public final class DeleteFileRequest {

	private final String identity;

	public DeleteFileRequest(String identity) {
		super();
		this.identity = identity;
	}

	public String getIdentity() {
		return identity;
	}

}
