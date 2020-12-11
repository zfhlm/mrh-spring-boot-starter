package org.lushen.mrh.boot.dfs.support.image;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.dfs.support.FileType;
import org.lushen.mrh.boot.dfs.support.ImageCodec;

/**
 * 图片base64编解码
 * 
 * @author hlm
 */
public class ImageBase64Codec implements ImageCodec<byte[], String> {

	private static final String DATA_IMAGE_PNG = "data:image/png;base64,";

	private static final String DATA_IMAGE_GIF = "data:image/gif;base64,";

	private static final String DATA_IMAGE_JPEG = "data:image/jpeg;base64,";

	@Override
	public String encode(byte[] image) {

		if(image == null) {
			throw new IllegalArgumentException("image is null!");
		}

		// 获取文件类型
		FileType fileType = FileType.of(image, FileType.PNG, FileType.GIF, FileType.JPG);
		if(fileType == FileType.UNKNOWN) {
			throw new IllegalArgumentException("file type is illegal!");
		}

		// base64编码
		String base64 = Base64.encodeBase64String(image);

		// 添加前缀
		if(fileType == FileType.PNG) {
			return StringUtils.join(DATA_IMAGE_PNG, base64);
		}
		else if(fileType == FileType.GIF) {
			return StringUtils.join(DATA_IMAGE_GIF, base64);
		}
		else {
			return StringUtils.join(DATA_IMAGE_JPEG, base64);
		}
	}

	@Override
	public byte[] decode(String image) {

		if(image == null) {
			throw new IllegalArgumentException("image is null!");
		}

		// 截取前缀
		String base64 = StringUtils.trim(StringUtils.substringAfterLast(image, ","));

		return Base64.decodeBase64(base64);
	}

}
