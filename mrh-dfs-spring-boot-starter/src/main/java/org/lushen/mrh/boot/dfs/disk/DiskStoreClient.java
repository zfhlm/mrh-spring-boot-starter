package org.lushen.mrh.boot.dfs.disk;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.dfs.FileStoreClient;
import org.lushen.mrh.boot.dfs.FileStoreException;
import org.lushen.mrh.boot.dfs.beans.DeleteFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileResponse;
import org.lushen.mrh.boot.dfs.beans.UploadFileRequest;
import org.lushen.mrh.boot.dfs.beans.UploadFileResponse;
import org.springframework.beans.factory.InitializingBean;

/**
 * 本地磁盘存储客户端
 * 
 * @author hlm
 */
public class DiskStoreClient implements FileStoreClient, InitializingBean {

	private static final String PATH_FORMAT = "yyyy/MM/dd/";

	private DiskFileProperties properties;

	public DiskStoreClient(DiskFileProperties properties) {
		super();
		this.properties = properties;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		File file = new File(properties.getRootPath());
		if( ! file.exists()) {
			file.mkdirs();
		}
		if( ! file.isDirectory()) {
			throw new FileStoreException(String.format("The file [%s] is not a directory !", file.getPath()));
		}
		if( ! file.canRead()) {
			throw new FileStoreException(String.format("The file [%s] is not a readable directory !", file.getPath()));
		}
		if( ! file.canWrite()) {
			throw new FileStoreException(String.format("The file [%s] is not a writeable directory !", file.getPath()));
		}
	}

	@Override
	public UploadFileResponse uploadFile(UploadFileRequest request) throws FileStoreException {

		try {

			//获取文件存储路径
			String suffix = StringUtils.substringAfterLast(request.getOriginalName(), ".");
			String pathname = StringUtils.join(new SimpleDateFormat(PATH_FORMAT), UUID.randomUUID().toString(), ".", suffix);

			//存储文件
			File file = new File(new File(this.properties.getRootPath()), pathname);
			if(file.exists()) {
				throw new FileStoreException("Store file to disk failure, duplicate name !");
			}
			FileUtils.writeByteArrayToFile(file, request.getBuffers());

			//返回响应对象
			return new UploadFileResponse(pathname);

		} catch (FileStoreException cause) {

			throw cause;

		} catch (Exception cause) {

			throw new FileStoreException("Store file to disk failure !", cause);

		}

	}

	@Override
	public DownloadFileResponse downloadFile(DownloadFileRequest request) throws FileStoreException {

		try {

			//读取文件
			File file = new File(new File(this.properties.getRootPath()), request.getIdentity());
			if( ! file.exists()) {
				throw new FileStoreException("Download file from disk failure, not exist !");
			}
			byte[] buffers = FileUtils.readFileToByteArray(file);

			//返回响应对象
			return new DownloadFileResponse(buffers, request.getIdentity());

		} catch (FileStoreException cause) {

			throw cause;

		} catch (Exception cause) {

			throw new FileStoreException("Download file from disk failure !", cause);

		}

	}

	@Override
	public void deleteFile(DeleteFileRequest request) throws FileStoreException {

		try {

			//删除文件
			File file = new File(new File(this.properties.getRootPath()), request.getIdentity());
			if( ! file.exists() ) {
				throw new FileStoreException("Delete file from disk failure, not exist !");
			} else {	
				file.delete();
			}

		} catch (FileStoreException cause) {

			throw cause;

		} catch (Exception cause) {

			throw new FileStoreException("Delete file from disk failure !", cause);

		}

	}

}
