package com.muck.task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import com.muck.response.GPSDataConfig;
import com.muck.service.GpsDataService;
import com.muck.service.PropertiesService;

/**
 * 	关于车辆GPS的定时任务
*/
@Component
@EnableScheduling
public class GPSDataTask implements SchedulingConfigurer{

	@Autowired
	private PropertiesService propertiesService;
	
	@Autowired
	private GpsDataService gpsDataService;
	
	private Logger logger = LoggerFactory.getLogger(GPSDataTask.class);
	
	/**
	 * 	根据指定的时间删除数据库中的历史车辆GPS数据
	 * 		说明:
	 * 			@Scheduled(cron="0/3 * * * * ?")  // 每隔3秒执行一次
	 * 			@Scheduled(cron = "0 15 10 ? * *") // 表示每天上午10点15分执行一次
	*/			
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
		// 1、获取gps配置的时间信息
		GPSDataConfig config = propertiesService.getGPSDataConfig();
		
		// 2、获取定时的任务配置在几点
		Integer minute = config.getMinute();
		
		// 3、获取定时的任务配置在几分钟
		Integer hour = config.getHour();
		
		// 4、设置要保留的GPS车载数据的月份
		final Integer month = config.getMonth();
		
		// 4、生成表达式
		String cronTime = "0 "+ minute +" "+ hour +" ? * *";
		
		taskRegistrar.addCronTask(new Runnable() {
			public void run() {
				try {
					gpsDataService.deleteBatchGPSData(month);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getLocalizedMessage(), e);
				}
			}
		}, cronTime);
	}
}
