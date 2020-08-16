package com.tdkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan(basePackages = "com.tdkj.dao")
@SpringBootApplication
@EnableCaching
public class SpringbootApplication {

	public static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		context=SpringApplication.run(SpringbootApplication.class, args);
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}");
	}
}
