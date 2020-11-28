package org.lushen.mrh.boot.springfox;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotationMetadata;

import springfox.documentation.oas.configuration.OpenApiDocumentationConfiguration;

/**
 * 自定义springfox装配注解，指定生效profiles
 * 
 * @author helm
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ActiveOpenApi.ActiveSwagger2Registrar.class)
public @interface ActiveOpenApi {

	/**
	 * 当profiles符合任意一个激活swagger2配置
	 * 
	 * @return
	 */
	String[] profiles();

	static class ActiveSwagger2Registrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

		private final Log log = LogFactory.getLog(getClass());

		private Environment environment;

		@Override
		public void setEnvironment(Environment environment) {
			this.environment = environment;
		}

		@Override
		public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

			// 读取注解配置
			Map<String, Object> attrs = importingClassMetadata.getAnnotationAttributes(ActiveOpenApi.class.getName());
			String[] profiles = (String[]) attrs.get("profiles");

			// 如果匹配profile
			if(this.environment.acceptsProfiles(Profiles.of(profiles))) {

				// spring未开启静态资源映射
				if( ! environment.getProperty("spring.resources.add-mappings", Boolean.TYPE, true) ) {
					log.warn("the application property [spring.resources.add-mappings] is false, unable to configure springfox ! ");
					return;
				}

				// see @EnableOpenApi
				BeanDefinitionBuilder springfoxBuilder = BeanDefinitionBuilder.genericBeanDefinition(OpenApiDocumentationConfiguration.class);
				registry.registerBeanDefinition(OpenApiDocumentationConfiguration.class.getSimpleName(), springfoxBuilder.getBeanDefinition());

				// 注入swagger2扩展配置
				BeanDefinitionBuilder expandBuilder = BeanDefinitionBuilder.genericBeanDefinition(SpringfoxAutoConfiguration.class);
				registry.registerBeanDefinition(SpringfoxAutoConfiguration.class.getName(), expandBuilder.getBeanDefinition());

			}

		}

	}

}
