package com.muck.service;


import java.util.List;

import com.muck.domain.Area;
import com.muck.domain.Disposal;

/**
 * @Description: 处置场管理service
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月4日 下午7:05:28
 */
public interface DisposalService extends BaseService<Disposal>{
	
	/**
	 * @Description: 获取所有的处置场id
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午11:06:29
	 * @return: String 返回所有的处置场id
	 */
	public String queryDisposalIdsAll();
	
	/**
	 * @Description: 获取处置场树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午5:48:30
	 * @return:List<Area> 返回处置场树
	 */
	public List<Area> queryDisposalTree();

	/***
	 * 	根据通道号查询处置场详情
	*/
	public Disposal queryDisposalInfoByChannelCode(String channelCode);

	
	/**
	 * 批量更新处置场的注册码
	 * */
	public void updateBatch(String registerCode,String channelCodes);

}
