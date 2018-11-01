package com.litang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.litang.domain.User;
@Repository
public interface UserMapper extends BaseMapper<User> {
	
	public User selectByUserName(@Param("userName")String userName);

	//根据用户状态查询
	public List<User> selectUserByUserState(@Param("userState")int userState);

	
	public User queryByUserTokenCode(@Param("userTokenCode")String userTokenCode);

	public Integer resetPwd(@Param("userId")String userId,@Param("newPwd")String newPwd);
	
	//为用户分配权限
	public void setRoleForUser(@Param("userId")String userId,@Param("roleId")String roleId);
	
	//根据用户id修改角色
	public void updateUserRole(@Param("userId")String userId,@Param("roleId")String roleId);
	
	//根据用户id删除角色
	public void deleteUserRole(@Param("userId")String userId);
}
