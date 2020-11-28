##### 加解密、登录令牌工具

	mrh-crypto-spring-boot-starter
	
	常用加解密实现
	
	常用登录令牌生成实现

##### 简单集成

	引入maven配置即可：
	
		<dependency>
			<groupId>org.lushen.mrh</groupId>
			<artifactId>mrh-crypto-spring-boot-starter</artifactId>
			<version>1.0</version>
		</dependency>

##### 加解密工具

	基于springboot注入加解密bean，需要添加以下配置到 application.properties 文件：
	
		①，aes加解密配置：
		
			org.lushen.mrh.crypto.aes.enabled=true
			org.lushen.mrh.crypto.aes.key=123456
		
		②，des加解密配置：
		
			org.lushen.mrh.crypto.des.enabled=true
			org.lushen.mrh.crypto.des.key=123456
		
		③，3des加解密配置：
		
			org.lushen.mrh.crypto.des3.enabled=true
			org.lushen.mrh.crypto.des3.key=123456
			org.lushen.mrh.crypto.des3.iv=123456
		
		④，默认注入无加解密功能的 CryptoProvider 实现到 spring 上下文容器.
		
		然后注入接口使用：
		
			@Autowired
			private CryptoProvider cryptoProvider;
		
			public void test() {
				cryptoProvider.encrypt(...);
			}
	
	基于纯Java方式使用：
	
		①，aes加解密：
		
			AesProperties properties = new AesProperties();
			properties.setKey("123456");
			AesCryptoProvider provider = new AesCryptoProvider(properties);
		
		②，des加解密：
		
			DesProperties properties = new DesProperties();
			properties.setKey("123456");
			DesCryptoProvider provider = new DesCryptoProvider(properties);
		
		③，3des加解密：
		
			Des3Properties properties = new Des3Properties();
			properties.setIv("123456");
			properties.setKey("123456");
			Des3CryptoProvider provider = new Des3CryptoProvider(properties);

##### 令牌生成工具

	提供两种生成方式：
	
		①，使用 json web token 生成登录令牌，配置文件需要添加以下配置：
		
			org.lushen.mrh.token.jwt.enabled=true
			org.lushen.mrh.token.jwt.signature=HS512
			org.lushen.mrh.token.jwt.secret=123456
			org.lushen.mrh.token.jwt.default-timeout-second=3600
		
		②，使用 CryptoProvider 生成登录令牌，开启或者配置 CryptoProvider bean实现，添加配置：
		
			org.lushen.mrh.token.crypto.enabled=true
	
	使用示例：
	
		①，定义令牌信息对象：
		
			public class MyTokenObject extends TokenObject {
			
				// fields
				
				// getter and setter
			
			}
		
		②，注入bean使用：
		
			@Autowired
			private TokenResolver tokenResolver;
			
			public void test() {
				MyTokenObject object = new MyTokenObject();
				...
				String token = tokenResolver.create(object);
			}
			
			public void test2() {
				String token = ...;
				MyTokenObject object = tokenResolver.parse(token, MyTokenObject.class);
			}

