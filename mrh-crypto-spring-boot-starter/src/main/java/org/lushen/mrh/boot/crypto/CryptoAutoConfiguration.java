package org.lushen.mrh.boot.crypto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.crypto.aes.AesCryptoProvider;
import org.lushen.mrh.boot.crypto.aes.AesProperties;
import org.lushen.mrh.boot.crypto.des.DesCryptoProvider;
import org.lushen.mrh.boot.crypto.des.DesProperties;
import org.lushen.mrh.boot.crypto.des3.Des3CryptoProvider;
import org.lushen.mrh.boot.crypto.des3.Des3Properties;
import org.lushen.mrh.boot.crypto.none.NoneCryptoProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加解密bean自动配置
 * 
 * @author hlm
 */
@Configuration
@EnableConfigurationProperties
public class CryptoAutoConfiguration {

	private static final String NAMESPACE = "org.lushen.mrh";

	private static final String ENABLED = "enabled";

	private static final String TRUE = "true";

	private static final String PROP_AES = NAMESPACE+".crypto.aes";

	private static final String PROP_DES = NAMESPACE+".crypto.des";

	private static final String PROP_DES3 = NAMESPACE+".crypto.des3";

	private final Log log = LogFactory.getLog(CryptoAutoConfiguration.class.getSimpleName());

	@Bean
	@ConditionalOnMissingBean(CryptoProvider.class)
	public CryptoProvider noneCryptoProvider() {
		return new NoneCryptoProvider();
	}

	@Configuration
	@ConditionalOnProperty(prefix=PROP_AES, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public class AesCryptoConfiguration {

		@Bean
		@ConfigurationProperties(prefix=PROP_AES)
		public AesProperties aesProperties() {
			return new AesProperties();
		}

		@Bean
		public CryptoProvider aesCryptoProvider(@Autowired AesProperties properties) {
			log.info(String.format("Initialize bean %s.", AesCryptoProvider.class.getName()));
			return new AesCryptoProvider(properties);
		}

	}

	@Configuration
	@ConditionalOnProperty(prefix=PROP_DES, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public class DesCryptoConfiguration {

		@Bean
		@ConfigurationProperties(prefix=PROP_DES)
		public DesProperties desProperties() {
			return new DesProperties();
		}

		@Bean
		public CryptoProvider desCryptoProvider(DesProperties properties) {
			log.info(String.format("Initialize bean %s.", DesCryptoProvider.class.getName()));
			return new DesCryptoProvider(properties);
		}

	}

	@Configuration
	@ConditionalOnProperty(prefix=PROP_DES3, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public class Des3CryptoConfiguration {

		@Bean
		@ConfigurationProperties(prefix=PROP_DES3)
		public Des3Properties des3Properties() {
			return new Des3Properties();
		}

		@Bean
		public CryptoProvider des3CryptoProvider(Des3Properties properties) {
			log.info(String.format("Initialize bean %s.", Des3CryptoProvider.class.getName()));
			return new Des3CryptoProvider(properties);
		}

	}

}
