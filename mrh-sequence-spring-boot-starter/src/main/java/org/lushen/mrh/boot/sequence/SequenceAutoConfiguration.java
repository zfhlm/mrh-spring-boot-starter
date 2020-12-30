package org.lushen.mrh.boot.sequence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.lushen.mrh.boot.sequence.registry.SequenceInstanceRepository;
import org.lushen.mrh.boot.sequence.registry.supports.ZookeeperSequenceInstanceRepository;
import org.lushen.mrh.boot.sequence.single.Sequence2KeyGenerator;
import org.lushen.mrh.boot.sequence.single.UuidKeyGenerator;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeInstanceCustomizer;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeInstancePayload;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeInstanceSerializer;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeProperties;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeRegistryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * generator 自动配置
 * 
 * @author helm
 */
@Configuration(proxyBeanMethods=false)
public class SequenceAutoConfiguration {

	private final Log log = LogFactory.getLog(SequenceAutoConfiguration.class);

	@Bean
	@ConditionalOnBean(SequenceGenerator.class)
	public KeyGenerator sequence2KeyGenerator(@Autowired SequenceGenerator generator) {
		log.info("Initializing key generator with sequence generator.");
		return new Sequence2KeyGenerator(generator);
	}

	@Bean
	@ConditionalOnMissingBean(KeyGenerator.class)
	public KeyGenerator uuidKeyGenerator() {
		log.info("Initializing uuid key generator.");
		return new UuidKeyGenerator();
	}

	public class RegistryAutoConfiguration {

		@Bean
		@ConfigurationProperties(prefix="org.lushen.mrh.sequence")
		public SnowflakeProperties snowflakeProperties() {
			return new SnowflakeProperties();
		}

		@Bean
		@ConditionalOnMissingBean
		public SnowflakeInstanceSerializer snowflakeInstanceSerializer() {
			log.info("Initializing bean " + SnowflakeInstanceSerializer.class.getName());
			return new SnowflakeInstanceSerializer();
		}

		@Bean
		@ConditionalOnMissingBean
		public SnowflakeInstanceCustomizer snowflakeInstanceCustomizer() {
			log.info("Initializing bean " + SnowflakeInstanceCustomizer.class.getName());
			return new SnowflakeInstanceCustomizer();
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnBean(CuratorFramework.class)
	public class ZookeeperRegistryAutoConfiguration extends RegistryAutoConfiguration {

		@Bean
		public SequenceInstanceRepository<SnowflakeInstancePayload> sequenceInstanceRepository(
				@Autowired SnowflakeProperties properties,
				@Autowired SnowflakeInstanceSerializer instanceSerializer,
				@Autowired SnowflakeInstanceCustomizer instanceCustomizer,
				@Autowired CuratorFramework client) {
			log.info("Initializing bean " + ZookeeperSequenceInstanceRepository.class.getName());
			return new ZookeeperSequenceInstanceRepository<>(instanceSerializer, instanceCustomizer, client, properties.getBasePath());
		}

		@Bean
		public SequenceGenerator sequenceGenerator(
				@Autowired SnowflakeProperties properties,
				@Autowired SequenceInstanceRepository<SnowflakeInstancePayload> instanceRepository) {
			log.info("Initializing bean " + SnowflakeRegistryGenerator.class.getName());
			return new SnowflakeRegistryGenerator(properties.getLiveTimeMillis(), instanceRepository);
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnBean(RedisConnectionFactory.class)
	@ConditionalOnMissingBean(SequenceGenerator.class)
	public class RedisRegistryAutoConfiguration extends RegistryAutoConfiguration {



	}

}
