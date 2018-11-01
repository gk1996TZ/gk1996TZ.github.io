package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description: 条件查询操作日志参数
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月19日 下午4:53:20
 */
public class LogQueryForm extends BaseForm {

	// 日志id
	private String id;
	// 账号
	private String userAccount;

	// 时间起止
	private String timeStartEnd;
	
	// 最近几个月
	private Integer month = 2;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getTimeStartEnd() {
		return timeStartEnd;
	}

	public void setTimeStartEnd(String timeStartEnd) {
		this.timeStartEnd = timeStartEnd;
	}

	@Override
	public StatusCode validate() {
		return null;
	}
}
