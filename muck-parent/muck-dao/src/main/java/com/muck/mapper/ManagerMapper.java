package com.muck.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.muck.domain.Manager;

/**
 * @Description: 系统用户Mapper
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月11日 下午2:10:37
 */
@Repository
public interface ManagerMapper extends BaseMapper<Manager>{

	/**
	* @Description: 根据条件查询用户,并且查询的结果是一条记录
	* @author: 展昭
	* @date: 2018年4月26日 下午3:48:55
	*/
	Manager selectOneByCondition(Map<String, String> conditions);
	/**
	 * @Description: 根据管理员姓名模糊查询管理员
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月3日 上午9:55:11
	 * @param: managerName 传入一个管理员姓名中包含此字符串的字符串
	 * @return: List<Manager> 返回包含管理员信息的列表
	 */
	List<Manager> selectByName(String managerName);
	/**
	 * @Description: 根据管理员联系方式查询管理员
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月3日 上午9:58:53
	 * @param: managerPhone 传入一个管理员联系方式中包含此字符串的字符串
	 * @return: List<Manager> 返回包含管理员信息的列表
	 */
	List<Manager> selectByPhone(String managerPhone);
}
