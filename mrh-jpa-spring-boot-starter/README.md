##### jpa动态条件查询扩展

	mrh-jpa-spring-boot-starter
	
	针对单表操作，动态条件查询进行扩展

##### 简单集成

	①，引入maven配置：
	
		<dependency>
			<groupId>org.lushen.mrh</groupId>
			<artifactId>mrh-jpa-spring-boot-starter</artifactId>
			<version>1.0</version>
		</dependency>
	
	②，注解开启扩展配置：
	
		@EnableJpaRepositories(repositoryFactoryBeanClass=JpaExampleRepositoryFactoryBean.class)
	
	③，接口继承指定接口：
	
		public interface HelloRepository extends JpaExampleRepository<HelloEntity, String> {}
	
	④，注入接口bean使用：
				
		@Autowired
		private HelloRepository helloRepo;
		
		public void test() {
			JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
			example.whereAnd()
				.equal(HelloEntity::getUsername, "测试1")
				.equal(HelloEntity::getGender, 1);
			List<HelloEntity> entities = helloRepo.findAll(example);
		}

##### 简单介绍
	
	接口扩展了以下五个方法（查询唯一记录，查询第一条记录，查询所有记录，分页查询记录，查询记录数）：
					
		T findOne(JpaExample<T> example);
		
		T findFirst(JpaExample<T> example);
		
		List<T> findAll(JpaExample<T> example);
	
		Page<T> findPage(JpaExample<T> example, Pageable pageable);
	
		long count(JpaExample<T> example);
	
	示例一：
		
		// 简单and查询
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereAnd()
			.equal(HelloEntity::getUsername, "测试1")
			.equal(HelloEntity::getGender, 1);
		List<HelloEntity> entities = helloRepo.findAll(example);
		
		select * from table_hello where user_name=? and gender=?
	
	示例二：
		
		// 简单and查询，对条件参数进行过滤
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereAnd()
			.equalIgnoreBlank(HelloEntity::getUsername, " ")
			.rightLikeIgnoreBlank(HelloEntity::getUsername, "测试")
			.equal(HelloEntity::getGender, 0);
		List<HelloEntity> entities = helloRepo.findAll(example);
		
		select * from table_hello where user_name=? and gender=?
		
	示例三：
		
		// 简单or查询，对条件参数进行过滤
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereOr()
			.equalIgnoreBlank(HelloEntity::getUsername, " ")
			.rightLikeIgnoreBlank(HelloEntity::getUsername, "测试")
			.equal(HelloEntity::getGender, 0);
		List<HelloEntity> entities = helloRepo.findAll(example);
		
		select * from table_hello where user_name=? or gender=?
		
	示例四：
	
		// 嵌套and和or条件，可以无限组合and和or、条件过滤进行查询操作
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		JpaWhere<HelloEntity> andWhere = example.whereAnd();
		andWhere.or()
			.equal(HelloEntity::getUsername, "测试4")
			.equal(HelloEntity::getGender, 1);
		andWhere.or()
			.equal(HelloEntity::getUsername, "测试5")
			.equal(HelloEntity::getGender, 1);
		List<HelloEntity> entities = helloRepo.findAll(example);
		
		select * from table_hello where ( user_name=? or gender=? ) and ( user_name=? or gender=? )
	
	示例五：
	
		// 排序
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereAnd()
			.equal(HelloEntity::getUsername, "测试1")
			.equal(HelloEntity::getGender, 1);
		example.orderByAsc(HelloEntity::getId);
		List<HelloEntity> entities = helloRepo.findAll(example);
		
		select * from table_hello where user_name=? and gender=? order by id asc
	
	示例六：
	
		// 多条件排序
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereAnd()
			.equal(HelloEntity::getUsername, "测试1")
			.equal(HelloEntity::getGender, 1);
		example.orderByAsc(HelloEntity::getId, HelloEntity::getGender);
		List<HelloEntity> entities = helloRepo.findAll(example);
		
		select * from table_hello where user_name=? and gender=? order by id asc, gender asc
	
	示例六：
	
		// 复杂排序
		JpaExample<HelloEntity> example = JpaExample.from(HelloEntity.class);
		example.whereAnd()
			.equal(HelloEntity::getUsername, "测试1")
			.equal(HelloEntity::getGender, 1);
		example.orderByAsc(HelloEntity::getId);
		example.orderByDesc(HelloEntity::getGender);
		List<HelloEntity> entities = helloRepo.findAll(example);
		
		select * from table_hello where user_name=? and gender=? order by id asc, gender desc
	
		

