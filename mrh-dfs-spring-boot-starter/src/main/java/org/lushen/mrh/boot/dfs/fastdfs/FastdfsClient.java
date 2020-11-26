package org.lushen.mrh.boot.dfs.fastdfs;

import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.lushen.mrh.boot.dfs.FileStoreClient;
import org.lushen.mrh.boot.dfs.FileStoreException;
import org.lushen.mrh.boot.dfs.beans.DeleteFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileResponse;
import org.lushen.mrh.boot.dfs.beans.UploadFileRequest;
import org.lushen.mrh.boot.dfs.beans.UploadFileResponse;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * fastdfs 分布式文件存储客户端
 * 
 * @author hlm
 */
public class FastdfsClient implements FileStoreClient, InitializingBean, DisposableBean {

	private FastdfsProperties properties;

	private FastdfsPool fastdfsPool;

	public FastdfsClient(FastdfsProperties properties) {
		super();
		this.properties = properties;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		// 客户端配置
		Properties props = new Properties();
		if(ArrayUtils.isEmpty(properties.getTrackerServers())) {
			throw new FileStoreException("Fastdfs properties [trackerServers] is null or empty.");
		} else {
			props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, StringUtils.join(properties.getTrackerServers(), ","));
		}
		if(properties.getHttpTrackerHttpPort() != null) {
			props.put(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT, properties.getHttpTrackerHttpPort());
		}
		if(StringUtils.isNotBlank(properties.getCharset())) {
			props.put(ClientGlobal.PROP_KEY_CHARSET, properties.getCharset());
		}
		if(properties.getConnectTimeoutInSeconds() != null) {
			props.put(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, properties.getConnectTimeoutInSeconds());
		}
		if(StringUtils.isNotBlank(properties.getHttpAntiStealToken())) {
			props.put(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN, properties.getHttpAntiStealToken());
		}
		if(StringUtils.isNotBlank(properties.getHttpSecretKey())) {
			props.put(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY, properties.getHttpSecretKey());
		}
		if(properties.getNetworkTimeoutInSeconds() != null) {
			props.put(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, properties.getNetworkTimeoutInSeconds());
		}
		ClientGlobal.initByProperties(props);

		//初始化客户端连接池
		TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
		this.fastdfsPool = new FastdfsPool(trackerClient, properties);

	}

	@Override
	public void destroy() throws Exception {

		// 关闭连接池
		if(this.fastdfsPool != null) {
			this.fastdfsPool.close();
		}

	}

	private FastdfsPoolObject borrowObject() throws FileStoreException {

		// 从连接池获取客户端
		try {
			return this.fastdfsPool.borrowObject();
		} catch (Exception e) {
			throw new FileStoreException("Unable to create fastdfs client pool object.");
		}

	}

	private void returnObject(FastdfsPoolObject poolObject) {

		// 归还客户端到连接池
		if(poolObject != null) {
			this.fastdfsPool.returnObject(poolObject);
		}

	}

	@Override
	public UploadFileResponse uploadFile(UploadFileRequest request) throws FileStoreException {
		FastdfsPoolObject poolObject = borrowObject();
		try {
			return new InnerClient(poolObject.getStorageClient()).uploadFile(request);
		} finally {
			returnObject(poolObject);
		}
	}

	@Override
	public DownloadFileResponse downloadFile(DownloadFileRequest request) throws FileStoreException {
		FastdfsPoolObject poolObject = borrowObject();
		try {
			return new InnerClient(poolObject.getStorageClient()).downloadFile(request);
		} finally {
			returnObject(poolObject);
		}
	}

	@Override
	public void deleteFile(DeleteFileRequest request) throws FileStoreException {
		FastdfsPoolObject poolObject = borrowObject();
		try {
			new InnerClient(poolObject.getStorageClient()).deleteFile(request);
		} finally {
			returnObject(poolObject);
		}
	}

	/*
	 * fastdfs 文件上传逻辑实现
	 */
	private class InnerClient implements FileStoreClient {

		private StorageClient1 storageClient;

		public InnerClient(StorageClient1 storageClient) {
			super();
			this.storageClient = storageClient;
		}

		@Override
		public UploadFileResponse uploadFile(UploadFileRequest request) throws FileStoreException {

			//上传文件
			String suffix = StringUtils.substringAfterLast(request.getOriginalName(), ".");
			String fileId = null;
			try {
				fileId = this.storageClient.upload_file1(request.getBuffers(), suffix, new NameValuePair[0]);
			} catch (Exception e) {
				throw new FileStoreException("Upload file to fastdfs server failure.", e);
			}
			if(fileId == null) {
				throw new FileStoreException("Upload file to fastdfs server failure, reply null.");
			}

			//返回响应对象
			return new UploadFileResponse(fileId);
		}

		@Override
		public DownloadFileResponse downloadFile(DownloadFileRequest request) throws FileStoreException {

			//下载文件
			String fileId = request.getIdentity();
			byte[] buffers = null;
			try {
				buffers = this.storageClient.download_file1(fileId);
			} catch (Exception e) {
				throw new FileStoreException("Download file from fastdfs server failure.", e);
			}
			if(buffers == null) {
				throw new FileStoreException("Download file from fastdfs server failure, reply null.");
			}

			//返回响应对象
			return new DownloadFileResponse(buffers, fileId);
		}

		@Override
		public void deleteFile(DeleteFileRequest request) throws FileStoreException {

			// 删除文件
			int errcode = 0;
			try {
				errcode = this.storageClient.delete_file1(request.getIdentity());
			} catch (Exception e) {
				throw new FileStoreException("Delete file from fastdfs server failure.", e);
			}
			if(errcode != 0) {
				throw new FileStoreException(String.format("Delete file from fastdfs server failure, reply code [%s].", errcode));
			}

		}

	}

}
