package org.lushen.mrh.boot.autoconfigure.jackson;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
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

	@Bean
	public BeanPostProcessor objectMapperPostProcessor(@Autowired ObjectMapper objectMapper) {
		return new BeanPostProcessor() {
			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if(bean instanceof ObjectMapper) {
					ObjectMapper objectMapper = (ObjectMapper) bean;
					objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
					objectMapper.setSerializationInclusion(Include.NON_NULL);
					objectMapper.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
					objectMapper.setVisibility(PropertyAccessor.SETTER, Visibility.NONE);
					objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
					objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				}
				return bean;
			}
		};
	}

}
