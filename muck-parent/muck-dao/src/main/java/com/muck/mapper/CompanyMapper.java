package com.muck.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Company;
import com.muck.response.SimpleCompanyResponse;

/**
 * @Description: 企业Mapper
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月11日 下午2:15:18
 */
@Repository
public interface CompanyMapper extends BaseMapper<Company>{

	/**
	 * 
	* @Description: 统计企业列表
	* 					1、包括企业基本信息
	* 					2、工程中正在使用的车辆数
	* @author: 展昭
	* @date: 2018年4月27日 下午6:56:46
	*/
	public List<Map<String, Object>> statisticsCompanyList();

	/**
	* @Description: 根据区域编码查询企业
	* @author: 展昭
	* @date: 2018年5月17日 上午11:33:02
	*/
	public List<Company> selectCompanyByAreaCode(@Param("areaCode")String areaCode);

	public List<SimpleCompanyResponse> statisticsGroupCars();
	

	/**
	 * @Description: 根据企业id查询企业名称
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午8:43:38
	 * @param: id 传入一个企业id
	 * @return:String 返回企业名称
	 */
	public String queryCompanyNameById(@Param("id")String id);
	

	/**
	 * @Description: 根据企业名称获取企业
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年10月26日 上午9:50:53
	 * @Param: companyName 传入一个企业名称
	 * @Return: Company 返回企业
	 */
	public Company queryByName(String companyName);
}
