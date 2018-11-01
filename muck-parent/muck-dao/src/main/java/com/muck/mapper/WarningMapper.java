package com.muck.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Warning;

/**
* @Description: 告警Mapper 
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月4日 上午10:08:20
*/
@Repository
public interface WarningMapper extends BaseMapper<Warning>{

	/**
	 * @Description: 获取异常总数 
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月26日  上午2:41:24
	 * @return:Long 返回异常总数
	 */
	Long queryWarningCount();
	/**
	 * @Description: 通过告警名称获取告警信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月5日  下午11:57:22
	 * @param: warningName 传入一个告警名称
	 * @return:Warning 返回告警信息
	 */
	public Warning queryByWarningName(@Param("warningName")String warningName);
}
