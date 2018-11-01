package com.muck.domain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 *	颜色实体 
*/
@Table(name = "t_color")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Color extends BaseEntity{

	// 中文颜色名称
    private String colorNameZh;

    // 英文颜色名称
    private String colorNameEn;

    // 颜色编码
    private Integer colorCode;

    // 颜色值,十六进制
    private String colorValue;

    // 颜色是否被占用,使用
    private Boolean colorOccupancy;

    // 颜色说明
    private String memo;

	public String getColorNameZh() {
		return colorNameZh;
	}

	public void setColorNameZh(String colorNameZh) {
		this.colorNameZh = colorNameZh;
	}

	public String getColorNameEn() {
		return colorNameEn;
	}

	public void setColorNameEn(String colorNameEn) {
		this.colorNameEn = colorNameEn;
	}

	public Integer getColorCode() {
		return colorCode;
	}

	public void setColorCode(Integer colorCode) {
		this.colorCode = colorCode;
	}

	public String getColorValue() {
		return colorValue;
	}

	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}

	public Boolean getColorOccupancy() {
		return colorOccupancy;
	}

	public void setColorOccupancy(Boolean colorOccupancy) {
		this.colorOccupancy = colorOccupancy;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}