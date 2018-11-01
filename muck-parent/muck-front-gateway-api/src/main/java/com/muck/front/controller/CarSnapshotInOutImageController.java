package com.muck.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.CarSnapshotInOutImage;
import com.muck.response.ResponseResult;

import com.muck.service.CarSnapshotInOutImageService;

/**
 * 	车辆进入进出工地处置场控制器
*/
@RestController("FrontCarSnapshotInOutImageController")
@RequestMapping("/front/carsnapshot/")
public class CarSnapshotInOutImageController extends BaseController{

	@Autowired
	private CarSnapshotInOutImageService carSnapshotInOutImageService;
	
	/**
	 *	保存车辆进入进出工地处置场数据 
	*/
	@RequestMapping(value = "save.action",method = RequestMethod.POST)
	public ResponseResult save(CarSnapshotInOutImage carSnapshotInOutImage){
		
		carSnapshotInOutImageService.save(carSnapshotInOutImage);
		
		return ResponseResult.ok();
	}
}