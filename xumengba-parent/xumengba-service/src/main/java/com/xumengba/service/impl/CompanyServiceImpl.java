package com.xumengba.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumengba.domain.Company;
import com.xumengba.mapper.BaseMapper;
import com.xumengba.mapper.CompanyMapper;
import com.xumengba.service.CompanyService;
import com.xumengba.utils.CollectionUtils;
@Service
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements CompanyService{

	@Autowired
	private CompanyMapper companyMapper;
	
	@Override
	protected String getFields() {
		return " * ";
	}

	@Override
	protected BaseMapper<Company> getMapper() {
		return companyMapper;
	}

	@Override
	public void updateCompany(Company company) {
		
		company.setUpdatedTime(new Date());
		
		companyMapper.updateCompany(company);
		
	}

	@Override
	public List<Company> queryCompany() {
		return CollectionUtils.toList(companyMapper.getCompany());
	}

}
