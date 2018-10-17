package com.xumengba.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages="com.xumengba")
@MapperScan(basePackages="com.xumengba.mapper")
@EnableAutoConfiguration
@EnableCaching
@PropertySource({ "classpath:config.properties" })
public class XuMengBaRun {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(XuMengBaRun.class);
		application.run(args);
	}
}
