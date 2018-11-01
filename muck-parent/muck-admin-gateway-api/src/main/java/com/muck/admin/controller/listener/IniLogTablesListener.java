package com.muck.admin.controller.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.muck.service.LogService;
import com.muck.utils.LogUtils;

/**
 * @Description: Spring启动的时候实现监听事件
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月2日 下午4:21:09
 */
@Component
public class IniLogTablesListener implements ApplicationListener<ApplicationEvent> {

	@Autowired
	private LogService logService;
	
	/**
	 * 使用spring监听器实现启动时创建连续三张日志表
	*/
	public void onApplicationEvent(ApplicationEvent event) {
		// 上下文刷新事件
		if (event instanceof ContextRefreshedEvent) {
			String tableName = LogUtils.generateLogTableName(0);
			logService.createLogTable(tableName);

			tableName = LogUtils.generateLogTableName(1);
			logService.createLogTable(tableName);

			tableName = LogUtils.generateLogTableName(2);
			
			logService.createLogTable(tableName);
			
			System.out.println("初始化日志表完成!!!");
		}
	}
}
