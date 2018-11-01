package com.litang.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.litang.utils.MD5Utils;

@ComponentScan(basePackages = "com.litang")
@MapperScan(basePackages = "com.litang.mapper")
@EnableAutoConfiguration
@Configuration
@Cacheable
@EnableCaching
@PropertySource({ "classpath:config.properties" })
@ImportResource({"classpath:transaction.xml"})
public class LitangRun {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(LitangRun.class);
		springApplication.run(args);
	}
}
