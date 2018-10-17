package com.xumengba.service;

import java.util.List;

import com.xumengba.domain.Company;

public interface CompanyService extends BaseService<Company> {
	
	public void updateCompany(Company company);
	
	public List<Company> queryCompany();
	
	
	
	

}
