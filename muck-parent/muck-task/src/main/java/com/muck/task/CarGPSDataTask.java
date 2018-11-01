package com.muck.task;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.muck.service.car.CarGPSDataSocketService;

/**
 *	车辆GPS数据任务 
*/
@Component
public class CarGPSDataTask {
	
	@Autowired
	private CarGPSDataSocketService carGPSDataSocketService;
	

	//@Scheduled(cron = "0/10 * * * * ?") // 每隔10秒钟执行
	public void socket() throws Exception{
		
		//System.out.println("---------------------------------------------------------------------------------");
		
		// 向前台发送GPS数据
		//carGPSDataSocketService.sendInfo();
	}
}
