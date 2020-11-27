package org.lushen.mrh.boot.autoconfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

/**
 * 启动监听器，用于获取上下文对象，启动时通过 {@link SpringApplication#addListeners(ApplicationListener...)} 添加监听器
 * 
 * @author hlm
 */
public class ApplicationEnvironmentListener implements ApplicationListener<ApplicationEvent>, Ordered {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ApplicationEnvironmentPreparedEvent) {
			Environment environment = ((ApplicationEnvironmentPreparedEvent)event).getEnvironment();
			ApplicationEnvironment.setEnvironment(environment);
		}
	}

	/**
	 * 设置比日志监听器高的优先级
	 */
	@Override
	public int getOrder() {
		return LoggingApplicationListener.DEFAULT_ORDER - 10;
	}

}
