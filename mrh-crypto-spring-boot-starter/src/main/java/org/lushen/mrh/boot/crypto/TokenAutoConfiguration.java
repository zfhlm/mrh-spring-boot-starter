package org.lushen.mrh.boot.crypto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.crypto.none.NoneCryptoProvider;
import org.lushen.mrh.boot.crypto.token.CryptoTokenResolver;
import org.lushen.mrh.boot.crypto.token.JwtProperties;
import org.lushen.mrh.boot.crypto.token.JwtTokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT鉴权工具自动配置
 * 
 * @author hlm
 */
@Configuration(proxyBeanMethods=false)
public class TokenAutoConfiguration {

	private final Log log = LogFactory.getLog(TokenAutoConfiguration.class);

	private static final String NAMESPACE = "org.lushen.mrh";

	private static final String ENABLED = "enabled";

	private static final String TRUE = "true";

	private static final String JWT = NAMESPACE+".token.jwt";

	private static final String CRYPTO = NAMESPACE+".token.crypto";

	@Configuration(proxyBeanMethods=false)
	@EnableConfigurationProperties
	@ConditionalOnClass(io.jsonwebtoken.Jwt.class)
	@ConditionalOnProperty(prefix=JWT, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public class JwtTokenConfiguration {

		@Bean
		@ConfigurationProperties(prefix = JWT)
		public JwtProperties jwtProperties() {
			return new JwtProperties();
		}

		@Bean
		public TokenResolver jwtTokenResolver(@Autowired JwtProperties properties) {
			log.info(String.format("Initialize bean %s.", JwtTokenResolver.class));
			return new JwtTokenResolver(properties);
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnBean(CryptoProvider.class)
	@ConditionalOnMissingBean({TokenResolver.class, NoneCryptoProvider.class})
	@ConditionalOnProperty(prefix=CRYPTO, name=ENABLED, havingValue=TRUE, matchIfMissing=false)
	public class cryptoTokenConfiguration {

		@Bean
		public TokenResolver cryptoTokenResolver(CryptoProvider cryptoProvider) {
			log.info(String.format("Initialize bean %s.", CryptoTokenResolver.class));
			return new CryptoTokenResolver(cryptoProvider);
		}

	}

}
