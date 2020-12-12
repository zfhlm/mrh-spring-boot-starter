package org.lushen.mrh.boot.springfox;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.lushen.mrh.boot.autoconfigure.support.enums.GenericEnum;
import org.lushen.mrh.boot.springfox.annotation.Doc;
import org.lushen.mrh.boot.springfox.annotation.DocHidden;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

@SpringBootApplication
@ActiveOpenApi(profiles={"default", "test", "dev"})
public class TestSpringfoxBootStarter {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringfoxBootStarter.class, args);
	}

	public static interface TestInterface {

		@Doc("测试接口")
		@PostMapping(path="test")
		public void test(@RequestBody @Valid TestParam param);

		@Doc("测试接口2")
		@GetMapping(path="test2")
		public void test2();

		@Doc("测试接口3")
		@PostMapping(path="test4")
		public void test4(@ModelAttribute @Valid Test2Param param);

	}

	@Doc("测试接口分组1")
	@RestController
	@RequestMapping(path="")
	public static class TestController implements TestInterface {

		@Override
		public void test(TestParam param) {

		}

		@Override
		public void test2() {
			throw new RuntimeException();
		}

		@Override
		public void test4(Test2Param param) {

		}

	}


	@Doc("测试接口分组2")
	@RestController
	@RequestMapping(path="")
	public static class Test2Controller {

		@DocHidden
		@Doc("测试接口")
		@PostMapping(path="test3")
		public void test(@RequestBody @Valid TestParam param) {

		}

	}

	@Doc("测试参数model")
	public static class TestParam {

		@NotBlank
		@Doc("主键")
		private String id;

		@NotNull
		@JsonFormat(pattern="yyyy-MM-dd")
		@Doc("日期")
		private Date date;

		@Doc("类型")
		private TestEnum type;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public TestEnum getType() {
			return type;
		}

		public void setType(TestEnum type) {
			this.type = type;
		}

	}

	@Doc("测试参数model2")
	public static class Test2Param {

		@NotBlank
		@Doc("主键")
		private String id;

		@NotNull
		@JsonFormat(pattern="yyyy-MM-dd")
		@Doc("日期")
		private Date date;

		@Doc("类型")
		private TestEnum type;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public TestEnum getType() {
			return type;
		}

		public void setType(TestEnum type) {
			this.type = type;
		}

	}

	public static enum TestEnum implements GenericEnum<TestEnum> {

		PNG(1, "PNG"),
		JPG(2, "JPG");

		private Integer value;

		private String name;

		private TestEnum(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		@Override
		public Integer toValue() {
			return value;
		}

		@Override
		public String toName() {
			return name;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}

	}

}
