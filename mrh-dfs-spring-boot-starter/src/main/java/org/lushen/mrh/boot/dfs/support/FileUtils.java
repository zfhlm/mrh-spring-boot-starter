package org.lushen.mrh.boot.dfs.support;

import static org.apache.commons.lang3.StringUtils.SPACE;

import java.io.File;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * {@link File} 工具类
 * 
 * @author hlm
 */
public final class FileUtils {

	private FileUtils() {}

	/**
	 * 文件是否存在
	 * 
	 * @param pathname
	 * @return
	 */
	public static final boolean exist(String pathname) {
		if(pathname == null) {
			return false;
		} else {
			return new File(pathname).exists();
		}
	}

	/**
	 * 文件是否存在
	 * 
	 * @param file
	 * @return
	 */
	public static final boolean exist(File file) {
		if(file == null) {
			return false;
		}
		return file.exists();
	}

	/**
	 * 计算文件大小，保留两位小数，自动计算单位
	 * 
	 * @param file
	 * @return
	 */
	public static final String byteCountToDisplaySize(File file) {
		if(file == null) {
			return byteCountToDisplaySize(0L);
		}
		return byteCountToDisplaySize(file.length());
	}

	/**
	 * 计算文件大小，保留两位小数，自动计算单位
	 * 
	 * @param size
	 * @return
	 */
	public static final String byteCountToDisplaySize(final long size) {
		return byteCountToDisplaySize(BigDecimal.valueOf(size), 2);
	}

	/**
	 * 计算文件大小，自动计算单位
	 * 
	 * @param size
	 * @param scale 保留小数点位数
	 * @return
	 */
	public static final String byteCountToDisplaySize(final BigDecimal size, int scale) {
		if(size.compareTo(FileUnit.ONE_KB) < 1) {
			return StringUtils.join(size.longValue(), SPACE, FileUnit.B);
		}
		else if(size.compareTo(FileUnit.ONE_MB) < 1) {
			return StringUtils.join(size.divide(FileUnit.ONE_KB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.KB);
		}
		else if(size.compareTo(FileUnit.ONE_GB) < 1) {
			return StringUtils.join(size.divide(FileUnit.ONE_MB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.MB);
		}
		else if(size.compareTo(FileUnit.ONE_TB) < 1) {
			return StringUtils.join(size.divide(FileUnit.ONE_GB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.GB);
		}
		else if(size.compareTo(FileUnit.ONE_PB) < 1) {
			return StringUtils.join(size.divide(FileUnit.ONE_TB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.TB);
		}
		else if(size.compareTo(FileUnit.ONE_EB) < 1) {
			return StringUtils.join(size.divide(FileUnit.ONE_PB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.PB);
		}
		else if(size.compareTo(FileUnit.ONE_YB) < 1) {
			return StringUtils.join(size.divide(FileUnit.ONE_EB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.EB);
		}
		else {
			return StringUtils.join(size.divide(FileUnit.ONE_YB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.YB);
		}
	}

	/**
	 * 计算文件的大小，保留2位小数，指定单位
	 * 
	 * @param size
	 * @param fileUnit
	 * @return
	 */
	public static final String byteCountSize(final long size, FileUnit fileUnit) {
		return byteCountSize(size, 2, fileUnit);
	}

	/**
	 * 计算文件的大小，指定单位
	 * 
	 * @param size
	 * @param scale
	 * @param fileUnit
	 * @return
	 */
	public static final String byteCountSize(final long size, int scale, FileUnit fileUnit) {
		return byteCountSize(BigDecimal.valueOf(size), scale, fileUnit);
	}

	/**
	 * 计算文件的大小，指定单位
	 * 
	 * @param size
	 * @param scale
	 * @param fileUnit
	 * @return
	 */
	public static final String byteCountSize(final BigDecimal size, int scale, FileUnit fileUnit) {
		if(FileUnit.B.equals(fileUnit)) {
			return StringUtils.join(size, SPACE, FileUnit.B);
		}
		else if(FileUnit.KB.equals(fileUnit)) {
			return StringUtils.join(size.divide(FileUnit.ONE_KB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.KB);
		}
		else if(FileUnit.MB.equals(fileUnit)) {
			return StringUtils.join(size.divide(FileUnit.ONE_MB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.MB);
		}
		else if(FileUnit.GB.equals(fileUnit)) {
			return StringUtils.join(size.divide(FileUnit.ONE_GB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.GB);
		}
		else if(FileUnit.TB.equals(fileUnit)) {
			return StringUtils.join(size.divide(FileUnit.ONE_TB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.TB);
		}
		else if(FileUnit.PB.equals(fileUnit)) {
			return StringUtils.join(size.divide(FileUnit.ONE_PB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.PB);
		}
		else if(FileUnit.EB.equals(fileUnit)) {
			return StringUtils.join(size.divide(FileUnit.ONE_EB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.EB);
		}
		else {
			return StringUtils.join(size.divide(FileUnit.ONE_YB).setScale(scale, BigDecimal.ROUND_HALF_UP), SPACE, FileUnit.YB);
		}
	}

	/**
	 * 文件大小单位
	 * 
	 * @author hlm
	 */
	public static enum FileUnit {

		B, KB, MB, GB, TB, PB, EB, ZB, YB;

		public static final BigDecimal ONE_KB = BigDecimal.valueOf(1024D);
		public static final BigDecimal ONE_MB = ONE_KB.multiply(ONE_KB);
		public static final BigDecimal ONE_GB = ONE_KB.multiply(ONE_MB);
		public static final BigDecimal ONE_TB = ONE_KB.multiply(ONE_GB);
		public static final BigDecimal ONE_PB = ONE_KB.multiply(ONE_TB);
		public static final BigDecimal ONE_EB = ONE_KB.multiply(ONE_PB);
		public static final BigDecimal ONE_ZB = ONE_KB.multiply(ONE_EB);
		public static final BigDecimal ONE_YB = ONE_KB.multiply(ONE_ZB);

	}

}
