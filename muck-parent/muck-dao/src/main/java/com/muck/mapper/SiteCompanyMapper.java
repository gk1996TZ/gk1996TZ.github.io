package com.muck.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Site;

/**
* @Description: 工地企业管关联Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月15日 下午2:04:52
 */
@Repository
public interface SiteCompanyMapper {

	/**
	* @Description: 添加工地和企业的关系
	* @param:
	* 			siteId:工地id
	* 			companyIds : 企业ids数组
	* @author: 展昭
	* @date: 2018年5月15日 下午2:06:47
	 */
	public void insert(@Param("site")Site site,@Param("companyIds")String[] companyIds);
	
}
