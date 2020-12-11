package org.lushen.mrh.boot.dfs.support.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.dfs.support.ImageCodec;

/**
 * 图片gzip压缩和解压缩
 * 
 * @author hlm
 */
public class ImageGzipCodec implements ImageCodec<byte[], byte[]> {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public byte[] encode(byte[] image) {

		if(image == null) {
			throw new IllegalArgumentException("image is null!");
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(image);
			gzip.flush();
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(gzip);
		}

	}

	@Override
	public byte[] decode(byte[] image) {

		if(image == null) {
			throw new IllegalArgumentException("image is null!");
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPInputStream uzip = null;
		try {
			uzip = new GZIPInputStream(new ByteArrayInputStream(image));
			byte[] buffer = new byte[1024];
			int n = -1;
			while ((n = uzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(uzip);
		}

	}

	private void close(Closeable closeable) {
		if(closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}

}
