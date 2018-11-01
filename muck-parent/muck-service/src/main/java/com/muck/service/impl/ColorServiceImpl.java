package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Color;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.ColorMapper;
import com.muck.service.ColorService;

/**
 * 
 *	颜色Service
*/
@Service
public class ColorServiceImpl extends BaseServiceImpl<Color> implements ColorService {

	@Autowired
	private ColorMapper colorMapper;

	/**
	 * 	查询所有颜色
	*/
	public List<Color> queryAllColors() {
		
		return colorMapper.selectAllColors();
	}
	
	//------------------------------
	@Override
	protected BaseMapper<Color> getMapper() {
		return colorMapper;
	}

	@Override
	protected String getFields() {
		return "id, color_name_zh, color_name_en, color_code, color_value,color_occupancy";
	}
}
