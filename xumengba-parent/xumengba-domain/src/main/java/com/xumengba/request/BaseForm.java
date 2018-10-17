package com.xumengba.request;

import com.xumengba.response.StatusCode;

/**
* @Description: 请求参数基类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月13日 下午2:22:54
 */
public abstract class BaseForm {

	public abstract StatusCode validate();
	
	public Object[] validateUnique(){
		return null;
	}
	
	// 当前请求的页码,默认是第1页
	private Long pageNum = 1L;
	
	// 每页显示多少条件记录
	private Long pageSize = 10L;
	
	// 标志字段,用于判断用户的输入是否是从查询来的
	private String query;

	public Long getPageNum() {
		return (pageNum == null || pageNum <= 0L) ? 1L : pageNum;
	}

	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
