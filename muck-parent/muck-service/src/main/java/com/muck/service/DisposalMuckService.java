package com.muck.service;

import java.util.List;

import com.muck.domain.DisposalMuckTurnover;

/**
 * @Description: 处置场进出渣土数据Service
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月7日 下午3:07:16
 */
public interface DisposalMuckService extends BaseService<DisposalMuckTurnover>{

	/**
	 * @Description: 查询指定条数的进出渣土数据
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月7日 下午4:26:09
	 * @param: disposalId 传入处置场id
	 * @param: count 传入查询的数据的条数
	 * @return: List<DisposalMuckTurnover> 返回含有指定处置场指定数量的进出渣土数据的列表
	 */
	public List<DisposalMuckTurnover> queryDisposalMuckTurnover(String disposalId,Integer count);
}
