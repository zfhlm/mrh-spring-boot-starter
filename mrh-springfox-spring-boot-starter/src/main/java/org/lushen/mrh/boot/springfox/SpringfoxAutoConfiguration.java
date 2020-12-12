package org.lushen.mrh.boot.springfox;

import java.util.Optional;

import org.lushen.mrh.boot.autoconfigure.support.enums.GenericEnum;
import org.lushen.mrh.boot.springfox.annotation.Doc;
import org.lushen.mrh.boot.springfox.plugin.DocPlugin;
import org.lushen.mrh.boot.springfox.plugin.GenericEnumPlugin;
import org.lushen.mrh.boot.springfox.plugin.JacksonPlugin;
import org.lushen.mrh.boot.springfox.plugin.ValidationPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * springfox 扩展自动配置
 * 
 * @author helm
 */
@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(ApiInfo.class)
@EnableConfigurationProperties
public class SpringfoxAutoConfiguration {

	private static final String PROP_PREFIX = "org.lushen.mrh.springfox";

	@Bean
	@ConfigurationProperties(prefix=PROP_PREFIX)
	public SpringfoxProperties springfoxProperties() {
		return new SpringfoxProperties();
	}

	@Bean
	@ConditionalOnMissingBean
	public ApiInfo apiInfo(@Autowired SpringfoxProperties properties) {
		return new ApiInfoBuilder()
				.title(properties.getTitle())
				.description(properties.getDescription())
				.version(properties.getVersion())
				.termsOfServiceUrl(properties.getTermsOfServiceUrl())
				.license(properties.getLicense())
				.licenseUrl(properties.getLicenseUrl())
				.contact(Optional.ofNullable(properties.getContact()).map(concact -> {
					return new Contact(concact.getName(), concact.getUrl(), concact.getEmail());
				}).orElse(null))
				.build();
	}

	@Bean
	@ConditionalOnMissingBean
	public Docket swaggerDocket(@Autowired ApiInfo apiInfo){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
				.select()
				.apis(handler -> handler.isAnnotatedWith(ApiOperation.class) || handler.isAnnotatedWith(Doc.class))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public DocPlugin docPlugin() {
		return new DocPlugin();
	}

	@Bean
	public JacksonPlugin jacksonPlugin(@Autowired ObjectMapper objectMapper) {
		return new JacksonPlugin(objectMapper.getDateFormat());
	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnClass(GenericEnum.class)
	public static class GenericEnumConfiguration {

		@Bean
		public GenericEnumPlugin genericEnumPlugin() {
			return new GenericEnumPlugin();
		}

	}

	@Configuration(proxyBeanMethods=false)
	@ConditionalOnClass(javax.validation.ConstraintValidator.class)
	public static class validationConfiguration {

		@Bean
		public ValidationPlugin validationPlugin() {
			return new ValidationPlugin();
		}

	}

}
