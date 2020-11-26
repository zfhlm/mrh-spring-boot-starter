package org.lushen.mrh.boot.dfs.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * {@link FTPClient} 连接池
 * 
 * @author hlm
 */
public class FtpClientPool extends GenericObjectPool<FTPClient> {

	public FtpClientPool(FtpFileProperties properties) {
		super(new FtpClientPooledObjectFactory(properties), properties);
	}

}
