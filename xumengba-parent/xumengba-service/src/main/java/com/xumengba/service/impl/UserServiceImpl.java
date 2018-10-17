package com.xumengba.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumengba.domain.User;
import com.xumengba.exception.BizException;
import com.xumengba.mapper.BaseMapper;
import com.xumengba.mapper.UserMapper;
import com.xumengba.response.StatusCode;
import com.xumengba.service.UserService;
import com.xumengba.utils.MD5Utils;
import com.xumengba.utils.RegUtil;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

	@Autowired
	UserMapper userMapper;
	// =============================
	@Override
	protected String getFields() {
		return " * ";
	}
	@Override
	protected BaseMapper<User> getMapper() {
		return userMapper;
	}
	@Override
	public User queryByUserName(String userName) {
		return userMapper.getByUserName(userName);
	}
	@Override
	public User login(String userName, String password) {
		return userMapper.login(userName, password);
	}
	@Override
	public void changePwd(String userName, String oldPwd, String newPwd) {
		User user = userMapper.getByUserName(userName);
		if (user == null) {
			throw new RuntimeException("找不到用户名为:" + userName + "的用户");
		}
		if(!RegUtil.isPassword(newPwd)){
			throw new BizException(StatusCode.USER_PWD_ERROR);
		}
		String pwd=user.getUserPwd();
		
		// 判断用户输入的密码是否正确
				if (MD5Utils.encode(oldPwd).equals(pwd)) {
					// 说明输入的密码真确,继续判断旧密码和新密码是否一致
					if (!newPwd.equals(oldPwd)) {
						// 加密并设置新密码
						user.setUserPwd(MD5Utils.encode(newPwd));
						user.setUpdatedTime(new Date());

						// 修改数据库数据
						userMapper.updateById(user);
					} else {
						// 说明新旧密码一致
						throw new BizException(StatusCode.USER_PWD_SAME);
					}
				}else{
					throw new BizException(StatusCode.PASSWORD_IS_NOT_TRUE);
				}
				
	}
	
	
	
	@Override
	public void save(User u) {
		
		String userName=u.getUserName();
		if(!RegUtil.isUsername(userName)){
			throw new BizException(StatusCode.USER_NAME_ERROR);
		}
		//判断用户名是否存在
		User us=userMapper.getByUserName(userName);
		if(us!=null){
			throw new BizException(StatusCode.USER_NAME_EXIST);
		}
		
		String userMail=u.getUserMail();
		if(StringUtils.isNotBlank(userMail)){
			boolean isMail=RegUtil.isEmail(userMail);
			if(!isMail){
				throw new BizException(StatusCode.USER_MAIL_ERROR);
			}
			//说明邮箱格式正确,判断邮箱是否已存在
			//根据邮箱查询用户
			User user=userMapper.getUserByMail(userMail);
			if(user!=null){
				//说明已经注册了这个邮箱
				throw new BizException(StatusCode.USER_MAIL_EXIST);
			}
		}
		String userPhone=u.getUserPhone();
		if(StringUtils.isNotBlank(userPhone)){
			if(!RegUtil.isMobile(userPhone)){
				throw new BizException(StatusCode.USER_PHONE_ERROR);
			}
			//说明手机号格式正确判断手机号是否已存在
			//根据手机号查询用户
			User user=userMapper.getUserByPhone(userPhone);
			if(user!=null){
				throw new BizException(StatusCode.USER_PHONE_EXIST);
			}
			
		}
		//校验密码
		if(!RegUtil.isPassword(u.getUserPwd())){
			throw new BizException(StatusCode.USER_PWD_ERROR);
		}
		Date date=new Date();
		u.setCreatedTime(date);
		u.setUpdatedTime(date);
		u.setUserPwd(MD5Utils.encode(u.getUserPwd()));
		u.setDeleted(true);
		
		super.save(u);
	}
	
	@Override
	public void editById(User t) {
		if(StringUtils.isBlank(t.getUserName())){
			throw new BizException(StatusCode.USER_NAME_NULL);
		}
		String userMail=t.getUserMail();
		if(StringUtils.isNotBlank(userMail)){
			boolean isMail=RegUtil.isEmail(userMail);
			if(!isMail){
				throw new BizException(StatusCode.USER_MAIL_ERROR);
			}
			//说明邮箱格式正确,判断邮箱是否已存在
			//根据邮箱查询用户
			User user=userMapper.getUserByMail(userMail);
			if(user!=null){
				//user不为空要判断这个user是不是当前要修改的user
				//说明已经注册了这个邮箱
				if(!(user.getUserName().equals(t.getUserName()))){
					throw new BizException(StatusCode.USER_MAIL_EXIST);
				}
			}
		}
		String userPhone=t.getUserPhone();
		if(StringUtils.isNotBlank(userPhone)){
			if(!RegUtil.isMobile(userPhone)){
				throw new BizException(StatusCode.USER_PHONE_ERROR);
			}
			//说明手机号格式正确判断手机号是否已存在
			//根据手机号查询用户
			User user=userMapper.getUserByPhone(userPhone);
			
			if(user!=null){
				if(!(user.getUserName().equals(t.getUserName()))){
					throw new BizException(StatusCode.USER_PHONE_EXIST);
				}
			}
		}
		t.setUpdatedTime(new Date());
		
		super.editById(t);
	}
	
}
