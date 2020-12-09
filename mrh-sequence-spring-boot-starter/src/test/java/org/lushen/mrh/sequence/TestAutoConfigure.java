package org.lushen.mrh.sequence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lushen.mrh.boot.sequence.SequenceAutoConfiguration;
import org.lushen.mrh.boot.sequence.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
@Import({TestCuratorConfiguration.class, SequenceAutoConfiguration.class})
public class TestAutoConfigure {

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Test
	public void test() {
		System.out.println(sequenceGenerator.generate());
	}

}