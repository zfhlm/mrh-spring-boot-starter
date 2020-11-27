package org.lushen.mrh.boot.autoconfigure.log4j2;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.status.StatusLogger;
import org.lushen.mrh.boot.autoconfigure.ApplicationEnvironment;
import org.springframework.core.env.Environment;

/**
 * log4j2 lookup 扩展，在log4j2配置中可以通过 ${application:name} 读取应用配置
 * 
 * @author hlm
 */
@Plugin(name="application", category=StrLookup.CATEGORY)
public class Log4j2EnvironmentLookup extends AbstractLookup {

	private static final Logger LOGGER = StatusLogger.getLogger();

	@Override
	public String lookup(LogEvent event, String key) {
		try {
			Environment environment = ApplicationEnvironment.getEnvironment();
			String value = (environment != null? environment.getProperty(key) : null);
			LOGGER.debug("Getting application property [{}]. value [{}]", key, value);
			return value;
		} catch (Exception ex) {
			LOGGER.warn("Error while getting application property [{}].", key, ex);
			return null;
		}
	}

}
