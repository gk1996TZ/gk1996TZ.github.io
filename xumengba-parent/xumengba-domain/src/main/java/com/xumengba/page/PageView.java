package com.xumengba.page;

import java.util.List;

/**
* @Description: 这个pageView这个对象就表示了当前页面的数据
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 下午3:23:02
*/
public class PageView<T> {

	// 用户的显示数据列表
	private List<T> datas;
	
	// 总记录数
	private long totalRecored;
	
	// 每页显示多少条记录
	private long pageSize = 10L;
	
	// 应该有多少页
	private long totalPage;
	
	// 表示的是数据库中应该从哪个位置开始
	private long startIndex;
	
	// 表示的是当前页
	private long currentPage;
	
	// 表示的是展示给前端页面显示的时候的开始页码,这个页码是取决于当前页
	private long startPageNo;
	
	// 表示的是展示给前端页面显示的结束页码,这个页码是取决于当前页
	private long endPageNo;
	
	public PageView(Long totalRecored,Long currentPage,Long pageSize){

		this.totalRecored = totalRecored; // 总记录数有了
		this.currentPage = (currentPage == null || currentPage <= 0L) ? 1L : currentPage; // 当前页
		this.pageSize = pageSize == null ? getPageSize() : pageSize;
		
		if(this.totalRecored % this.pageSize == 0L){
			this.totalPage = this.totalRecored / this.pageSize;
		}else{
			this.totalPage = this.totalRecored / this.pageSize + 1;
		}
		
		this.startIndex = (this.currentPage - 1) * this.pageSize;
		
		/*
		 * 		我现在一个页面就显示10个页码
		 * 
		 * 			1、我的总页数假如说就是8页
		*/
		if(this.totalPage <= 10L){
			this.startPageNo = 1L;
			this.endPageNo = this.totalPage;
		}else{
			
			this.startPageNo = this.currentPage - 5;
			this.endPageNo = this.currentPage + 4;
			
			if(this.startPageNo < 1){
				this.startPageNo = 1L;
				this.endPageNo = 10L;
			}
			
			if(this.endPageNo > this.totalPage){
				this.startPageNo = this.totalPage - 9;
				this.endPageNo = this.totalPage;
			}
		}
	}
	
	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public long getTotalRecored() {
		return totalRecored;
	}

	public void setTotalRecored(long totalRecored) {
		this.totalRecored = totalRecored;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getStartPageNo() {
		return startPageNo;
	}

	public void setStartPageNo(long startPageNo) {
		this.startPageNo = startPageNo;
	}

	public long getEndPageNo() {
		return endPageNo;
	}

	public void setEndPageNo(long endPageNo) {
		this.endPageNo = endPageNo;
	}
}
