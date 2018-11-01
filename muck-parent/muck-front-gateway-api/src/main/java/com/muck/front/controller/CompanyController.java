package com.muck.front.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Company;
import com.muck.response.ResponseResult;
import com.muck.service.CompanyService;

/**
 * 	企业Controller
*/
@RestController("FrontCompanyController")
@RequestMapping("/front/company")
public class CompanyController extends BaseController{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CompanyService companyService;
	
	/***
	 *	根据企业id查询企业详情 
	*/
	@RequestMapping(value = "queryCompanyInfoById.action" , method = RequestMethod.GET)
	public ResponseResult queryCompanyInfoById(String companyId){
		
		if(StringUtils.isBlank(companyId)){
			logger.error("调用queryCompanyInfoById接口时companyId为空");
		}
		
		try {
			Company company = companyService.queryById(companyId);
			
			return ResponseResult.ok(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}
}
