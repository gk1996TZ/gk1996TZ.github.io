package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

/**
 * @Description: 停车场Excel请求模版参数
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月27日 下午10:43:53
 */
public class CarParkExcelQueryTemplate extends BaseExcelQueryTemplate{

	/**停车场编号*/
	@ExcelTemplate(name="停车场编号")
	private String has_carParkCode;
	/**停车场名称*/
	@ExcelTemplate(name="停车场名称")
	private String has_carParkName;
	/**停车场所属区域名称*/
	@ExcelTemplate(name="所属区域")
	private String has_areaName;
	/**所属企业名称*/
	@ExcelTemplate(name="所属企业")
	private String has_companyName;
}
