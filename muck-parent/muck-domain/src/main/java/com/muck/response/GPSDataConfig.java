package com.muck.response;

/**
 * 
 *	车辆GPS数据配置信息
*/
public class GPSDataConfig {
	
	/**	获取定时的任务配置在几点	*/
	private Integer hour;
	
	/**	获取定时的任务配置在几分钟  **/
	private Integer minute;
	
	/**	设置要保留的GPS车载数据的月份	**/
	private Integer month;
	
	public GPSDataConfig() {
	
	}

	public GPSDataConfig(Integer hour, Integer minute,Integer month) {
		this.hour = hour;
		this.minute = minute;
		this.month = month;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}
