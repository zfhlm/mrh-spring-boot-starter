package org.lushen.mrh.boot.jpa;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lushen.mrh.boot.jpa.example.JpaExample;
import org.lushen.mrh.boot.jpa.where.JpaWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses=TestHelloService.class, repositoryFactoryBeanClass=JpaExampleRepositoryFactoryBean.class)
@EnableTransactionManagement
public class TestHelloService {

	@Autowired
	private HelloRepository helloRepo;

	@Test
	public void testInsert() {
		for(int index=0; index<100; index++) {
			HelloEntity entity = new HelloEntity();
			entity.setId(UUID.randomUUID().toString().replace("-", ""));
			entity.setUsername("测试" + index);
			entity.setGender(index%2);
			helloRepo.save(entity);
		}
	}

	@Test
	public void testSimpleQuery() {
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereAnd()
			.equal(HelloEntity::getUsername, "测试1")
			.equal(HelloEntity::getGender, 1);
		System.out.println(example);
		List<HelloEntity> entities = helloRepo.findAll(example);
		entities.forEach(System.out::println);
	}
	
	@Test
	public void testDynamicSimpleQuery() {
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereAnd()
			.equalIgnoreBlank(HelloEntity::getUsername, " ")
			.rightLikeIgnoreBlank(HelloEntity::getUsername, "测试")
			.equal(HelloEntity::getGender, 0);
		List<HelloEntity> entities = helloRepo.findAll(example);
		entities.forEach(System.out::println);
	}
	
	@Test
	public void testOrQuery() {
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereOr()
			.rightLikeIgnoreBlank(HelloEntity::getUsername, "测试")
			.equal(HelloEntity::getGender, 0);
		List<HelloEntity> entities = helloRepo.findAll(example);
		entities.forEach(System.out::println);
	}
	
	@Test
	public void testNestingQuery() {
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		JpaWhere<HelloEntity> andWhere = example.whereAnd();
		andWhere.or()
			.equal(HelloEntity::getUsername, "测试4")
			.equal(HelloEntity::getGender, 1);
		andWhere.or()
			.equal(HelloEntity::getUsername, "测试5")
			.equal(HelloEntity::getGender, 1);
		List<HelloEntity> entities = helloRepo.findAll(example);
		entities.forEach(System.out::println);
	}

}
