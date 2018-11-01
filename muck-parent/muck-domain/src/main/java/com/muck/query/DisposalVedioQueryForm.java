package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description: 处置场查询视频的请求实体类
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月17日 上午10:35:11
 */
public class DisposalVedioQueryForm extends BaseForm{

	/**区域编号*/
	private String areaCode;
	/**企业id数组*/
	private String[] companyIds;
	/**处置场id数组*/
	private String[] disposalIds;
	/**处置场名称*/
	private String disposalName;
	/**处置场负责人名称*/
	private String managerName;
	/**处置场负责人联系方式*/
	private String managerPhone;
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String[] getCompanyIds() {
		return companyIds;
	}
	public void setCompanyIds(String[] companyIds) {
		this.companyIds = companyIds;
	}
	public String[] getDisposalIds() {
		return disposalIds;
	}
	public void setDisposalIds(String[] disposalIds) {
		this.disposalIds = disposalIds;
	}
	public String getDisposalName() {
		return disposalName;
	}
	public void setDisposalName(String disposalName) {
		this.disposalName = disposalName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	@Override
	public StatusCode validate() {
		return null;
	}
}
