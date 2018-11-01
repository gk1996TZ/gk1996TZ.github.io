package com.litang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 数据库连接层基类
 */
public interface BaseMapper<T> {

	/**
	 * @Description: 添加一个实体
	 * @Param: t 传入一个被添加的实体
	 * @Return: 返回受影响行数
	 */
	public Integer insert(T t);

	/**
	 * @Description: 批量添加的操作
	 * @Param: list 传入一个实体列表
	 * @Return: 返回受影响行数
	 */
	public Integer insertAll(List<T> list);

	/**
	 * @Description: 根据id删除（逻辑删除）
	 * @Param: id 传入一个id
	 * @Return: 返回受影响行数
	 */
	public Integer deleteById(String id);

	/**
	 * @Description: 根据id删除（真实删除）
	 * @Param: id 传入一个id
	 * @Return: 返回受影响行数
	 */
	public Integer deleteByIdReal(String id);

	/**
	 * @Description: 根据id修改
	 * @Param: t 传入一个被修改的实体
	 * @Return: 返回受影响行数
	 */
	public Integer editById(T t);

	/**
	 * @Description: 根据id查询
	 * @Param: id 传入一个id
	 * @Return: 返回查询到的实体
	 */
	public T queryById(String id);

	// 查询分页数据
	public List<T> selectPageData(@Param("sql") String sql);

	// 统计条数
	public Long selectTotalRecoreds(@Param("sql") String sql);

	// 根据条件查询数据
	public List<T> queryData(@Param("sql") String sql);

	// 根据条件删除
	public void deleteData(@Param("sql") String sql);
}
