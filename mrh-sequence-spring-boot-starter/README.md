##### 分布式序列ID生成器

	mrh-sequence-spring-boot-starter
	
	使用snowflake生成器算法，使用注册中心解决时钟回拨问题

##### 简单介绍
	
	初始化优先级：
	
		①，如果存在 zookeeper curator 客户端，自动使用zookeeper作为注册中心
		
		②，如果存在 RedisConnectionFactory 客户端，自动使用redis作为注册中心
		
		③，可以自定义注册中心实现 org.lushen.mrh.sequence.snowflake.SnowflakeFactory，然后配置为bean即可

##### 简单集成

	springboot 引入maven配置根据上下文环境自动初始化生成器：
	
		<dependency>
			<groupId>org.lushen.mrh</groupId>
			<artifactId>mrh-sequence-spring-boot-starter</artifactId>
			<version>1.0</version>
		</dependency>
	
	业务代码注入：
	
		@Autowired
		private SequenceGenerator generator;
		
		public void test() {
			Long sequence = generator.generate();
		}
