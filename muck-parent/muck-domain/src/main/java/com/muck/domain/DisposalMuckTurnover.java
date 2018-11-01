package com.muck.domain;

import java.io.Serializable;
import java.util.Date;

import com.muck.annotation.Table;

/**
 * @Description: 处置场渣土进出信息实体类
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月7日 上午9:28:25
 */
@Table(name="t_disposal_muck_turnover")
public class DisposalMuckTurnover extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -7743652777990639922L;
	/**处置场id*/
	private String disposalId;
	/**渣土进出信息，*/
	private String muckTurnoverMessage;
	/**渣土运输人员*/
	private String muckTransportPerson;
	/**渣土进出时间*/
	private Date muckTurnoverTime;
	/**门卫人员*/
	private String guardPerson;
	/**备注信息*/
	private String memo;
	public DisposalMuckTurnover() {
	}
	public DisposalMuckTurnover(String disposalId, String muckTurnoverMessage, String muckTransportPerson,
			Date muckTurnoverTime, String guardPerson, String memo) {
		super();
		this.disposalId = disposalId;
		this.muckTurnoverMessage = muckTurnoverMessage;
		this.muckTransportPerson = muckTransportPerson;
		this.muckTurnoverTime = muckTurnoverTime;
		this.guardPerson = guardPerson;
		this.memo = memo;
	}
	public String getDisposalId() {
		return disposalId;
	}
	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}
	public String getMuckTurnoverMessage() {
		return muckTurnoverMessage;
	}
	public void setMuckTurnoverMessage(String muckTurnoverMessage) {
		this.muckTurnoverMessage = muckTurnoverMessage;
	}
	public String getMuckTransportPerson() {
		return muckTransportPerson;
	}
	public void setMuckTransportPerson(String muckTransportPerson) {
		this.muckTransportPerson = muckTransportPerson;
	}
	public Date getMuckTurnoverTime() {
		return muckTurnoverTime;
	}
	public void setMuckTurnoverTime(Date muckTurnoverTime) {
		this.muckTurnoverTime = muckTurnoverTime;
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
	public String toString() {
		return "DisposalMuckTurnover [disposalId=" + disposalId + ", muckTurnoverMessage=" + muckTurnoverMessage
				+ ", muckTransportPerson=" + muckTransportPerson + ", muckTurnoverTime=" + muckTurnoverTime
				+ ", guardPerson=" + guardPerson + ", memo=" + memo + "]";
	}
}
