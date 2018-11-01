package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.DisposalMuckTurnover;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.DisposalMuckMapper;
import com.muck.service.DisposalMuckService;

/**
 * @Description: 渣土进出数据 Service
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月7日 下午3:10:44
 */
@Service
public class DisposalMuckServiceImpl extends BaseServiceImpl<DisposalMuckTurnover> implements DisposalMuckService{

	@Autowired
	DisposalMuckMapper disposalMuckMapper;
	
	@Override
	public List<DisposalMuckTurnover> queryDisposalMuckTurnover(String disposalId,Integer count) {
		return disposalMuckMapper.queryDisposalMuckTurnover(disposalId,count);
	}
	// ----------------------------------------------------
	@Override
	protected BaseMapper<DisposalMuckTurnover> getMapper() {
		return disposalMuckMapper;
	}
	@Override
	protected String getFields() {
		return "*";
	}
}
