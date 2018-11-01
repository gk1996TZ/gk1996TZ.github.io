package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.muck.domain.BaseEntity;

/**
* @Description: 基础Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月12日 下午5:15:52
 */
public interface BaseMapper<T extends BaseEntity> {

	// 添加
	public void insert(T entity);
	
	/**
	 * @Description: 添加所有的
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月27日  下午5:08:15
	 * @param:@param list传入一个需要被添加的数据列表
	 */
	public void insertAll(List<T> list);
	
	// 根据id删除(逻辑删除)
	public void deleteById(String id);
	
	// 根据id删除(真实删除)
	public void deleteByIdReal(String id);
	
	// 根据id修改实体对象
	public void updateById(T entity);

	// 根据id查询实体对象
	public T selectById(String id);
	
	// 查询分页数据
	public List<T> selectPageData(@Param("sql")String sql);
	
	// 统计条数
	public Long selectTotalRecoreds(@Param("sql")String sql);
	
	// 根据条件查询数据
	public List<T> queryData(@Param("sql")String sql);
	
	//根据条件删除
	public void  deleteData(@Param("sql")String sql);
	
	// 批量删除从大华那边同步的数据(逻辑删除)
	public void deleteBatchDaHuaSynData();

	/**
	 * 	根据id查询实体对象
	 * 		只是查询对象的基本信息,不包括对象之间的关联信息
	*/
	public T selectByIdSimple(String id);
}
