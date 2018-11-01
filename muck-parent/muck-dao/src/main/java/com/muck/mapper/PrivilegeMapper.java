package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Privilege;

/**
* @Description: 权限Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月27日 下午2:13:40
 */
@Repository
public interface PrivilegeMapper extends BaseMapper<Privilege> {

	/**
	* @Description: 查询顶级权限
	* @author: 展昭
	* @date: 2018年4月27日 下午4:44:02
	 */
	public List<Privilege> selectTopPrivileges();

	/**
	* @Description: 根据父权限查询下面的所有的子权限
	* @author: 展昭
	* @date: 2018年4月27日 下午4:44:02
	 */
	public List<Privilege> selectSubPrivilegesByParentId(@Param("parentId")String parentId);

}
