package org.lushen.mrh.boot.dfs.fastdfs;

import java.util.Arrays;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * fastdfs 配置
 * 
 * @author hlm
 */
public class FastdfsProperties extends GenericObjectPoolConfig<FastdfsPoolObject> {

	private String[] trackerServers;			//tracker url地址

	private Integer httpTrackerHttpPort;		//tracker http端口

	private String charset;						//默认编码

	private Integer connectTimeoutInSeconds;	//连接超时时间

	private String httpAntiStealToken;			//是否开启防盗链

	private String httpSecretKey;				//http访问秘钥

	private Integer networkTimeoutInSeconds;	//网络超时时间(秒)

	private Integer maxRetris = new Integer(1);	//失败最大重试次数

	public String[] getTrackerServers() {
		return trackerServers;
	}

	public void setTrackerServers(String[] trackerServers) {
		this.trackerServers = trackerServers;
	}

	public Integer getHttpTrackerHttpPort() {
		return httpTrackerHttpPort;
	}

	public void setHttpTrackerHttpPort(Integer httpTrackerHttpPort) {
		this.httpTrackerHttpPort = httpTrackerHttpPort;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Integer getConnectTimeoutInSeconds() {
		return connectTimeoutInSeconds;
	}

	public void setConnectTimeoutInSeconds(Integer connectTimeoutInSeconds) {
		this.connectTimeoutInSeconds = connectTimeoutInSeconds;
	}

	public String getHttpAntiStealToken() {
		return httpAntiStealToken;
	}

	public void setHttpAntiStealToken(String httpAntiStealToken) {
		this.httpAntiStealToken = httpAntiStealToken;
	}

	public String getHttpSecretKey() {
		return httpSecretKey;
	}

	public void setHttpSecretKey(String httpSecretKey) {
		this.httpSecretKey = httpSecretKey;
	}

	public Integer getNetworkTimeoutInSeconds() {
		return networkTimeoutInSeconds;
	}

	public void setNetworkTimeoutInSeconds(Integer networkTimeoutInSeconds) {
		this.networkTimeoutInSeconds = networkTimeoutInSeconds;
	}

	public Integer getMaxRetris() {
		return maxRetris;
	}

	public void setMaxRetris(Integer maxRetris) {
		this.maxRetris = maxRetris;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FastdfsProperties [trackerServers=");
		builder.append(Arrays.toString(trackerServers));
		builder.append(", httpTrackerHttpPort=");
		builder.append(httpTrackerHttpPort);
		builder.append(", charset=");
		builder.append(charset);
		builder.append(", connectTimeoutInSeconds=");
		builder.append(connectTimeoutInSeconds);
		builder.append(", httpAntiStealToken=");
		builder.append(httpAntiStealToken);
		builder.append(", httpSecretKey=");
		builder.append(httpSecretKey);
		builder.append(", networkTimeoutInSeconds=");
		builder.append(networkTimeoutInSeconds);
		builder.append(", maxRetris=");
		builder.append(maxRetris);
		builder.append("]");
		return builder.toString();
	}

}
