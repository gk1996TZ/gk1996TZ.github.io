package com.muck.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.muck.domain.Color;

/**
 *	颜色Mapper
*/
@Repository
public interface ColorMapper extends BaseMapper<Color>{

	/**
	 * 	查询所有颜色
	*/
	public List<Color> selectAllColors();

}
