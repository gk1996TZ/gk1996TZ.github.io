package com.litang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litang.domain.UserInfo;
import com.litang.mapper.BaseMapper;
import com.litang.mapper.UserInfoMapper;
import com.litang.service.UserInfoService;
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Override
	public boolean editByUserId(UserInfo userInfo) {
		return userInfoMapper.editByUserId(userInfo);
	}
	
	@Override
	public UserInfo queryByUserId(String userId) {
		return userInfoMapper.queryByUserId(userId);
	}


	@Override
	protected BaseMapper<UserInfo> getMapper() {
		return userInfoMapper;
	}


	@Override
	protected String getFields() {
		return " * ";
	}

}
