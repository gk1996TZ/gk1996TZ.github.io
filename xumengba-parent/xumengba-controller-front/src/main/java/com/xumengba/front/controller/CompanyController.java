package com.xumengba.front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.Company;
import com.xumengba.response.ResponseResult;
import com.xumengba.service.CompanyService;

@RestController("FrontCompanyController")
@RequestMapping("/front/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private CompanyService companyService;
	
	
	/**
	 * 查询企业信息
	 * */
	@RequestMapping(value="queryCompany.action",method=RequestMethod.GET)
	public  ResponseResult queryCompany(){
		List<Company>company=companyService.queryCompany();
		return ResponseResult.ok(company);
		
	}

}
