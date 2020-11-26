package org.lushen.mrh.boot.dfs.ftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.lushen.mrh.boot.dfs.FileStoreException;

/**
 * {@link FTPClient} 连接池对象工厂
 * 
 * @author hlm
 */
public class FtpClientPooledObjectFactory implements PooledObjectFactory<FTPClient> {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	private FtpFileProperties properties;

	public FtpClientPooledObjectFactory(FtpFileProperties properties) {
		super();
		this.properties = properties;
	}

	@Override
	public PooledObject<FTPClient> makeObject() throws Exception {

		try {

			//实例化客户端
			FTPClient client = new FTPClient();
			client.setControlEncoding(this.properties.getCharset().displayName());
			client.setConnectTimeout(this.properties.getConnectTimeoutInSeconds()*1000);

			//开启连接和验证
			client.connect(this.properties.getHost(), this.properties.getPort());
			client.login(this.properties.getUsername(), this.properties.getPassword());

			//验证是否连接成功
			int replyCode = client.getReplyCode(); 
			if( ! FTPReply.isPositiveCompletion(client.getReplyCode()) ){
				client.disconnect();
				throw new FileStoreException(String.format("Connect to ftp server failure, reply code [%s] !", replyCode));
			}

			//返回连接池对象
			return new DefaultPooledObject<FTPClient>(client);

		} catch (FileStoreException cause) {

			throw cause;

		} catch (Exception cause) {

			throw new FileStoreException("Make ftp client object failure !", cause);

		}

	}

	@Override
	public void destroyObject(PooledObject<FTPClient> pooledObject) throws Exception {

		try {

			//关闭ftp客户端连接
			FTPClient client = pooledObject.getObject();
			client.abort();

		} catch (Exception cause) {

			log.warn("Destroy ftp client object failure !", cause);

		}

	}

	@Override
	public boolean validateObject(PooledObject<FTPClient> pooledObject) {

		try {

			//验证ftp客户端是否可用
			FTPClient client = pooledObject.getObject();
			return client.isConnected() && client.isAvailable();

		} catch (Exception cause) {

			log.warn("Validate ftp client object failure !", cause);

			return false;

		}

	}

	@Override
	public void activateObject(PooledObject<FTPClient> pooledObject) throws Exception {

		try {

			//切换到一级根目录
			FTPClient client = pooledObject.getObject();
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			client.changeWorkingDirectory(FtpFileClient.PATH_SEQ);

		} catch (Exception cause) {

			throw new FileStoreException("Activate ftp client object failure !", cause);

		}

	}

	@Override
	public void passivateObject(PooledObject<FTPClient> pooledObject) throws Exception {}

}
