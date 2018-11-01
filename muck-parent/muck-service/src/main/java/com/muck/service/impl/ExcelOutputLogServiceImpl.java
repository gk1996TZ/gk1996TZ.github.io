package com.muck.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.ExcelOutputLog;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.ExcelOutputLogMapper;
import com.muck.service.ExcelOutputLogService;

/**
 * @Description: 导出Excel日志Service实现
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月26日 下午4:39:21
 */
@Service
public class ExcelOutputLogServiceImpl extends BaseServiceImpl<ExcelOutputLog> implements ExcelOutputLogService{
	@Autowired
	ExcelOutputLogMapper excelOutputLogMapper;
	// -------------------------------------------------------------------
	@Override
	protected BaseMapper<ExcelOutputLog> getMapper() {
		return excelOutputLogMapper;
	}

	@Override
	protected String getFields() {
		return "*";
	}
}
