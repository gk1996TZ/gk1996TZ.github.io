package com.litang.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.litang.domain.Image;

/**
 * 图片Mapper
 */
@Repository
public interface ImageMapper extends BaseMapper<Image>{
	
	public Integer deleteByIds(@Param("ids")String ids);

}
