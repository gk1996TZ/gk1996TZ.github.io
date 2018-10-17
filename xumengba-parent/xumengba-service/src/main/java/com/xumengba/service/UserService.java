package com.xumengba.service;

import com.xumengba.domain.User;

public interface UserService extends BaseService<User>{
	
	public User queryByUserName(String userName);

	public User login(String userName, String password);
	
	//修改密码
	public void changePwd(String userName,String oldPwd,String newPwd);

}
