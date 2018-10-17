package com.xumengba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.xumengba.domain.Category;
import com.xumengba.domain.User;
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
	
	//查询所有的一级分类
	@Select("select id,category_name,memo,created_time,updated_time from t_category where parent_id is null and deleted=1")
	@Results(id="categoryResultMap",value={
			@Result(property = "id", column = "id" ,typeHandler=com.xumengba.handler.IdTypeHandler.class),
			@Result(property = "categoryName", column = "category_name", jdbcType=JdbcType.VARCHAR),
			@Result(property = "memo", column = "memo",jdbcType=JdbcType.VARCHAR),
			@Result(property = "createdTime", column = "created_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property = "updatedTime", column = "updated_time",jdbcType=JdbcType.TIMESTAMP),
		})
	public List<Category> queryTopCategory();
	
	//根据id查询子分类
	@ResultMap(value="categoryResultMap")
	@Select("select id,category_name,memo,parent_id,created_time,updated_time from t_category where parent_id=#{parentId,typeHandler=com.xumengba.handler.IdTypeHandler}}")
	public List<Category> querySubCategorysById(@Param("parentId")String parentId);
	
	//根据父类id查询所有子类id
	@Select("select getChildLst(#{parentId,typeHandler=com.xumengba.handler.IdTypeHandler})")
	public String getSubCategoryIdsByParentId(@Param("parentId")String parentId);
	
	/**
	 * @Description: 查询文章的分页数据
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:25:18
	 * @param: sql 传入一个sql
	 * @return: List<Image> 返回文章分页数据
	 */
	@Select("${sql}")
	@ResultMap(value="categoryResultMap")
	public List<Category> selectPageData(@Param("sql")String sql);
	/**
	 * @Description: 统计数据条数
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:25:52
	 * @param: sql 传入一个sql
	 * @return: Long 返回数据条数
	 */
	@Select("${sql}")
	public Long selectTotalRecoreds(@Param("sql")String sql);
	
	/**
	 * 根据条件查询数据列表
	 * @param sql 传入一个sql
	 * @return 返回数据列表
	 */
	@Select("${sql}")
	@ResultMap(value="categoryResultMap")
	public List<Category> queryData(@Param("sql")String sql);
	/**
	 * @Description: 根据类别名称获取类别id
	 * @author: 朱俊亮
	 * @date: 2018年10月12日 上午9:44:22
	 * @param: categoryName 类别名称
	 * @return: String 类别id
	 */
	@Select("select id,category_name from t_category where category_name = #{categoryName}")
	@Results(id="categoryMap",value={
		@Result(property = "id", column = "id" ,typeHandler=com.xumengba.handler.IdTypeHandler.class),
		@Result(property = "categoryName", column = "category_name", jdbcType=JdbcType.VARCHAR),
	})
	public Category queryByName(@Param("categoryName")String categoryName);

}
