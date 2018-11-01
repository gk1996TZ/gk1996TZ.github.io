package com.muck.domain;

import java.io.Serializable;
import java.util.Date;

import com.muck.annotation.Table;

/**
 * @Description: 处置场车辆进出数据实体类
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月4日 下午7:01:52
 */
@Table(name="t_disposal_car_trunoner")
public class DisposalCarTurnover extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -6868581416679337110L;
	/**处置场编号*/
	private Long disposalId;
	/**车辆进出信息*/
	private String carTurnoverMessage;
	/**车辆驾驶人员*/
	private String carTransoportPerson;
	/**车辆进出时间*/
	private Date carTurnoverTime;
	/**门卫人员*/
	private String guardPerson;
	/**备注信息*/
	private String memo;
	public DisposalCarTurnover() {
	}
	public DisposalCarTurnover(Long disposalId, String carTurnoverMessage, String carTransoportPerson,
			Date carTurnoverTime, String guardPerson, String memo) {
		super();
		this.disposalId = disposalId;
		this.carTurnoverMessage = carTurnoverMessage;
		this.carTransoportPerson = carTransoportPerson;
		this.carTurnoverTime = carTurnoverTime;
		this.guardPerson = guardPerson;
		this.memo = memo;
	}
	public Long getDisposalId() {
		return disposalId;
	}
	public void setDisposalId(Long disposalId) {
		this.disposalId = disposalId;
	}
	public String getCarTurnoverMessage() {
		return carTurnoverMessage;
	}
	public void setCarTurnoverMessage(String carTurnoverMessage) {
		this.carTurnoverMessage = carTurnoverMessage;
	}
	public String getCarTransoportPerson() {
		return carTransoportPerson;
	}
	public void setCarTransoportPerson(String carTransoportPerson) {
		this.carTransoportPerson = carTransoportPerson;
	}
	public Date getCarTurnoverTime() {
		return carTurnoverTime;
	}
	public void setCarTurnoverTime(Date carTurnoverTime) {
		this.carTurnoverTime = carTurnoverTime;
	}
	public String getGuardPerson() {
		return guardPerson;
	}
	public void setGuardPerson(String guardPerson) {
		this.guardPerson = guardPerson;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carTransoportPerson == null) ? 0 : carTransoportPerson.hashCode());
		result = prime * result + ((carTurnoverMessage == null) ? 0 : carTurnoverMessage.hashCode());
		result = prime * result + ((carTurnoverTime == null) ? 0 : carTurnoverTime.hashCode());
		result = prime * result + ((disposalId == null) ? 0 : disposalId.hashCode());
		result = prime * result + ((guardPerson == null) ? 0 : guardPerson.hashCode());
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
		DisposalCarTurnover other = (DisposalCarTurnover) obj;
		if (carTransoportPerson == null) {
			if (other.carTransoportPerson != null)
				return false;
		} else if (!carTransoportPerson.equals(other.carTransoportPerson))
			return false;
		if (carTurnoverMessage == null) {
			if (other.carTurnoverMessage != null)
				return false;
		} else if (!carTurnoverMessage.equals(other.carTurnoverMessage))
			return false;
		if (carTurnoverTime == null) {
			if (other.carTurnoverTime != null)
				return false;
		} else if (!carTurnoverTime.equals(other.carTurnoverTime))
			return false;
		if (disposalId == null) {
			if (other.disposalId != null)
				return false;
		} else if (!disposalId.equals(other.disposalId))
			return false;
		if (guardPerson == null) {
			if (other.guardPerson != null)
				return false;
		} else if (!guardPerson.equals(other.guardPerson))
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
		return "DisposalCarTurnover [disposalId=" + disposalId + ", carTurnoverMessage=" + carTurnoverMessage
				+ ", carTransoportPerson=" + carTransoportPerson + ", carTurnoverTime=" + carTurnoverTime
				+ ", guardPerson=" + guardPerson + ", memo=" + memo + "]";
	}
}
