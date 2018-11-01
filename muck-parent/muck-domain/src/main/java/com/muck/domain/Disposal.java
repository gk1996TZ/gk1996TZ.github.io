package com.muck.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 * @Description:处置场信息实体类
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月4日 下午6:01:39
 */
@Table(name = "t_disposal")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Disposal extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 7026193171520448419L;
	/** 处置场编号 */
	private String disposalId;
	/** 处置场名称 */
	private String disposalName;
	/** 处置场渣土容量 */
	private Integer disposalMuckCapacity;
	/** 处置场区域id */
	private String areaId;
	/** 处置场所属区域编号 */
	private String areaCode;
	/** 处置场所属区域名称 */
	private String areaName;
	/** 处置场地址 */
	private String disposalAddress;
	/** 处置场所属企业id */
	private String companyId;
	/** 处置场所属企业名称 */
	private String companyName;
	/** 处置场注册码 */
	private String disposalRegisterCode;
	/** 备注信息 */
	private String memo;
	/** 处置场负责人 */
	private List<Manager> manager;
	/** 处置场负责人1 */
	private String disposalPrincipalName1;
	/** 处置场负责人1的联系方式 */
	private String disposalPrincipalPhone1;
	/** 处置场负责人2 */
	private String disposalPrincipalName2;
	/** 处置场负责人2的联系方式 */
	private String disposalPrincipalPhone2;
	/** 处置场负责人3 */
	private String disposalPrincipalName3;
	/** 处置场负责人3的联系方式 */
	private String disposalPrincipalPhone3;
	/** 处置场负责人4 */
	private String disposalPrincipalName4;
	/** 处置场负责人4的联系方式 */
	private String disposalPrincipalPhone4;
	/** 处置场负责人5 */
	private String disposalPrincipalName5;
	/** 处置场负责人5的联系方式 */
	private String disposalPrincipalPhone5;

	public Disposal() {
	}

	public Disposal(String disposalId, String disposalName, Integer disposalMuckCapacity, String areaId,
			String areaCode, String areaName, String disposalAddress, String companyId, String companyName, String memo,
			List<Manager> manager, String disposalPrincipalName1, String disposalRegisterCode,
			String disposalPrincipalPhone1, String disposalPrincipalName2, String disposalPrincipalPhone2,
			String disposalPrincipalName3, String disposalPrincipalPhone3, String disposalPrincipalName4,
			String disposalPrincipalPhone4, String disposalPrincipalName5, String disposalPrincipalPhone5,
			String channelCode, String deviceCode) {
		super();
		this.disposalId = disposalId;
		this.disposalName = disposalName;
		this.disposalMuckCapacity = disposalMuckCapacity;
		this.areaId = areaId;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.disposalAddress = disposalAddress;
		this.companyId = companyId;
		this.companyName = companyName;
		this.disposalRegisterCode = disposalRegisterCode;
		this.memo = memo;
		this.manager = manager;
		this.disposalPrincipalName1 = disposalPrincipalName1;
		this.disposalPrincipalPhone1 = disposalPrincipalPhone1;
		this.disposalPrincipalName2 = disposalPrincipalName2;
		this.disposalPrincipalPhone2 = disposalPrincipalPhone2;
		this.disposalPrincipalName3 = disposalPrincipalName3;
		this.disposalPrincipalPhone3 = disposalPrincipalPhone3;
		this.disposalPrincipalName4 = disposalPrincipalName4;
		this.disposalPrincipalPhone4 = disposalPrincipalPhone4;
		this.disposalPrincipalName5 = disposalPrincipalName5;
		this.disposalPrincipalPhone5 = disposalPrincipalPhone5;
		this.channelCode = channelCode;
		this.deviceCode = deviceCode;
	}


	public String getDisposalRegisterCode() {
		return disposalRegisterCode;
	}

	public void setDisposalRegisterCode(String disposalRegisterCode) {
		this.disposalRegisterCode = disposalRegisterCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	public String getDisposalName() {
		return disposalName;
	}

	public void setDisposalName(String disposalName) {
		this.disposalName = disposalName;
	}

	public Integer getDisposalMuckCapacity() {
		return disposalMuckCapacity;
	}

	public void setDisposalMuckCapacity(Integer disposalMuckCapacity) {
		this.disposalMuckCapacity = disposalMuckCapacity;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDisposalAddress() {
		return disposalAddress;
	}

	public void setDisposalAddress(String disposalAddress) {
		this.disposalAddress = disposalAddress;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<Manager> getManager() {
		return manager;
	}

	public void setManager(List<Manager> manager) {
		this.manager = manager;
	}

	public String getDisposalPrincipalName1() {
		return disposalPrincipalName1;
	}

	public void setDisposalPrincipalName1(String disposalPrincipalName1) {
		this.disposalPrincipalName1 = disposalPrincipalName1;
	}

	public String getDisposalPrincipalPhone1() {
		return disposalPrincipalPhone1;
	}

	public void setDisposalPrincipalPhone1(String disposalPrincipalPhone1) {
		this.disposalPrincipalPhone1 = disposalPrincipalPhone1;
	}

	public String getDisposalPrincipalName2() {
		return disposalPrincipalName2;
	}

	public void setDisposalPrincipalName2(String disposalPrincipalName2) {
		this.disposalPrincipalName2 = disposalPrincipalName2;
	}

	public String getDisposalPrincipalPhone2() {
		return disposalPrincipalPhone2;
	}

	public void setDisposalPrincipalPhone2(String disposalPrincipalPhone2) {
		this.disposalPrincipalPhone2 = disposalPrincipalPhone2;
	}

	public String getDisposalPrincipalName3() {
		return disposalPrincipalName3;
	}

	public void setDisposalPrincipalName3(String disposalPrincipalName3) {
		this.disposalPrincipalName3 = disposalPrincipalName3;
	}

	public String getDisposalPrincipalPhone3() {
		return disposalPrincipalPhone3;
	}

	public void setDisposalPrincipalPhone3(String disposalPrincipalPhone3) {
		this.disposalPrincipalPhone3 = disposalPrincipalPhone3;
	}

	public String getDisposalPrincipalName4() {
		return disposalPrincipalName4;
	}

	public void setDisposalPrincipalName4(String disposalPrincipalName4) {
		this.disposalPrincipalName4 = disposalPrincipalName4;
	}

	public String getDisposalPrincipalPhone4() {
		return disposalPrincipalPhone4;
	}

	public void setDisposalPrincipalPhone4(String disposalPrincipalPhone4) {
		this.disposalPrincipalPhone4 = disposalPrincipalPhone4;
	}

	public String getDisposalPrincipalName5() {
		return disposalPrincipalName5;
	}

	public void setDisposalPrincipalName5(String disposalPrincipalName5) {
		this.disposalPrincipalName5 = disposalPrincipalName5;
	}

	public String getDisposalPrincipalPhone5() {
		return disposalPrincipalPhone5;
	}

	public void setDisposalPrincipalPhone5(String disposalPrincipalPhone5) {
		this.disposalPrincipalPhone5 = disposalPrincipalPhone5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaCode == null) ? 0 : areaCode.hashCode());
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((areaName == null) ? 0 : areaName.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((disposalId == null) ? 0 : disposalId.hashCode());
		result = prime * result + ((disposalMuckCapacity == null) ? 0 : disposalMuckCapacity.hashCode());
		result = prime * result + ((disposalName == null) ? 0 : disposalName.hashCode());
		result = prime * result + ((disposalPrincipalName1 == null) ? 0 : disposalPrincipalName1.hashCode());
		result = prime * result + ((disposalPrincipalName2 == null) ? 0 : disposalPrincipalName2.hashCode());
		result = prime * result + ((disposalPrincipalName3 == null) ? 0 : disposalPrincipalName3.hashCode());
		result = prime * result + ((disposalPrincipalName4 == null) ? 0 : disposalPrincipalName4.hashCode());
		result = prime * result + ((disposalPrincipalName5 == null) ? 0 : disposalPrincipalName5.hashCode());
		result = prime * result + ((disposalPrincipalPhone1 == null) ? 0 : disposalPrincipalPhone1.hashCode());
		result = prime * result + ((disposalPrincipalPhone2 == null) ? 0 : disposalPrincipalPhone2.hashCode());
		result = prime * result + ((disposalPrincipalPhone3 == null) ? 0 : disposalPrincipalPhone3.hashCode());
		result = prime * result + ((disposalPrincipalPhone4 == null) ? 0 : disposalPrincipalPhone4.hashCode());
		result = prime * result + ((disposalPrincipalPhone5 == null) ? 0 : disposalPrincipalPhone5.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disposal other = (Disposal) obj;
		if (areaCode == null) {
			if (other.areaCode != null)
				return false;
		} else if (!areaCode.equals(other.areaCode))
			return false;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (areaName == null) {
			if (other.areaName != null)
				return false;
		} else if (!areaName.equals(other.areaName))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (disposalId == null) {
			if (other.disposalId != null)
				return false;
		} else if (!disposalId.equals(other.disposalId))
			return false;
		if (disposalMuckCapacity == null) {
			if (other.disposalMuckCapacity != null)
				return false;
		} else if (!disposalMuckCapacity.equals(other.disposalMuckCapacity))
			return false;
		if (disposalName == null) {
			if (other.disposalName != null)
				return false;
		} else if (!disposalName.equals(other.disposalName))
			return false;
		if (disposalPrincipalName1 == null) {
			if (other.disposalPrincipalName1 != null)
				return false;
		} else if (!disposalPrincipalName1.equals(other.disposalPrincipalName1))
			return false;
		if (disposalPrincipalName2 == null) {
			if (other.disposalPrincipalName2 != null)
				return false;
		} else if (!disposalPrincipalName2.equals(other.disposalPrincipalName2))
			return false;
		if (disposalPrincipalName3 == null) {
			if (other.disposalPrincipalName3 != null)
				return false;
		} else if (!disposalPrincipalName3.equals(other.disposalPrincipalName3))
			return false;
		if (disposalPrincipalName4 == null) {
			if (other.disposalPrincipalName4 != null)
				return false;
		} else if (!disposalPrincipalName4.equals(other.disposalPrincipalName4))
			return false;
		if (disposalPrincipalName5 == null) {
			if (other.disposalPrincipalName5 != null)
				return false;
		} else if (!disposalPrincipalName5.equals(other.disposalPrincipalName5))
			return false;
		if (disposalPrincipalPhone1 == null) {
			if (other.disposalPrincipalPhone1 != null)
				return false;
		} else if (!disposalPrincipalPhone1.equals(other.disposalPrincipalPhone1))
			return false;
		if (disposalPrincipalPhone2 == null) {
			if (other.disposalPrincipalPhone2 != null)
				return false;
		} else if (!disposalPrincipalPhone2.equals(other.disposalPrincipalPhone2))
			return false;
		if (disposalPrincipalPhone3 == null) {
			if (other.disposalPrincipalPhone3 != null)
				return false;
		} else if (!disposalPrincipalPhone3.equals(other.disposalPrincipalPhone3))
			return false;
		if (disposalPrincipalPhone4 == null) {
			if (other.disposalPrincipalPhone4 != null)
				return false;
		} else if (!disposalPrincipalPhone4.equals(other.disposalPrincipalPhone4))
			return false;
		if (disposalPrincipalPhone5 == null) {
			if (other.disposalPrincipalPhone5 != null)
				return false;
		} else if (!disposalPrincipalPhone5.equals(other.disposalPrincipalPhone5))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Disposal [disposalId=" + disposalId + ", disposalName=" + disposalName + ", disposalMuckCapacity="
				+ disposalMuckCapacity + ", areaId=" + areaId + ", areaCode=" + areaCode + ", areaName=" + areaName
				+ ", companyId=" + companyId + ", companyName=" + companyName + ", memo=" + memo + ", manager="
				+ manager + ", disposalPrincipalName1=" + disposalPrincipalName1 + ", disposalPrincipalPhone1="
				+ disposalPrincipalPhone1 + ", disposalPrincipalName2=" + disposalPrincipalName2
				+ ", disposalPrincipalPhone2=" + disposalPrincipalPhone2 + ", disposalPrincipalName3="
				+ disposalPrincipalName3 + ", disposalPrincipalPhone3=" + disposalPrincipalPhone3
				+ ", disposalPrincipalName4=" + disposalPrincipalName4 + ", disposalPrincipalPhone4="
				+ disposalPrincipalPhone4 + ", disposalPrincipalName5=" + disposalPrincipalName5
				+ ", disposalPrincipalPhone5=" + disposalPrincipalPhone5 + "]";
	}

	// -------------

	private String channelCode;

	private String deviceCode;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
}
