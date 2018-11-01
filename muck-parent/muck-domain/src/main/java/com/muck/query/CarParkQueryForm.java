package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description: 停车场的请求参数封装类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月27日 下午10:54:33
 */
public class CarParkQueryForm extends BaseForm{

	/**停车场编号*/
	private String carParkCode;
	/**停车场名称*/
	private String carParkName;
	/**停车场所属区域编号*/
	private String areaCode;
	/**停车场所属区域名称*/
	private String areaName;
	/**企业id*/
	private String companyId;
	/**企业名称*/
	private String companyName;
	
	public String getCarParkCode() {
		return carParkCode;
	}

	public void setCarParkCode(String carParkCode) {
		this.carParkCode = carParkCode;
	}

	public String getCarParkName() {
		return carParkName;
	}

	public void setCarParkName(String carParkName) {
		this.carParkName = carParkName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

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

	@Override
	public StatusCode validate() {
		return null;
	}

}
