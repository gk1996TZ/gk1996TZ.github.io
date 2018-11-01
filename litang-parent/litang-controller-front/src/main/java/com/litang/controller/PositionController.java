package com.litang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.litang.request.UserPosition;
import com.litang.response.ResponseResult;
import com.litang.service.UserPositionService;

@RestController("FrontPositionController")
@RequestMapping("/front/position")
public class PositionController {

	@Autowired
	private UserPositionService userPositionService;
	
	/***
	 * 	接收每隔几分钟传递过来的用户定位信息
	*/
	@RequestMapping(value = "receiveUserPosition.action",method=RequestMethod.POST)
	public ResponseResult receiveUserPosition(UserPosition userPosition){
		
		userPositionService.receiveUserPosition(userPosition);
		
		return ResponseResult.ok();
	}
	
	/***
	 * 	当用户点解定位的时候把所有的用户的定位信息传给前台
	*/
	@RequestMapping(value = "sendUserPositions.action")
	public ResponseResult sendUserPositions(){
		
		List<UserPosition> userPositions = userPositionService.sendUserPositions();
		
		return ResponseResult.ok(userPositions);
	}
	
	
	
	
	
	
}
