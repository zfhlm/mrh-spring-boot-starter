package org.lushen.mrh.boot.crypto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@SpringBootApplication
@ComponentScan(basePackageClasses=TestAutoConfigure.class)
public class TestAutoConfigure {

	@Test
	public void test() {

	}

}