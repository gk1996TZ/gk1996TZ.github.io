package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

public class SiteProjectInfoQueryForm extends BaseForm {

	// 项目详情id
	private String projectInfoId;

	// 区域id
	private String areaId;

	public String getProjectInfoId() {
		return projectInfoId;
	}

	public void setProjectInfoId(String projectInfoId) {
		this.projectInfoId = projectInfoId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Override
	public StatusCode validate() {
		return null;
	}

}
