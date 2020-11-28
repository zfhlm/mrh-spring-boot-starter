package org.lushen.mrh.boot.autoconfigure.validation;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
@Configuration
@ConditionalOnWebApplication(type=Type.SERVLET)
public class ValidationAutoConfiguration {

	private final Log log = LogFactory.getLog(ValidationAutoConfiguration.class);

	private static final String VALIDATION = "validation";

	private static final String VALIDATION_RESOURCES = "classpath*:*.properties";

	@Bean
	public WebMvcConfigurer validatorWebMvcConfigurer(@Autowired MessageSource messageSource) {
		log.info("Initialize validator WebMvcConfigurer, load validation message resouces.");
		return new ValidationWebMvcConfigurer(messageSource);
	}

	private static class ValidationWebMvcConfigurer implements WebMvcConfigurer {

		private MessageSource parent;

		public ValidationWebMvcConfigurer(MessageSource parent) {
			super();
			this.parent = parent;
		}

		@Override
		public Validator getValidator() {

			// 扫描 validation properties 文件
			List<String> basenames = new ArrayList<String>();
			try {
				ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
				for(Resource resource : patternResolver.getResources(VALIDATION_RESOURCES)) {
					String filename = StringUtils.substringBeforeLast(resource.getFilename(), ".");
					if(StringUtils.containsIgnoreCase(filename, VALIDATION)) {
						basenames.add(filename);
					}
				}
			} catch (Throwable cause) {
				// ignore
			}

			ReloadableResourceBundleMessageSource subMessageSource = new ReloadableResourceBundleMessageSource();
			subMessageSource.addBasenames(basenames.stream().toArray(len -> new String[len]));
			subMessageSource.setDefaultEncoding(Charset.defaultCharset().name());
			subMessageSource.setParentMessageSource(this.parent);

			LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
			validator.setValidationMessageSource(subMessageSource);
			validator.setProviderClass(HibernateValidator.class);

			return validator;
		}

	}

}
