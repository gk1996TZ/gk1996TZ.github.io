package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description: 企业请求实体类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月29日 下午1:31:12
 */
public class CompanyQueryForm extends BaseForm{

	/**企业id*/
	private String companyId;
	/**企业名称*/
	private String companyName;
	/**企业负责人名称*/
	private String companyPrincipalName;
	/**企业法人*/
	private String companyLegalRepresentative;
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCompanyPrincipalName() {
		return companyPrincipalName;
	}


	public void setCompanyPrincipalName(String companyPrincipalName) {
		this.companyPrincipalName = companyPrincipalName;
	}

	public String getCompanyLegalRepresentative() {
		return companyLegalRepresentative;
	}

	public void setCompanyLegalRepresentative(String companyLegalRepresentative) {
		this.companyLegalRepresentative = companyLegalRepresentative;
	}

	@Override
	public StatusCode validate() {
		return null;
	}
}
