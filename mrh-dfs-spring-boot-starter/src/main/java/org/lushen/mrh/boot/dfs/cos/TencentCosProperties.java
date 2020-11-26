package org.lushen.mrh.boot.dfs.cos;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 腾讯云COS客户端配置
 * 
 * @author hlm
 */
public class TencentCosProperties implements InitializingBean {

	private String appId;		//appId

	private String secretId;	//秘钥ID

	private String secretKey;	//秘钥

	private String region;		//存储桶区域

	private String bucket;		//存储桶名称

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtils.isBlank(this.appId)) {
			throw new IllegalArgumentException("The property 'appId' can't be null or blank !");
		}
		if(StringUtils.isBlank(this.secretId)) {
			throw new IllegalArgumentException("The property 'secretId' can't be null or blank !");
		}
		if(StringUtils.isBlank(this.secretKey)) {
			throw new IllegalArgumentException("The property 'secretKey' can't be null or blank !");
		}
		if(StringUtils.isBlank(this.region)) {
			throw new IllegalArgumentException("The property 'region' can't be null or blank !");
		}
		if(StringUtils.isBlank(this.bucket)) {
			throw new IllegalArgumentException("The property 'bucket' can't be null or blank !");
		}
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TencentCosProperties [appId=");
		builder.append(appId);
		builder.append(", secretId=");
		builder.append(secretId);
		builder.append(", secretKey=");
		builder.append(secretKey);
		builder.append(", region=");
		builder.append(region);
		builder.append(", bucket=");
		builder.append(bucket);
		builder.append("]");
		return builder.toString();
	}

}
