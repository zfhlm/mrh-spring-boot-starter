package org.lushen.mrh.boot.autoconfigure.webmvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * validation 校验信息自动配置
 * 
 * @author hlm
 */
@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(HibernateValidator.class)
@ConditionalOnWebApplication(type=Type.SERVLET)
public class ValidationAutoConfiguration {

	private final Log log = LogFactory.getLog(ValidationAutoConfiguration.class);

	private static final String VALIDATION = "validation";

	private static final String VALIDATION_RESOURCES = "classpath*:*.properties";

	@Bean
	public WebMvcConfigurer validatorWebMvcConfigurer(@Autowired MessageSource parent) {

		log.info("Initialize validator WebMvcConfigurer, load validation message resouces.");

		List<String> basenames = new ArrayList<String>();
		try {
			ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
			for(Resource resource : patternResolver.getResources(VALIDATION_RESOURCES)) {
				String filename = StringUtils.substringBeforeLast(resource.getFilename(), ".");
				if(StringUtils.containsIgnoreCase(filename, VALIDATION)) {
					basenames.add(filename);
				}
			}
		} catch (Throwable cause) {}

		ReloadableResourceBundleMessageSource subMessageSource = new ReloadableResourceBundleMessageSource();
		subMessageSource.addBasenames(basenames.stream().toArray(len -> new String[len]));
		subMessageSource.setDefaultEncoding(Charset.defaultCharset().name());
		subMessageSource.setParentMessageSource(parent);

		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(subMessageSource);
		validator.setProviderClass(HibernateValidator.class);

		return new WebMvcConfigurer() {
			@Override
			public Validator getValidator() {
				return validator;
			}
		};
	}

}
