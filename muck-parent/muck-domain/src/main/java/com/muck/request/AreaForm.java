package com.muck.request;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.muck.annotation.Unique;
import com.muck.response.StatusCode;

/**
 * @Description: 区域Form
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月18日 下午4:52:37
 */
@Unique(uniqueFields = { "area_name" }, tableName = "t_area")
public class AreaForm extends BaseForm {

	// 区域id
	private String areaId;

	// 区域编号
	private String areaCode;

	// 区域经度
	private BigDecimal areaLongitude;

	// 区域纬度
	private BigDecimal areaLatitude;

	// 区域名称
	private String areaName;

	// 上级区域的id
	private String parentAreaId;

	// 区域备注信息
	private String memo;

	public BigDecimal getAreaLongitude() {
		return areaLongitude;
	}

	public void setAreaLongitude(BigDecimal areaLongitude) {
		this.areaLongitude = areaLongitude;
	}

	public BigDecimal getAreaLatitude() {
		return areaLatitude;
	}

	public void setAreaLatitude(BigDecimal areaLatitude) {
		this.areaLatitude = areaLatitude;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(String parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	// 参数校验
	public StatusCode validate() {
		StatusCode statusCode = null;
		if (StringUtils.isBlank(areaName)) {
			statusCode = StatusCode.AREA_NAME_BLANK;
		}
		return statusCode;
	}

	@Override
	public Object[] validateUnique() {
		return new Object[] { areaName };
	}
}
