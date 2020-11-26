package org.lushen.mrh.sequence;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestCuratorConfiguration {

	@Bean
	public CuratorFramework curatorFramework() {
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		builder.connectString("localhost:2181");
		builder.retryPolicy(new RetryForever(1000));
		CuratorFramework client = builder.build();
		client.start();
		return client;
	}

}
