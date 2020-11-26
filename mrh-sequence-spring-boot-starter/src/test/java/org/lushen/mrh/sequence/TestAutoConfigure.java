package org.lushen.mrh.sequence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lushen.mrh.boot.sequence.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Test
	public void test() {
		System.out.println(sequenceGenerator.generate());
	}

}