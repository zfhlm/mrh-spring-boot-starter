package org.lushen.mrh.boot.autoconfigure.jackson;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson 自动配置
 * 
 * @author hlm
 */
@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(ObjectMapper.class)
public class JacksonAutoConfiguration {

	private final Log log = LogFactory.getLog(JacksonAutoConfiguration.class);

	/**
	 * 自定义Jackson bean配置
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer mrhJackson2ObjectMapperBuilderCustomizer() {
		log.info(String.format("Initialize bean %s.", Jackson2ObjectMapperBuilderCustomizer.class));
		return (builder -> {
			builder.timeZone(TimeZone.getTimeZone("GMT+8"));
			builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			builder.serializationInclusion(Include.NON_NULL);
			builder.visibility(PropertyAccessor.GETTER, Visibility.NONE);
			builder.visibility(PropertyAccessor.SETTER, Visibility.NONE);
			builder.visibility(PropertyAccessor.FIELD, Visibility.ANY);
		});
	}

}
