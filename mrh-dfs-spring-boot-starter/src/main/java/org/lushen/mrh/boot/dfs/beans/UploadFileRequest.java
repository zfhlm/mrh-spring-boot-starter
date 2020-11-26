package org.lushen.mrh.boot.dfs.beans;

/**
 * 文件上传请求对象
 * 
 * @author hlm
 */
public final class UploadFileRequest {

	private final byte[] buffers;

	private final String originalName;

	public UploadFileRequest(byte[] buffers, String originalName) {
		super();
		this.buffers = buffers;
		this.originalName = originalName;
	}

	public byte[] getBuffers() {
		return buffers;
	}

	public String getOriginalName() {
		return originalName;
	}

}
