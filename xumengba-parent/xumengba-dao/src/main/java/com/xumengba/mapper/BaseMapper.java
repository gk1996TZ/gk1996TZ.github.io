package com.xumengba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.xumengba.domain.BaseEntity;
import com.xumengba.mapper.provider.InsertMapperProvider;
import com.xumengba.mapper.provider.UpdateMapperProvider;

@Mapper
public interface BaseMapper<T> {

	/**
	 * @Description:添加单个对象
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:16:30
	 * @param:  baseEntity 传入单个对象
	 * @return: Integer 返回受影响行数
	 */
	@InsertProvider(type=InsertMapperProvider.class,method="insert")
	@SelectKey(before=false,keyColumn="id",keyProperty="idRaw",statement="SELECT LAST_INSERT_ID()",resultType=Long.class)
	public Integer insert(BaseEntity baseEntity);
	/**
	 * @Description: 添加对象列表的操作
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:17:19
	 * @param: objects 传入一个对象列表
	 * @return: Integer 返回受影响行数
	 */
	@InsertProvider(type=InsertMapperProvider.class,method="insertAll")
	public Integer insertAll(List<? extends BaseEntity> objects);
	/**
	 * @Description: 根据id真实删除
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:17:58
	 * @param: tableName 传入一个表名
	 * @param: id 传入一个id
	 * @return: Integer 返回受影响行数
	 */
	@Delete("delete from ${tableName} where id= #{id,typeHandler=com.xumengba.handler.IdTypeHandler}")
	public Integer deleteByIdReal(@Param("tableName")String tableName,@Param("id")String id);
	/**
	 * @Description: 根据id逻辑删除删除
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:18:50
	 * @param: tableName 传入一个表名
	 * @param: id 传入一个id
	 * @return: Integer 返回受影响行数
	 */
	@Update("update  ${tableName} set deleted = 0 where id= #{id,typeHandler=com.xumengba.handler.IdTypeHandler}")
	public Integer deleteById(@Param("tableName")String tableName,@Param("id")String id);
	/**
	 * @Description: 修改数据的操作（根据id修改）
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:19:36
	 * @param: obj 传入一个需要被修改的对象
	 * @return: Integer 返回受影响行数
	 */
	@UpdateProvider(type=UpdateMapperProvider.class,method="updateById")
	public Integer updateById(Object obj);
	/**
	 * @Description: 根据id查询对象
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:20:45
	 * @param: id 传入一个id
	 * @return: T 返回查询到的对象
	 */
	public T queryById(String id);
	/**
	 * @Description: 查询分页数据
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:21:18
	 * @param: sql 传入一个sql
	 * @return: List<T> 返回分页数据
	 */
	public List<T> selectPageData(String sql);
	/**
	 * @Description: 统计数据条数
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:21:52
	 * @param: sql 传入一个sql
	 * @return: Long 返回数据条数
	 */
	public Long selectTotalRecoreds(String sql);
	/**
	 * @Description: 根据条件查询数据列表
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:22:26
	 * @param: sql 传入一个sql
	 * @return: List<T> 返回数据列表
	 */
	public List<T> queryData(String sql);
}
