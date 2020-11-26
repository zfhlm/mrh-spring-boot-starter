package org.lushen.mrh.boot.dfs.cos;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.dfs.FileStoreClient;
import org.lushen.mrh.boot.dfs.FileStoreException;
import org.lushen.mrh.boot.dfs.beans.DeleteFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileResponse;
import org.lushen.mrh.boot.dfs.beans.UploadFileRequest;
import org.lushen.mrh.boot.dfs.beans.UploadFileResponse;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;

/**
 * 腾讯云COS文件存储客户端
 * 
 * @author hlm
 */
public class TencentCosClient implements FileStoreClient, InitializingBean, DisposableBean {

	private TencentCosProperties properties;

	private COSClient cosClient;

	public TencentCosClient(TencentCosProperties properties) {
		super();
		this.properties = properties;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		COSCredentials credentials = new BasicCOSCredentials(this.properties.getSecretId(), this.properties.getSecretKey());
		ClientConfig clientConfig = new ClientConfig(new Region(properties.getRegion()));
		COSClient cosClient = new COSClient(credentials, clientConfig);
		this.cosClient = cosClient;
	}

	@Override
	public void destroy() throws Exception {
		if(this.cosClient != null) {
			this.cosClient.shutdown();
		}
	}

	@Override
	public UploadFileResponse uploadFile(UploadFileRequest request) throws FileStoreException {

		try {

			// 文件唯一key
			String suffix = StringUtils.substringAfterLast(request.getOriginalName(), ".");
			String key = StringUtils.join(UUID.randomUUID().toString(), ".", suffix);

			//上传文件
			String bucketName = this.properties.getBucket();
			InputStream input = new ByteArrayInputStream(request.getBuffers());
			this.cosClient.putObject(bucketName, key, input, new ObjectMetadata());

			//封装返回对象
			return new UploadFileResponse(key);

		} catch (Exception cause) {

			throw new FileStoreException("Upload file to tencent cos server failure !", cause);

		}

	}

	@Override
	public DownloadFileResponse downloadFile(DownloadFileRequest request) throws FileStoreException {

		try {

			//下载文件
			String bucketName = this.properties.getBucket();
			COSObject cosObject = this.cosClient.getObject(new GetObjectRequest(bucketName, request.getIdentity()));

			//封装响应对象
			byte[] buffers = IOUtils.toByteArray(cosObject.getObjectContent());
			return new DownloadFileResponse(buffers, request.getIdentity());

		} catch (Exception cause) {

			throw new FileStoreException("Download file from tencent cos server failure !", cause);

		}

	}

	@Override
	public void deleteFile(DeleteFileRequest request) throws FileStoreException {

		try {

			//删除文件
			String bucketName = this.properties.getBucket();
			this.cosClient.deleteObject(bucketName, request.getIdentity());

		} catch (Exception cause) {
			throw new FileStoreException("Delete file from tencent cos server failure !", cause);
		}

	}

}
