package com.muck.response;

import java.util.List;

import com.muck.domain.Company;

public class CarGroupTree {

	private List<Company> listCompany;
	private String rootName;
	public List<Company> getListCompany() {
		return listCompany;
	}
	public void setListCompany(List<Company> listCompany) {
		this.listCompany = listCompany;
	}
	public String getRootName() {
		return rootName;
	}
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
}
