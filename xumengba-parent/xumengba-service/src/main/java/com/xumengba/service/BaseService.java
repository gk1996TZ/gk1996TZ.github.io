package com.xumengba.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.xumengba.domain.BaseEntity;
import com.xumengba.page.PageView;

public interface BaseService<T extends BaseEntity> {

	/**
	 * 添加实体的方法
	 * @param t 传入一个实体
	 */
	public void save(T t);
	/**
	 * 根据id真实删除
	 * @param id 传入一个id
	 */
	public void deleteByIdReal(String id);
	/**
	 * 根据id逻辑删除
	 * @param id 传入一个id
	 */
	public void deleteById(String id);
	/**
	 * 添加实体列表的方法
	 * @param list 传入一个实体列表
	 */
	public void saveAll(List<T> list);
	/**
	 * 根据id修改
	 * @param t 传入一个实体
	 */
	public void editById(T t);
	
	/**
	 * 根据id查询对象
	 * @param id 传入一个id
	 * @return 返回对象
	 */
	public T queryById(String id);
	public PageView<T> queryPageData(Long currentPageNum, Long pageSize, 
			 String whereSQL,List<Object> whereParams,
			 LinkedHashMap<String, String> orderBy);
	
	public PageView<T> queryPageData();
	
	/**
	 * @Description: 根据条件查询数据 
	 * @param: wherSQL 传入查询的sql语句
	 * @param: whereParams 传入查询的条件
	 * @return: List<T> 返回数据列表
	 */
	public List<T> queryData(String wherSQL,List<Object> whereParams);
	
	/**
	 * @Description: 查询所有的数据
	 * @return: List<T> 返回所有的数据列表
	 */
	public List<T> queryData();
}
