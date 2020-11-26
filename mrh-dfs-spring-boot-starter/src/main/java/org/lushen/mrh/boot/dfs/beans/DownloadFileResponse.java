package org.lushen.mrh.boot.dfs.beans;

/**
 * 文件下载响应对象
 * 
 * @author hlm
 */
public final class DownloadFileResponse {

	private final byte[] buffers;

	private final String identity;

	public DownloadFileResponse(byte[] buffers, String identity) {
		super();
		this.buffers = buffers;
		this.identity = identity;
	}

	public byte[] getBuffers() {
		return buffers;
	}

	public String getIdentity() {
		return identity;
	}

}
