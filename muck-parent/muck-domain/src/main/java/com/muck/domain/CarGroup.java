package com.muck.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 * @Description: 车辆组实体类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月25日 下午8:34:58
 */
@Table(name="t_car_group")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CarGroup extends BaseEntity{

	/**父级id*/
	private String parentId;
	
	/**车辆组编号*/
	private String groupCode;
	
	/**车辆组名称*/
	private String groupName;

	/**车辆组所属企业id*/
	private String companyId;
	
	/**车辆组所属企业名称*/
	private String companyName;
	
	/**车辆组下所有的车辆*/
	private List<Car> cars = new ArrayList<Car>();
	
	/**一级车辆组下的二级车辆组*/
	private List<CarGroup> carGroups;
	
	/**车辆组车辆数*/
	private Integer total;
	
	/**车辆组备注信息*/
	private String memo;
	public CarGroup() {
	}

	public CarGroup(String groupCode, String groupName) {
		this.groupCode = groupCode;
		this.groupName = groupName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	public List<CarGroup> getCarGroups() {
		return carGroups;
	}

	public void setCarGroups(List<CarGroup> carGroups) {
		this.carGroups = carGroups;
	}

	public Integer getTotal() {
		
		return this.total == null ? 0 : total;
	}

	public void setTotal(Integer total) {
		this.total = total;
		
	}
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "CarGroup [parentId=" + parentId + ", groupCode=" + groupCode + ", groupName=" + groupName
				+ ", companyId=" + companyId + ", companyName=" + companyName + ", cars=" + cars + ", carGroups="
				+ carGroups + ", total=" + total + ", memo=" + memo + "]";
	}
}
