package org.lushen.mrh.boot.dfs.fastdfs;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.lushen.mrh.boot.dfs.FileStoreException;

/**
 * fastdfs客户端连接池对象工厂
 * 
 * @author hlm
 */
public class FastdfsPoolObjectFactory implements PooledObjectFactory<FastdfsPoolObject> {

	private TrackerClient trackerClient;	//tracker客户端

	public FastdfsPoolObjectFactory(TrackerClient trackerClient) {
		super();
		this.trackerClient = trackerClient;
	}

	@Override
	public PooledObject<FastdfsPoolObject> makeObject() throws Exception {

		//连接tracker
		TrackerServer trackerServer = trackerClient.getConnection();
		if (trackerServer == null) {
			throw new FileStoreException("Unable to connect tracker server.");
		}

		//配置storage客户端
		StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
		if (storageServer == null) {
			throw new FileStoreException("Unable to get storage server from tracker server.");
		}

		//创建客户端
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);

		//创建连接池对象
		FastdfsPoolObject object = new FastdfsPoolObject(trackerServer, storageServer, storageClient);

		return new DefaultPooledObject<FastdfsPoolObject>(object);
	}

	@Override
	public void destroyObject(PooledObject<FastdfsPoolObject> pooledObject) throws Exception {

		//关闭客户端连接
		FastdfsPoolObject object = pooledObject.getObject();
		object.close();

	}

	@Override
	public boolean validateObject(PooledObject<FastdfsPoolObject> pooledObject) {
		return true;
	}

	@Override
	public void activateObject(PooledObject<FastdfsPoolObject> p) throws Exception {}

	@Override
	public void passivateObject(PooledObject<FastdfsPoolObject> p) throws Exception {}

}
