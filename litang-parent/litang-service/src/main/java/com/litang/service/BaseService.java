package com.litang.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.litang.page.PageView;

/**
 * 业务层基类
 */
public interface BaseService<T> {

	/**
	 * @Description: 添加一个实体
	 * @Param: t 传入一个被添加的实体
	 */
	public boolean insert(T t);
	/**
	 * @Description: 根据id删除（逻辑删除）
	 * @param id 传入一个id
	 */
	public boolean deleteById(String id);
	/**
	 * @Description: 根据id删除（真实删除）
	 * @Param: id 传入一个id
	 */
	public boolean deleteByIdReal(String id);
	/**
	 * @Description: 根据id修改
	 * @Param: t 传入一个被修改的实体
	 */
	public boolean editById(T t);
	/**
	 * @Description: 根据id查询
	 * @Param: id 传入一个id
	 * @Return: 返回查询到的实体
	 */
	public T queryById(String id);
	
	public PageView<T> queryPageData(Long currentPageNum, Long pageSize, 
			 String whereSQL,List<Object> whereParams,
			 LinkedHashMap<String, String> orderBy);
	
	public PageView<T> queryPageData();
	/**
	 * @Description: 根据条件查询数据 
	 * @version: v1.0.0
	 * @param: wherSQL 传入查询的sql语句
	 * @param: whereParams 传入查询的条件
	 * @return: List<T> 返回数据列表
	 */
	public List<T> queryData(String wherSQL,List<Object> whereParams);
	
	/**
	 * @Description: 查询所有的数据
	 * @version: v1.0.0
	 * @return: List<T> 返回所有的数据列表
	 */
	public List<T> queryData();
}
