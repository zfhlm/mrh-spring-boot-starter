package org.lushen.mrh.boot.springfox;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.lushen.mrh.boot.springfox.annotation.Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

@SpringBootApplication
@ActiveOpenApi(profiles={"default", "test", "dev"})
public class TestSpringfox {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringfox.class, args);
	}

	@Doc("测试")
	@RestController
	@RequestMapping(path="")
	public static class TestController {

		@Doc("测试接口")
		@PostMapping(path="test")
		public void test(@RequestBody @Valid TestParam param) {

		}

	}


	public static class TestParam {

		@NotBlank
		@Doc("主键")
		private String id;

		@NotNull
		@JsonFormat(pattern="yyyy-MM-dd")
		@Doc("日期")
		private Date date;

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

	}

}
