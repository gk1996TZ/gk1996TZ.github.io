package com.litang.service;

import java.util.List;

import com.litang.domain.User;

public interface UserService extends BaseService<User> {
	
	//根据用户名查询用户
	public User queryByUserName(String userName);

	public List<User> queryUsersByUserState(int userState);

	public User queryByUserTokenCode(String userTokenCode);
	
	public boolean resetPwd(String userId);
	
	//为用户添加角色
	public void setUserRole(String userId,String roleId);
	
	//为用户修改角色
	public void updateUserRole(String userId,String roleId);
	
	//删除用户角色
	public void deleteUserRoleByUserId(String userId);
}
