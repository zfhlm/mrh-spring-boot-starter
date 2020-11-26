package org.lushen.mrh.sequence.snowflake;

/**
 * snowflake 配置
 * 
 * @author hlm
 */
public class SnowflakeProperties {

	public static final String DEFAULT_BASE_PATH = "/snowflake/default";

	public static final long DEFAULT_LIVE_TIME_MILLIS = 5*60*1000L;	

	private String basePath = DEFAULT_BASE_PATH;

	private long liveTimeMillis  = DEFAULT_LIVE_TIME_MILLIS;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public long getLiveTimeMillis() {
		return liveTimeMillis;
	}

	public void setLiveTimeMillis(long liveTimeMillis) {
		this.liveTimeMillis = liveTimeMillis;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SnowflakeProperties [basePath=");
		builder.append(basePath);
		builder.append(", liveTimeMillis=");
		builder.append(liveTimeMillis);
		builder.append("]");
		return builder.toString();
	}

}
