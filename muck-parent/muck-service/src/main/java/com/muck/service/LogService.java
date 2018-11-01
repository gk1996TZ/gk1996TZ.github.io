package com.muck.service;

import java.util.List;

import com.muck.domain.Log;
import com.muck.page.PageView;

/**
* @Description: 日志Service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月28日 下午3:32:39
 */
public interface LogService extends BaseService<Log>{

	/***
	 * 
	* @Description: 通过表名创建日志表
	* @author: 展昭
	* @date: 2018年5月3日 上午10:25:19
	 */
	public void createLogTable(String tableName);
	
	/***
	* @Description: 查询所有的日志
	* @author: 展昭
	* @date: 2018年5月3日 上午10:25:14
	 */
	public List<Log> queryNearestLogs();
	
	public PageView<Log> queryNearestLogs(Long currentPageNum, Long pageSize, int n);
	
	public PageView<Log> queryPageData(Long currentPageNum, Long pageSize, String wherSQL, List<Object> whereParams);
}
