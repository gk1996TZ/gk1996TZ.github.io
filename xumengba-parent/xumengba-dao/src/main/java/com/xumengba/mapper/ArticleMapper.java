package com.xumengba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.xumengba.domain.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article>{

	/**
	 * @Description: 根据id查询文章
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:23:45
	 * @param: id 传入一个id
	 * @return: Article 返回查询到的文章
	 */
	@Select("select * from t_article where id = #{id,typeHandler=com.xumengba.handler.IdTypeHandler}")
	@Results(id="articleResultMap",value={
			@Result(property="id",column="id",typeHandler=com.xumengba.handler.IdTypeHandler.class),
			@Result(property="articleTitle",column="article_title",jdbcType=JdbcType.VARCHAR),
			@Result(property="articleContent",column="article_content",jdbcType=JdbcType.VARCHAR),
			@Result(property="articleAuthor",column="article_author",jdbcType=JdbcType.VARCHAR),
			@Result(property="articlePic",column="article_pic",jdbcType=JdbcType.VARCHAR),
			@Result(property="articleSeq",column="article_seq",jdbcType=JdbcType.TINYINT),
			@Result(property="categoryId",column="category_id",typeHandler=com.xumengba.handler.IdTypeHandler.class),
			@Result(property="memo",column="memo",jdbcType=JdbcType.VARCHAR),
			@Result(property="createdTime",column="created_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property="updatedTime",column="updated_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property="deleted",column="deleted",jdbcType=JdbcType.TINYINT)
	})
	public Article queryById(String id);
	
	/**
	 * @Description: 查询文章的分页数据
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:25:18
	 * @param: sql 传入一个sql
	 * @return: List<Article> 返回文章分页数据
	 */
	@Select("${sql}")
	@Results(id="articleResultPageMap",value={
			@Result(property="id",column="id",typeHandler=com.xumengba.handler.IdTypeHandler.class),
			@Result(property="articleTitle",column="article_title",jdbcType=JdbcType.VARCHAR),
			@Result(property="articleContent",column="article_content",jdbcType=JdbcType.VARCHAR),
			@Result(property="articleAuthor",column="article_author",jdbcType=JdbcType.VARCHAR),
			@Result(property="articlePic",column="article_pic",jdbcType=JdbcType.VARCHAR),
			@Result(property="articleSeq",column="article_seq",jdbcType=JdbcType.TINYINT),
			@Result(property="categoryId",column="category_id",typeHandler=com.xumengba.handler.IdTypeHandler.class),
			@Result(property="categoryName",column="category_name",jdbcType=JdbcType.VARCHAR),
			@Result(property="memo",column="memo",jdbcType=JdbcType.VARCHAR),
			@Result(property="createdTime",column="created_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property="updatedTime",column="updated_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property="deleted",column="deleted",jdbcType=JdbcType.TINYINT)
	})
	public List<Article> selectPageData(@Param("sql")String sql);
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
	 * @Description: 根据条件查询文章数据列表
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:22:26
	 * @param: sql 传入一个sql
	 * @return: List<Article> 返回数据列表
	 */
	@Select("${sql}")
	@ResultMap(value="articleResultPageMap")
	public List<Article> queryData(@Param("sql")String sql);
	/**
	 * @Description: 根据id获取上一篇文章
	 * @author: 朱俊亮
	 * @date: 2018年10月12日 下午3:08:15
	 * @param: id 传入一个id
	 * @param: categoryId 传入一个类别id
	 * @return: Article 返回一个文章对象
	 */
	@Select("select * from t_article where id = (select max(id) from t_article where id < #{id,typeHandler=com.xumengba.handler.IdTypeHandler} and category_id = #{categoryId,typeHandler=com.xumengba.handler.IdTypeHandler})")
	@ResultMap(value="articleResultMap")
	public Article queryAritcleUpById(@Param("id")String id,@Param("categoryId")String categoryId);
	/**
	 * @Description: 根据id获取下一篇文章
	 * @author: 朱俊亮
	 * @date: 2018年10月12日 下午3:09:03
	 * @param: id 传入一个id
	 * @param: categoryId 传入一个类别id
	 * @return: Article 返回一个文章对象
	 */
	@Select("select * from t_article where id = (select min(id) from t_article where id > #{id,typeHandler=com.xumengba.handler.IdTypeHandler} and category_id = #{categoryId,typeHandler=com.xumengba.handler.IdTypeHandler})")
	@ResultMap(value="articleResultMap")
	public Article queryAritcleDownById(@Param("id")String id,@Param("categoryId")String categoryId);
	
	
	@Select("select * from t_article where category_id = #{categoryId,typeHandler=com.xumengba.handler.IdTypeHandler}")
	@ResultMap(value="articleResultMap")
	public List<Article> getArticlesByCategoryId(@Param("categoryId")String categoryId);
}
