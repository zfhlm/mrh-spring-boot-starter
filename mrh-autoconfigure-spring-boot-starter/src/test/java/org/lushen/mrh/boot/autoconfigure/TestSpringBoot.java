package org.lushen.mrh.boot.autoconfigure;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@ComponentScan(basePackageClasses=TestSpringBoot.class)
public class TestSpringBoot {

	public static void main(String[] args) throws Exception {
		SpringApplication application = new SpringApplication(TestSpringBoot.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);
		new CountDownLatch(1).await();
	}

}
