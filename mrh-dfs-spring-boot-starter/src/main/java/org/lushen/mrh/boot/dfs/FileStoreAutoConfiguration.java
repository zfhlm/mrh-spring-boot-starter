package org.lushen.mrh.boot.dfs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.dfs.cos.TencentCosClient;
import org.lushen.mrh.boot.dfs.cos.TencentCosProperties;
import org.lushen.mrh.boot.dfs.disk.DiskFileProperties;
import org.lushen.mrh.boot.dfs.disk.DiskStoreClient;
import org.lushen.mrh.boot.dfs.fastdfs.FastdfsClient;
import org.lushen.mrh.boot.dfs.fastdfs.FastdfsProperties;
import org.lushen.mrh.boot.dfs.ftp.FtpFileClient;
import org.lushen.mrh.boot.dfs.ftp.FtpFileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储客户端自动配置
 * 
 * @author hlm
 */
@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties
public class FileStoreAutoConfiguration{

	private static final Log log = LogFactory.getLog(FileStoreAutoConfiguration.class.getSimpleName());

	private static final String NAMESPACE = "org.lushen.mrh";

	private static final String ENABLED = "enabled";

	private static final String TRUE = "true";

	private static final String FASTDFS_PREFIX = NAMESPACE+".dfs.fastdfs";

	private static final String DISK_PREFIX = NAMESPACE+".dfs.disk";

	private static final String COS_PREFIX = NAMESPACE+".dfs.cos";

	private static final String FTP_PREFIX = NAMESPACE+".dfs.ftp";

	/**
	 * fastdfs 文件存储客户端配置
	 */
	@Configuration(proxyBeanMethods=false)
	@ConditionalOnProperty(prefix=FASTDFS_PREFIX, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public static class FastdfsConfiguration {

		@Bean
		@ConfigurationProperties(prefix=FASTDFS_PREFIX)
		public FastdfsProperties fastdfsProperties() {
			return new FastdfsProperties();
		}

		@Bean
		@ConditionalOnBean(FastdfsProperties.class)
		public FileStoreClient fastdfsClient(@Autowired FastdfsProperties properties) {
			log.info(String.format("Initialize bean %s.", FastdfsClient.class.getName()));
			return new FastdfsClient(properties);
		}

	}

	/**
	 * 磁盘文件存储客户端配置
	 */
	@Configuration(proxyBeanMethods=false)
	@ConditionalOnProperty(prefix=DISK_PREFIX, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public static class DiskFileConfiguration {

		@Bean
		@ConfigurationProperties(prefix=DISK_PREFIX)
		public DiskFileProperties diskFileProperties() {
			return new DiskFileProperties();
		}

		@Bean
		@ConditionalOnBean(DiskFileProperties.class)
		public FileStoreClient diskFileClient(@Autowired DiskFileProperties properties) {
			log.info(String.format("Initialize bean %s.", DiskStoreClient.class.getName()));
			return new DiskStoreClient(properties);
		}

	}

	/**
	 * 腾讯云cos文件存储客户端配置
	 */
	@Configuration(proxyBeanMethods=false)
	@ConditionalOnProperty(prefix=COS_PREFIX, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public static class TencentCosConfiguration {

		@Bean
		@ConfigurationProperties(prefix=COS_PREFIX)
		public TencentCosProperties tencentCosProperties() {
			return new TencentCosProperties();
		}

		@Bean
		@ConditionalOnBean(TencentCosProperties.class)
		public FileStoreClient tencentCosClient(@Autowired TencentCosProperties properties) {
			log.info(String.format("Initialize bean %s.", TencentCosClient.class.getName()));
			return new TencentCosClient(properties);
		}

	}

	/**
	 * FTP文件存储客户端配置
	 */
	@Configuration(proxyBeanMethods=false)
	@ConditionalOnProperty(prefix=FTP_PREFIX, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public static class FtpFileConfiguration {

		@Bean
		@ConfigurationProperties(prefix=FTP_PREFIX)
		public FtpFileProperties ftpFileProperties() {
			return new FtpFileProperties();
		}

		@Bean
		@ConditionalOnBean(FtpFileProperties.class)
		public FileStoreClient ftpFileClient(@Autowired FtpFileProperties properties) {
			log.info(String.format("Initialize bean %s.", FtpFileClient.class.getName()));
			return new FtpFileClient(properties);
		}

	}

}
