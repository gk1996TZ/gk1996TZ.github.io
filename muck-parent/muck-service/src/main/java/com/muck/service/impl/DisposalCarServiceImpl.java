package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.DisposalCarTurnover;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.DisposalCarMapper;
import com.muck.service.DisposalCarService;

/**
 * @Description: 处置场进出车辆数据Service
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月7日 下午3:13:22
 */
@Service
public class DisposalCarServiceImpl extends BaseServiceImpl<DisposalCarTurnover> implements DisposalCarService{
	
	@Autowired
	DisposalCarMapper disposalCarMapper;
	
	@Override
	public List<DisposalCarTurnover> queryDisposalCarTurnover(String disposalId,Integer count) {
		return disposalCarMapper.queryDisposalCarTurnover(disposalId,count);
	}
	// ---------------------------------------------------
	@Override
	protected BaseMapper<DisposalCarTurnover> getMapper() {
		return disposalCarMapper;
	}
	@Override
	protected String getFields() {
		return "*";
	}
}
