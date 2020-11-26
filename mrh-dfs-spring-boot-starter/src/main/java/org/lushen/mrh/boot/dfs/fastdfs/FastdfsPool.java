package org.lushen.mrh.boot.dfs.fastdfs;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.csource.fastdfs.TrackerClient;

/**
 * fastdfs客户端连接池
 * 
 * @author hlm
 */
public class FastdfsPool extends GenericObjectPool<FastdfsPoolObject> {

	public FastdfsPool(TrackerClient trackerClient, FastdfsProperties properties) {
		super(new FastdfsPoolObjectFactory(trackerClient), properties);
	}

}
