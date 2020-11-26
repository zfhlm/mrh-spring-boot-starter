package org.lushen.mrh.sequence;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeGenerator;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeProperties;
import org.lushen.mrh.boot.sequence.snowflake.factory.SnowflakeCuratorFactory;

public class TestZookeeper {

	public static void main(String[] args) throws Exception {

		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		builder.connectString("localhost:2181");
		builder.retryPolicy(new RetryForever(1000));
		CuratorFramework client = builder.build();
		client.start();

		SnowflakeProperties properties = new SnowflakeProperties();
		properties.setBasePath("/snowflake");
		properties.setLiveTimeMillis(5*60*1000L);

		SnowflakeCuratorFactory factory = new SnowflakeCuratorFactory(client, properties, e -> {});
		factory.afterPropertiesSet();

		SnowflakeGenerator generator = new SnowflakeGenerator(factory);
		generator.afterPropertiesSet();

		while(true) {

			System.out.println(generator.generate());

			Thread.sleep(500L);

		}

	}

}
