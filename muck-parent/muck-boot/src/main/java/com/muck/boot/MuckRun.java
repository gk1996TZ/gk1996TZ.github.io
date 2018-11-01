package com.muck.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.muck.jt809.MyJT809BusiHander;
import com.muck.service.AutoRegister;
import com.muck.socket.VehicleWebSocket;

@ComponentScan(basePackages = "com.muck")
@MapperScan(basePackages = "com.muck.mapper")
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@ImportResource({ "classpath:transaction.xml", "classpath:uniquevalidate.xml", "classpath:logger.xml" })
@PropertySource({ "classpath:config.properties" })
public class MuckRun {

	// 大华SDK库实例
	// static DHNetSDKLib DHNetSDKLibObj = DHNetSDKLib.INSTANCE;
	public static void main(String[] args) {
		// 打印版本信息
		// DHNetSDKLibObj.CLIENT_GetSDKVersion();
		
		// 初始化
		// DHNetSDKLibObj.CLIENT_Init(null, new NativeLong(0));
		// System.out.println("dhnetsdk Init ok !");
		SpringApplication springApplication = new SpringApplication(MuckRun.class);
		ConfigurableApplicationContext applicationContext = springApplication.run(args);
		// CarGPSDataSocketService.setApplicationContext(applicationContext);//
		// 解决WebSocket不能注入的问题
		//JT809BusiHander.setApplicationContext(applicationContext);// 解决WebSocket不能注入的问题
		MyJT809BusiHander.setApplicationContext(applicationContext);// 解决WebSocket不能注入的问题
		AutoRegister.setApplicationContext(applicationContext);
		VehicleWebSocket.setApplicationContext(applicationContext);
		//ChaobaoWebSocket.setApplicationContext(applicationContext);
		/***
		 * 将SpringBoot的系统启动类修改为如下代码,目的是为了解决在SpringBoot环境下解决WebSocket无法注入service
		 * 的问题。
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("AutoRegister___________________________________启动了吗——————————————————————");
				AutoRegister.test();
			}
		}).start();
	}
}

