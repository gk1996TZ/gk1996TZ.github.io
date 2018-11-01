package com.muck.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @Description: 用户权限组mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月25日 下午12:30:08
 */
@Repository
public interface ManagerPrivilegeGroupMapper{

	/**
	* @Description: 根据用户ids删除此用户下面的权限
	* @param: managerIds : 用户ids
	* @author: 展昭
	* @date: 2018年4月25日 下午12:32:22
	 */
	public void deleteByManagerIds(String[] managerIds);

	/**
	* @Description: 批量为添加数据
	* @author: 展昭
	* @date: 2018年4月25日 下午5:01:38
	 */
	public void insertBatch(Map<String,String[]> map);
}
