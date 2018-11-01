package com.muck.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Area;
import com.muck.domain.Disposal;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.DisposalMapper;
import com.muck.service.DisposalService;
import com.muck.utils.ArrayUtils;

/**
 * @Description: 处置场管理service实现类
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月4日 下午7:07:32
 */
@Service
public class DisposalServiceImpl extends BaseServiceImpl<Disposal> implements DisposalService {

	@Autowired
	private DisposalMapper disposalMapper;
	
	@Override
	public String queryDisposalIdsAll() {
		List<String> disposalIds = disposalMapper.queryDisposalIdsAll();
		if(disposalIds != null){
			return ArrayUtils.array2str(disposalIds.toArray());
		}
		return null;
	}
	
	@Override
	public List<Area> queryDisposalTree() {
		return disposalMapper.queryDisposalTree();
	}
	

	/***
	 * 	根据通道号查询处置场详情
	*/
	public Disposal queryDisposalInfoByChannelCode(String channelCode) {
		return disposalMapper.selectDisposalInfoByChannelCode(channelCode);
	}

	// ---------------------------------------------
	@Override
	protected BaseMapper<Disposal> getMapper() {
		return disposalMapper;
	}

	@Override
	protected String getFields() {
		return "*";
	}

	@Override
	public void updateBatch(String registerCode,String channelCodes) {
		
		disposalMapper.updateBatch(registerCode, channelCodes);
		
	}

}
