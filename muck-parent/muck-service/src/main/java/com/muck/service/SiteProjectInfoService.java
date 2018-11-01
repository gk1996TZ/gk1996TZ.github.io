package com.muck.service;

import java.util.List;

import com.muck.domain.SiteProjectInfo;

/**
 *	工地项目详情service
*/
public interface SiteProjectInfoService extends BaseService<SiteProjectInfo> {
	
	
	
	//读取Excel返回List
	public List<SiteProjectInfo> readExcel(String path) throws Exception;
	
	//根据注册号查询项目详情
	public SiteProjectInfo queryByRegisterCode(String registerCode);
	
	//导出单个或多个项目详清(生成表格填充数据)
	public String exportExcelData2(List<SiteProjectInfo> siteProjectInfos) throws Exception;
	
	//导出项目详情列表
	public String exportExcelDatas(List<SiteProjectInfo> siteProjectInfos,String fileName) throws Exception;
	
	//导出项目详情汇总自己生成表
	public String exportExcelDatas(List<SiteProjectInfo> siteProjectInfos) throws Exception;

}
