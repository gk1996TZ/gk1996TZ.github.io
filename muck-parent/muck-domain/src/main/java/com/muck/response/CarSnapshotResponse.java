package com.muck.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class CarSnapshotResponse {

	/**车辆识别记录id*/
	private String id;
	
	/**抓拍类别名称*/
	private String typeName;
	
	/**相应名称*/
	private String relevantName;
	
	/**抓拍类别id*/
	private String typeId;
	
	/**车牌号*/
	private String carCode;
	
	/**车牌颜色*/
	private String carCardColor;
	
	/**企业id*/
	private String companyId;
	
	/**企业名称*/
	private String companyName;
	
	/**企业联系方式*/
	private String companyContact;
	
	/**大图图片路径*/
	private String bigImage;
	
	/**小图图片路径*/
	private String smailImage;
	
	/**图片*/
	private List<String> image = new ArrayList<String>();
	
	/**车辆进出时间*/
	private String time;

	/**当前车辆在一天内出或入的次数*/
	private Integer count;
	
	public List<String> getImage() {
		if(!StringUtils.isBlank(bigImage)){
			image.add(bigImage);
		}
		if(!StringUtils.isBlank(smailImage)){
			image.add(smailImage);
		}
		return image;
	}

	public void setImage(List<String> image) {
		this.image = image;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getCarCardColor() {
		return carCardColor;
	}

	public void setCarCardColor(String carCardColor) {
		this.carCardColor = carCardColor;
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
	
	public String getRelevantName() {
		return relevantName;
	}

	public void setRelevantName(String relevantName) {
		this.relevantName = relevantName;
	}

	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}
	public String getBigImage() {
		return bigImage;
	}

	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}

	public String getSmailImage() {
		return smailImage;
	}

	public void setSmailImage(String smailImage) {
		this.smailImage = smailImage;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
