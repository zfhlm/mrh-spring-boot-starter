package org.lushen.mrh.boot.dfs.fastdfs;

import java.io.Closeable;
import java.io.IOException;

import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

/**
 * fastdfs客户端连接池对象
 * 
 * @author hlm
 */
public class FastdfsPoolObject implements Closeable {

	private final TrackerServer trackerServer;

	private final StorageServer storageServer;

	private final StorageClient1 storageClient;

	public FastdfsPoolObject(TrackerServer trackerServer, StorageServer storageServer, StorageClient1 storageClient) {
		super();
		this.trackerServer = trackerServer;
		this.storageServer = storageServer;
		this.storageClient = storageClient;
	}

	public TrackerServer getTrackerServer() {
		return trackerServer;
	}

	public StorageServer getStorageServer() {
		return storageServer;
	}

	public StorageClient1 getStorageClient() {
		return storageClient;
	}

	@Override
	public void close() throws IOException {
		if(storageServer != null) {
			storageServer.close();
		}
		if(trackerServer != null) {
			trackerServer.close();
		}
	}

}
