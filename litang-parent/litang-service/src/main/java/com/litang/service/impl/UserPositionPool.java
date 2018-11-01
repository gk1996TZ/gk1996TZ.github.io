package com.litang.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.litang.request.UserPosition;

public final class UserPositionPool {

	public static final List<UserPosition> pool = new ArrayList<UserPosition>();
	
	/**
	 * 	向用户定位为池中发送消息
	*/
	public static void add(UserPosition userPosition){
		
		if(pool.contains(userPosition)){
			// 存在删除
			pool.remove(userPosition);
		}
		// 不管有没有则直接添加
		pool.add(userPosition);
	}
	
	// 获取所有的用户定位
	public static List<UserPosition> getAll(){
		return pool;
	}
	
	// 根据用户id从pool中删除
	public static void remove(UserPosition userPosition){
		pool.remove(userPosition);
	}
}
