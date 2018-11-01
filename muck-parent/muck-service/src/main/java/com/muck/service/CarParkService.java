package com.muck.service;

import java.util.List;

import com.muck.domain.Area;
import com.muck.domain.CarPark;

/**
 * @Description: 停车场Service
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月27日 下午10:15:36
 */
public interface CarParkService extends BaseService<CarPark>{

	/**
	 * @Description: 查询停车场树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日  上午11:31:49
	 * @return:List<Area> 返回停车场树
	 */
	public List<Area> queryCarParkTree();
}
