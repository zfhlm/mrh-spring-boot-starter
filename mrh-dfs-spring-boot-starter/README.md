##### 分布式文件存储客户端

	mrh-dfs-spring-boot-starter
	
	对常用的文件存储，封装统一的文件存储客户端sdk

##### 客户端配置

	使用时需要在 application.properties 中指定客户端配置
	
	①，本地磁盘存储：
	
		org.lushen.mrh.dfs.disk.enabled=true
		org.lushen.mrh.dfs.disk.root-path=/usr/local/file
	
	②，ftp服务器存储：
		
		org.lushen.mrh.dfs.ftp.enabled=true
		org.lushen.mrh.dfs.ftp.charset=UTF-8
		org.lushen.mrh.dfs.ftp.connect-timeout-in-seconds=5
		org.lushen.mrh.dfs.ftp.host=127.0.0.1
		org.lushen.mrh.dfs.ftp.port=23
		org.lushen.mrh.dfs.ftp.username=user
		org.lushen.mrh.dfs.ftp.password=password
	
	③，腾讯云cos存储：
	
		org.lushen.mrh.dfs.cos.enabled=true
		org.lushen.mrh.dfs.cos.app-id=APPID
		org.lushen.mrh.dfs.cos.secret-id=KEY
		org.lushen.mrh.dfs.cos.secret-key=KEY
		org.lushen.mrh.dfs.cos.region=REGION
		org.lushen.mrh.dfs.cos.bucket=BUCKET
	
	④，fastdfs存储：
	
		org.lushen.mrh.dfs.fastdfs.enabled=true
		org.lushen.mrh.dfs.fastdfs.tracker-servers=
		org.lushen.mrh.dfs.fastdfs.http-tracker-http-port=
		org.lushen.mrh.dfs.fastdfs.charset=
		org.lushen.mrh.dfs.fastdfs.connect-timeout-in-seconds=
		org.lushen.mrh.dfs.fastdfs.http-anti-steal-token=
		org.lushen.mrh.dfs.fastdfs.http-secret-key=
		org.lushen.mrh.dfs.fastdfs.network-timeout-in-seconds=
		org.lushen.mrh.dfs.fastdfs.max-retris=

##### 使用示例

	注入文件客户端bean：
			
		@Autowired
		private FileStoreClient client;
	
	示例中，identify 文件系统标识当前文件的唯一ID.
	
	文件上传示例：
	
		try {
			UploadFileRequest request = new UploadFileRequest(buffers, originalName);
			UploadFileResponse response = client.uploadFile(request);
			String identify = response.getIdentity();
		} catch (FileStoreException e) {
			// TODO 上传失败
		}
	
	文件下载示例：
	
		try {
			DownloadFileRequest request = new DownloadFileRequest(identity);
			DownloadFileResponse response = client.downloadFile(request);
			byte[] buffer = response.getBuffers();
		} catch (FileStoreException e) {
			// TODO 下载失败
		}
	
	文件删除示例：
	
		try {
			DeleteFileRequest request = new DeleteFileRequest(identity);
			client.deleteFile(request);
		} catch (FileStoreException e) {
			// TODO 删除失败
		}

##### 后续集成和优化

	①，断点续传
	
	②，大文件处理
	
	③，其他存储客户端集成
