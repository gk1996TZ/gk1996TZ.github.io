package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.ManagerPrivilegeGroup;

/**
 * @Description: 授权Mapper
 * @Version: v1.0.0
 * @Author: 朱俊亮
 * @Date: 2018年7月16日 下午4:50:14
 */
@Repository
public interface AccreditMapper extends BaseMapper<ManagerPrivilegeGroup>{

	/**
	 * @Description: 给单个用户添加多个权限组
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年7月16日 下午5:07:31
	 * @Param: managerId 传入一个用户id
	 * @Param: privilegeGroupIds 传入一个权限组id列表
	 */
	public void insertBatch(@Param("managerId")String managerId,@Param("privilegeGroupIds")List<String> privilegeGroupIds);
	/**
	 * @Description: 给单个用户删除多个权限组
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年7月16日 下午5:57:34
	 * @Param: managerId 传入一个用户id
	 * @Param: privilegeGroupIds 传入一个权限组id列表
	 */
	public void deleteBatch(@Param("managerId")String managerId,@Param("privilegeGroupIds")List<String> privilegeGroupIds);
	
	/**
	 * 根据权限组id查询该权限组有没有赋予给用户
	 * */
	public int selectByPrivilegeGroupId(@Param("privilegeGroupId")String privilegeGroupId);
}
