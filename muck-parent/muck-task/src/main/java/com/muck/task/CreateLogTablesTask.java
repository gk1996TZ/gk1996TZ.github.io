package com.muck.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.muck.service.LogService;
import com.muck.utils.LogUtils;

/**
* @Description: 动态生成日志表的任务
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月2日 下午3:44:33
 */
@Component
public class CreateLogTablesTask {
	
	@Autowired
	private LogService logService;
	
	/**
	* @Description: 每月的中旬(15号的晚上10点钟去动态的生成日志表)
	* @author: 展昭
	* @date: 2018年5月2日 下午4:04:13
	*/
	//@Scheduled(cron = "0 0 22 15 * ?")
	//@Scheduled(cron = "0 */1 * * * ?") // 每隔1分钟执行
	public void executeCreateLogTable(){
		
		//下一月
		String tableName = LogUtils.generateLogTableName(1);
		logService.createLogTable(tableName);
		
		//下两月
		tableName = LogUtils.generateLogTableName(2);
		logService.createLogTable(tableName);
		
		//下三月
		tableName = LogUtils.generateLogTableName(3);
		logService.createLogTable(tableName);
	}
}
