package com.muck.domain;

import java.util.Date;

import com.muck.annotation.Table;

/**
 * @Description: 操作日志
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月28日 下午2:48:16
 */
@Table(name = "t_log")
public class Log extends BaseEntity {

	// 操作类型
	private String operatorType;
	
	// 操作时间
	private Date operatorTime;

	// 操作的反馈结果(成功success，失败failure)
	private String operatorResult;
	
	// 操作的结果信息
	private String operatorResultMsg;
	
	// 操作的参数
	private String operatorParams;

	// 操作人的账号
	private String operatorAccount;

	// 操作人的姓名
	private String operatorName;
	
	// 操作人联系方式
	private String operatorPhone;
	
	// 描述说明
	private String memo;

	// 操作的模块
	private String operatorModel;

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorPhone() {
		return operatorPhone;
	}

	public void setOperatorPhone(String operatorPhone) {
		this.operatorPhone = operatorPhone;
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	public String getOperatorResult() {
		return operatorResult;
	}

	public void setOperatorResult(String operatorResult) {
		this.operatorResult = operatorResult;
	}

	public String getOperatorAccount() {
		return operatorAccount;
	}

	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(String operatorModel) {
		this.operatorModel = operatorModel;
	}

	public String getOperatorResultMsg() {
		return operatorResultMsg;
	}

	public void setOperatorResultMsg(String operatorResultMsg) {
		this.operatorResultMsg = operatorResultMsg;
	}

	public String getOperatorParams() {
		return operatorParams;
	}

	public void setOperatorParams(String operatorParams) {
		this.operatorParams = operatorParams;
	}
}
