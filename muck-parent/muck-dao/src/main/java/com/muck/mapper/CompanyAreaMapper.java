package com.muck.mapper;

import org.apache.ibatis.annotations.Param;

import com.muck.domain.Company;

/**
* @Description: 添加企业入驻到哪些区域的关联
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月16日 下午4:23:02
 */
public interface CompanyAreaMapper {

	/**
	* @Description: 添加企业入驻到哪些企业的关联
	* @author: 展昭
	* @date: 2018年5月16日 下午4:24:02
	 */
	public void insert(@Param("company")Company company , @Param("areaCodes")String[] areaCodes);
	
}
