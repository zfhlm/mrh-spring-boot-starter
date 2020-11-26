package org.lushen.mrh.boot.dfs.disk;

/**
 * 本地磁盘客户端配置
 * 
 * @author hlm
 */
public class DiskFileProperties {

	private String rootPath;	//文件存储一级目录路径

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DiskFileProperties [rootPath=");
		builder.append(rootPath);
		builder.append("]");
		return builder.toString();
	}

}
