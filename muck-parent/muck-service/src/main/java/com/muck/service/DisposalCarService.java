package com.muck.service;

import java.util.List;

import com.muck.domain.DisposalCarTurnover;

/**
 * @Description: 处置场车辆进出数据Service
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月7日 下午3:06:07
 */
public interface DisposalCarService extends BaseService<DisposalCarTurnover>{

	/**
	 * @Description: 查询指定条数的车辆进出数据 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月7日 下午4:24:25
	 * @param: disposalId 传入处置场id
	 * @param: count 传入查询数据的条数
	 * @return: List<DisposalCarTurnover> 返回含有指定条数的指定处置场的车辆车辆进出的数据列表
	 */
	public List<DisposalCarTurnover> queryDisposalCarTurnover(String disposalId,Integer count);
}
