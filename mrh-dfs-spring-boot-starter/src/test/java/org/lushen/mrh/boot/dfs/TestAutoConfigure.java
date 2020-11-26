package org.lushen.mrh.boot.dfs;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lushen.mrh.boot.dfs.beans.DeleteFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileRequest;
import org.lushen.mrh.boot.dfs.beans.DownloadFileResponse;
import org.lushen.mrh.boot.dfs.beans.UploadFileRequest;
import org.lushen.mrh.boot.dfs.beans.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@SpringBootApplication
@ComponentScan(basePackageClasses=TestAutoConfigure.class)
public class TestAutoConfigure {

	@Autowired
	private FileStoreClient client;

	@Test
	public void testUpload() throws IOException {
		byte[] buffers = FileUtils.readFileToByteArray(new File(FileSystemView.getFileSystemView().getHomeDirectory(), "test.png"));
		String originalName = "test.png";
		try {
			UploadFileRequest request = new UploadFileRequest(buffers, originalName);
			UploadFileResponse response = client.uploadFile(request);
			String identify = response.getIdentity();
			System.out.println(identify);
		} catch (FileStoreException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDownload() {
		String identity = "";
		try {
			DownloadFileRequest request = new DownloadFileRequest(identity);
			DownloadFileResponse response = client.downloadFile(request);
			byte[] buffer = response.getBuffers();
			FileUtils.writeByteArrayToFile(new File(FileSystemView.getFileSystemView().getHomeDirectory(), "test2.png"), buffer);
		} catch (FileStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
		String identity = "";
		try {
			DeleteFileRequest request = new DeleteFileRequest(identity);
			client.deleteFile(request);
		} catch (FileStoreException e) {
			e.printStackTrace();
		}
	}

}
