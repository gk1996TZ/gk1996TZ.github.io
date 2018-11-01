package com.muck.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.muck.domain.Warning;
import com.muck.page.PageView;

/**
* @Description: 告警Service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月4日 上午10:13:56
 */
public interface WarningService extends BaseService<Warning> {


	
	/**
	 * @Description: 获取所有异常信息的总数
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月27日  下午1:27:13
	 * @return:Long 返回所有异常信息的总数
	 */
	public Long queryWarningCount(); 
	/**
	 * @Description: 查询异常分页数据
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月27日  下午2:23:30
	 * @Param: currentPageNum 当前页码
	 * @Param: pageSize 每页显示的记录数
	 * @Param: whereSQL 条件sql
	 * @Param: whereParams 条件参数
	 * @Param: orderBy 
	 * @Param: warningType 告警类型1.工地告警2.车辆告警3.处置场告警
	 * @Return: PageView<Warning> 返回分页数据
	 */
	public PageView<Warning> queryPageData(Long currentPageNum, Long pageSize, 
			 String whereSQL,List<Object> whereParams,
			 LinkedHashMap<String, String> orderBy,Integer warningType);
	/**
	 * @Description: 通过告警名称获取告警信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月5日  下午11:57:22
	 * @param: warningName 传入一个告警名称
	 * @return:Warning 返回告警信息
	 */
	public Warning queryByWarningName(String warningName);
}
