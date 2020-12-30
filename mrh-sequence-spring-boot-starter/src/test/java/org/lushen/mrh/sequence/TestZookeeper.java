package org.lushen.mrh.sequence;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.lushen.mrh.boot.sequence.registry.supports.ZookeeperSequenceInstanceRepository;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeInstanceCustomizer;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakePayload;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeInstanceSerializer;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeProperties;
import org.lushen.mrh.boot.sequence.snowflake.SnowflakeRegistryGenerator;

public class TestZookeeper {

	public static void main(String[] args) throws Exception {

		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		builder.connectString("localhost:2181");
		builder.retryPolicy(new RetryForever(1000));
		CuratorFramework client = builder.build();
		client.start();

		SnowflakeProperties properties = new SnowflakeProperties();
		
		SnowflakeInstanceSerializer instanceSerializer = new SnowflakeInstanceSerializer();
		SnowflakeInstanceCustomizer instanceCustomizer = new SnowflakeInstanceCustomizer();
		ZookeeperSequenceInstanceRepository<SnowflakePayload> instanceRepository = 
				new ZookeeperSequenceInstanceRepository<>(instanceSerializer, instanceCustomizer, client, properties.getBasePath());
		instanceRepository.afterPropertiesSet();
		
		SnowflakeRegistryGenerator generator = new SnowflakeRegistryGenerator(properties.getLiveTimeMillis(), instanceRepository);
		generator.afterPropertiesSet();

		while(true) {

			System.out.println(generator.generate());

			Thread.sleep(500L);

		}

	}

}
