package org.lushen.mrh.boot.dfs.ftp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.lushen.mrh.boot.dfs.FileStoreClient;
import org.lushen.mrh.boot.dfs.FileStoreException;
import org.lushen.mrh.boot.dfs.beans.DeleteFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileResponse;
import org.lushen.mrh.boot.dfs.beans.UploadFileRequest;
import org.lushen.mrh.boot.dfs.beans.UploadFileResponse;
import org.springframework.beans.factory.DisposableBean;

/**
 * FTP文件存储客户端
 * 
 * @author hlm
 */
public class FtpFileClient implements FileStoreClient, DisposableBean {

	static final String PATH_SEQ = "/";

	static final String PATH_FORMAT = "yyyy/MM/dd";

	private FtpClientPool ftpClientPool;

	public FtpFileClient(FtpFileProperties properties) {
		super();
		this.ftpClientPool = new FtpClientPool(properties);
	}

	@Override
	public void destroy() throws Exception {
		if(this.ftpClientPool != null && ! this.ftpClientPool.isClosed()) {
			this.ftpClientPool.close();
		}
	}

	private FTPClient borrowObject() throws FileStoreException {
		try {
			return this.ftpClientPool.borrowObject();
		} catch (Exception e) {
			throw new FileStoreException("Create ftp store client failure!", e);
		}
	}

	private void returnObject(FTPClient client) {
		if(client != null) {
			this.ftpClientPool.returnObject(client);
		}
	}

	@Override
	public UploadFileResponse uploadFile(UploadFileRequest request) throws FileStoreException {
		FTPClient client = borrowObject();
		try {
			return new InnerClient(client).uploadFile(request);
		} finally {
			returnObject(client);
		}
	}

	@Override
	public DownloadFileResponse downloadFile(DownloadFileRequest request) throws FileStoreException {
		FTPClient client = borrowObject();
		try {
			return new InnerClient(client).downloadFile(request);
		} finally {
			returnObject(client);
		}
	}

	@Override
	public void deleteFile(DeleteFileRequest request) throws FileStoreException {
		FTPClient client = borrowObject();
		try {
			new InnerClient(client).deleteFile(request);
		} finally {
			returnObject(client);
		}
	}

	/*
	 * FTP 上传客户端实现
	 */
	private class InnerClient implements FileStoreClient {

		private FTPClient ftpClient;

		public InnerClient(FTPClient ftpClient) {
			super();
			this.ftpClient = ftpClient;
		}

		@Override
		public UploadFileResponse uploadFile(UploadFileRequest request) throws FileStoreException {

			try {

				//切换到存储目录
				String paths = new SimpleDateFormat(PATH_FORMAT).format(new Date());
				for(String path : StringUtils.split(paths, PATH_SEQ)) {
					this.ftpClient.makeDirectory(path);
					if( ! this.ftpClient.changeWorkingDirectory(path) ) {
						throw new FileStoreException(String.format("Change to ftp server directory %s failure, sub path [%s] !", paths, path));
					}
				}

				//上传文件
				String suffix = StringUtils.substringAfterLast(request.getOriginalName(), ".");
				String filename = StringUtils.join(UUID.randomUUID().toString(), ".", suffix);
				InputStream local = new ByteArrayInputStream(request.getBuffers());
				if( ! this.ftpClient.storeUniqueFile(filename, local) ) {
					throw new FileStoreException("Upload file to ftp server failure !");
				}

				//返回响应对象
				return new UploadFileResponse(StringUtils.join(paths, PATH_SEQ, filename));

			} catch(IOException e) {

				throw new FileStoreException("Upload file to ftp server failure !", e);

			}

		}

		@Override
		public DownloadFileResponse downloadFile(DownloadFileRequest request) throws FileStoreException {

			try {

				//文件属性
				String distinctKey = request.getIdentity();
				String paths = StringUtils.substring(distinctKey, 0, PATH_FORMAT.length());
				String filename = StringUtils.substring(distinctKey, PATH_FORMAT.length()+1);

				//切换到存储目录
				for(String path : StringUtils.split(paths, PATH_SEQ)) {
					if( ! this.ftpClient.changeWorkingDirectory(path) ) {
						throw new FileStoreException(String.format("Change to ftp server directory %s failure, sub path [%s] !", paths, path));
					}
				}

				//下载文件
				InputStream inputStream = this.ftpClient.retrieveFileStream(filename);
				if(inputStream == null) {
					throw new FileStoreException("Download file from ftp server failure, reply null !");
				}
				byte[] buffers = IOUtils.toByteArray(inputStream);
				inputStream.close();

				//返回响应对象
				return new DownloadFileResponse(buffers, request.getIdentity());

			} catch(IOException e) {

				throw new FileStoreException("Download file from ftp server failure !", e);

			}

		}

		@Override
		public void deleteFile(DeleteFileRequest request) throws FileStoreException {

			try {

				//文件属性
				String distinctKey = request.getIdentity();
				String paths = StringUtils.substring(distinctKey, 0, PATH_FORMAT.length());
				String filename = StringUtils.substring(distinctKey, PATH_FORMAT.length()+1);

				//切换到存储目录
				for(String path : StringUtils.split(paths, PATH_SEQ)) {
					if( ! this.ftpClient.changeWorkingDirectory(path) ) {
						throw new FileStoreException(String.format("Change to ftp server directory %s failure, sub path [%s] !", paths, path));
					}
				}

				//删除文件
				if( ! this.ftpClient.deleteFile(filename) ) {
					throw new FileStoreException("Delete file from ftp server failure !");
				}

			} catch (IOException e) {

				throw new FileStoreException("Delete file from ftp server failure !", e);

			}

		}

	}

}
