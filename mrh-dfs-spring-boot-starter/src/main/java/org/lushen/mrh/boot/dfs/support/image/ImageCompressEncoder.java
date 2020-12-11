package org.lushen.mrh.boot.dfs.support.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.lushen.mrh.boot.dfs.support.ImageEncoder;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片压缩工具类
 * 
 * @author hlm
 */
public class ImageCompressEncoder implements ImageEncoder<byte[], byte[]> {

	private final double scale;

	private final long maxByte;

	public ImageCompressEncoder(long maxByte) {
		this(0.8D, maxByte);
	}

	public ImageCompressEncoder(double scale, long maxByte) {
		super();
		this.scale = scale;
		this.maxByte = maxByte;
	}

	@Override
	public byte[] encode(byte[] image) {
		try {
			byte[] bytes = image;
			while(true) {
				if(bytes.length <= this.maxByte) {
					break;
				}
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				Thumbnails.of(new ByteArrayInputStream(bytes)).scale(this.scale).toOutputStream(outputStream);
				bytes = outputStream.toByteArray();
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
