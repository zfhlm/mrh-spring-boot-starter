package org.lushen.mrh.sequence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.lushen.mrh.sequence.single.SequenceKeyGenerator;
import org.lushen.mrh.sequence.single.UuidKeyGenerator;
import org.lushen.mrh.sequence.snowflake.SnowflakeConsumer;
import org.lushen.mrh.sequence.snowflake.SnowflakeFactory;
import org.lushen.mrh.sequence.snowflake.SnowflakeGenerator;
import org.lushen.mrh.sequence.snowflake.SnowflakeProperties;
import org.lushen.mrh.sequence.snowflake.factory.SnowflakeCuratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * generator 自动配置
 * 
 * @author helm
 */
@Configuration
@EnableConfigurationProperties
public class BootAutoConfiguration {

	private static final Log log = LogFactory.getLog(BootAutoConfiguration.class);

	@Bean
	@ConditionalOnMissingBean(KeyGenerator.class)
	public KeyGenerator uuidKeyGenerator() {
		log.info("Initialize uuid key generator.");
		return new UuidKeyGenerator();
	}

	@Bean
	@ConditionalOnBean(SequenceGenerator.class)
	public KeyGenerator sequenceKeyGenerator(@Autowired SequenceGenerator generator) {
		log.info("Initialize key generator with sequence generator.");
		return new SequenceKeyGenerator(generator);
	}

	@Bean
	@ConditionalOnBean(SnowflakeGenerator.class)
	@ConditionalOnMissingBean(SnowflakeConsumer.class)
	public SnowflakeConsumer snowflakeConsumer() {
		log.info("Intitle snowflake payload consumer.");
		return (payload -> {});
	}

	@Configuration
	@ConditionalOnBean(CuratorFramework.class)
	@ConditionalOnMissingBean(SequenceGenerator.class)
	public static class CuratorAutoConfiguration {

		@Bean
		@ConfigurationProperties(prefix="org.lushen.mrh.sequence")
		public SnowflakeProperties snowflakeProperties() {
			return new SnowflakeProperties();
		}

		@Bean
		public SnowflakeFactory snowflakeFactory(
				@Autowired CuratorFramework client, 
				@Autowired SnowflakeProperties properties, 
				@Autowired SnowflakeConsumer consumer) {
			log.info("Intitle snowflake curator factory.");
			return new SnowflakeCuratorFactory(client, properties, consumer);
		}

		@Bean
		public SnowflakeGenerator snowflakeGenerator(@Autowired SnowflakeFactory factory) {
			log.info("Intitle snowflake curator generator.");
			return new SnowflakeGenerator(factory);
		}

	}

}
