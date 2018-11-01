package com.litang.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litang.domain.User;
import com.litang.mapper.UserMapper;
import com.litang.request.UserPosition;
import com.litang.service.UserPositionService;

@Service
public class UserPositionServiceImpl implements UserPositionService {

	@Autowired
	private UserMapper userMapper;

	// 接收用户定位信息
	public void receiveUserPosition(UserPosition userPosition) {

		// 根据用户id查询用户信息
		String userId = userPosition.getUserId();
		if (StringUtils.isNotBlank(userId)) {
			User user = userMapper.queryById(userId);
			if (user != null) {
				userPosition.setName(user.getUserName());
				userPosition.setPhone(user.getUserInfo().getUserPhone());
				
				UserPositionPool.add(userPosition);
			}

		}
	}

	public List<UserPosition> sendUserPositions() {
		return UserPositionPool.getAll();
	}
}
