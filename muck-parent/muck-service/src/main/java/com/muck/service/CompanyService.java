package com.muck.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.muck.domain.Company;
import com.muck.domain.CompanyManager;
import com.muck.page.PageView;
import com.muck.query.CompanyQueryForm;
import com.muck.response.SimpleCompanyResponse;

/**
* @Description: 企业Service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 上午11:23:14
 */
public interface CompanyService extends BaseService<Company> {

	/**
	 * 
	* @Description: 统计企业列表
	* 					1、包括企业基本信息
	* 					2、工程中正在使用的车辆数
	* @author: 展昭
	* @date: 2018年4月27日 下午6:56:46
	*/
	public List<Map<String,Object>> statisticsCompanyList();

	/**
	* @Description: 根据区域编码查询企业
	* @author: 展昭
	* @date: 2018年5月17日 上午11:31:12
	 */
	public List<Company> queryCompanyByAreaCode(String areaCode);
	
	
	/**
	 * @Description: 获取企业的分页数据
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月29日  下午1:40:48
	 * @param:@param companyQueryForm 传入一个查询企业的条件请求参数
	 * @return:PageView<Company> 返回企业的分页数据
	 */
	public PageView<Company> queryCompanyPageData(CompanyQueryForm companyQueryForm);
	
	public PageView<Company> queryCompanyPageData();
	
	public List<SimpleCompanyResponse> statisticsGroupCars();
	
	
	//根据文件名获取文件导出路径
	public String exportCompanyInfo(String fileName,Company company) throws Exception;
	
	/**
	 * @Description: 导出企业汇总表数据
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年8月16日  下午7:38:54
	 * @param: fileName 传入一个模版文件名
	 * @param: listCompany 传入一个企业列表
	 * @param: listCompanyManager 传入一个企业管理人员列表
	 * @param: water 传入水印
	 * @return:String 返回excel文件在本地存放的路径
	 */
	public String createExcelPOI(String fileName,List<Company> listCompany,List<CompanyManager> listCompanyManager,String [] water);
	
	/**
	 * @Description: 根据企业id查询企业名称
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午8:43:38
	 * @param: id 传入一个企业id
	 * @return:String 返回企业名称
	 */
	public String queryCompanyNameById(String id);
	
	/**
	 * @Description: 根据企业名称获取企业
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年10月26日 上午9:50:53
	 * @Param: companyName 传入一个企业名称
	 * @Return: Company 返回企业
	 */
	public Company queryByName(@Param("companyName")String companyName);
}
