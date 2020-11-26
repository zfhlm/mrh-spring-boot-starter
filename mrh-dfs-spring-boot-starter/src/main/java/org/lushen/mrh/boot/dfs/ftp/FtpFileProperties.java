package org.lushen.mrh.boot.dfs.ftp;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;

/**
 * FTP文件存储客户端属性对象
 * 
 * @author hlm
 */
public class FtpFileProperties extends GenericObjectPoolConfig<FTPClient> implements InitializingBean {

	private Charset charset = Charset.forName("UTF-8");	//编码方式

	private int connectTimeoutInSeconds = 5;		//创建连接超时时间(毫秒)

	private String host;		//连接host

	private int port;			//连接端口

	private String username;	//连接账号

	private String password;	//连接密码

	@Override
	public void afterPropertiesSet() throws Exception {
		if(this.charset == null) {
			throw new IllegalArgumentException("The property 'charset' can't be null !");
		}
		if(this.connectTimeoutInSeconds <= 0) {
			throw new IllegalArgumentException("The property 'connectTimeoutInSeconds' can't be less than or equals zero !");
		}
		if(StringUtils.isBlank(this.host)) {
			throw new IllegalArgumentException("The property 'host' can't be null or blank !");
		}
		if(this.port <= 0) {
			throw new IllegalArgumentException("The property 'port' can't be less than or equals zero !");
		}
		if(StringUtils.isBlank(this.username)) {
			throw new IllegalArgumentException("The property 'username' can't be null or blank !");
		}
		if(StringUtils.isBlank(this.password)) {
			throw new IllegalArgumentException("The property 'password' can't be null or blank !");
		}
	}

	public int getConnectTimeoutInSeconds() {
		return connectTimeoutInSeconds;
	}

	public void setConnectTimeoutInSeconds(int connectTimeoutInSeconds) {
		this.connectTimeoutInSeconds = connectTimeoutInSeconds;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FtpFileProperties [charset=");
		builder.append(charset);
		builder.append(", connectTimeoutInSeconds=");
		builder.append(connectTimeoutInSeconds);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}

}
