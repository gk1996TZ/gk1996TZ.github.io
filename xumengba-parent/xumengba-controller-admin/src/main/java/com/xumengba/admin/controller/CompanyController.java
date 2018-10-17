package com.xumengba.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.Company;
import com.xumengba.response.ResponseResult;
import com.xumengba.service.CompanyService;

@RestController("AdminCompanyController")
@RequestMapping("/admin/company")
public class CompanyController extends BaseController {
	
	@Autowired
	private CompanyService companyService;
	
	
	/**
	 * 传入一个需要被修改的对象
	 * */
	
	@RequestMapping(value="update.action",method=RequestMethod.POST)
	public ResponseResult update(Company company){
		
		companyService.updateCompany(company);
		return ResponseResult.ok();
	}
	
	
	/**
	 * 查询企业信息
	 * */
	@RequestMapping(value="queryCompany.action",method=RequestMethod.GET)
	public  ResponseResult queryCompany(){
		List<Company>company=companyService.queryCompany();
		return ResponseResult.ok(company);
		
	}

}
