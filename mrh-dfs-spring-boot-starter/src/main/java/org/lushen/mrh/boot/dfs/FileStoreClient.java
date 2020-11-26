package org.lushen.mrh.boot.dfs;

import org.lushen.mrh.boot.dfs.beans.DeleteFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileResponse;
import org.lushen.mrh.boot.dfs.beans.UploadFileRequest;
import org.lushen.mrh.boot.dfs.beans.UploadFileResponse;

/**
 * 文件存储客户端
 * 
 * @author hlm
 */
public interface FileStoreClient {

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @return
	 * @throws FileStoreException
	 */
	UploadFileResponse uploadFile(UploadFileRequest request) throws FileStoreException;

	/**
	 * 下载文件
	 * 
	 * @param request
	 * @return
	 * @throws FileStoreException
	 */
	DownloadFileResponse downloadFile(DownloadFileRequest request) throws FileStoreException;

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @throws FileStoreException
	 */
	void deleteFile(DeleteFileRequest request) throws FileStoreException;

}
