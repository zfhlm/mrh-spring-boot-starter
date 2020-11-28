package org.lushen.mrh.boot.autoconfigure.jackson;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class JacksonAutoConfiguration {

	private final Log log = LogFactory.getLog(JacksonAutoConfiguration.class);

	@Bean
	public ObjectMapper objectMapper() {
		log.info("Initialize bean " + ObjectMapper.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.SETTER, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return objectMapper;
	}

}
