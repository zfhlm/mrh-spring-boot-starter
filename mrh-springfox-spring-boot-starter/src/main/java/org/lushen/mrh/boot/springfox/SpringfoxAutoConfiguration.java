package org.lushen.mrh.boot.springfox;

import java.util.Optional;

import org.lushen.mrh.boot.springfox.annotation.Doc;
import org.lushen.mrh.boot.springfox.plugin.DocPlugin;
import org.lushen.mrh.boot.springfox.plugin.GenericEnumPlugin;
import org.lushen.mrh.boot.springfox.plugin.JacksonPlugin;
import org.lushen.mrh.boot.springfox.plugin.ValidationPlugin;
import org.lushen.mrh.support.generic.enums.GenericEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.oas.configuration.OpenApiDocumentationConfiguration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * springfox 扩展自动配置
 * 
 * @author helm
 */
@Configuration
@ConditionalOnClass(EnableOpenApi.class)
@ConditionalOnBean(OpenApiDocumentationConfiguration.class)
@EnableConfigurationProperties(SpringfoxProperties.class)
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
	public DocPlugin docPlugin(@Autowired TypeResolver typeResolver) {
		return new DocPlugin(typeResolver);
	}

	@Configuration
	@ConditionalOnClass(GenericEnum.class)
	public static class GenericEnumConfiguration {

		@Bean
		public GenericEnumPlugin genericEnumPlugin() {
			return new GenericEnumPlugin();
		}

	}

	@Configuration
	@ConditionalOnClass(ObjectMapper.class)
	public static class JacksonConfiguration {

		@Bean
		public JacksonPlugin jacksonPlugin() {
			return new JacksonPlugin();
		}

	}

	@Configuration
	@ConditionalOnClass(javax.validation.ConstraintValidator.class)
	public static class validationConfiguration {

		@Bean
		public ValidationPlugin validationPlugin() {
			return new ValidationPlugin();
		}

	}

}
