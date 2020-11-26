package org.lushen.mrh.boot.dfs.beans;

/**
 * 文件下载请求对象
 * 
 * @author hlm
 */
public final class DownloadFileRequest {

	private final String identity;

	public DownloadFileRequest(String identity) {
		super();
		this.identity = identity;
	}

	public String getIdentity() {
		return identity;
	}

}
