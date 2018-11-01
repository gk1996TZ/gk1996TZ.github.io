package com.litang.service;

import java.util.List;

import com.litang.request.UserPosition;

public interface UserPositionService {
	
	public void receiveUserPosition(UserPosition userPosition);
	
	public List<UserPosition> sendUserPositions();
	
}
