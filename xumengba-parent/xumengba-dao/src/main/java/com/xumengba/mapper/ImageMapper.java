package com.xumengba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.xumengba.domain.Image;

@Mapper
public interface ImageMapper extends BaseMapper<Image>{

	/**
	 * @Description: 根据id查询图片
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午2:08:45
	 * @param: id 传入一个id
	 * @return: Image 返回查询到的图片
	 */
	@Select("select * from t_image where id = #{id,typeHandler=com.xumengba.handler.IdTypeHandler}")
	@Results(id = "imageResultMap",value={
		@Result(property="id",column="id",typeHandler=com.xumengba.handler.IdTypeHandler.class),
		@Result(property="imageTitle",column="image_title",jdbcType=JdbcType.VARCHAR),
		@Result(property="imageUrl",column="image_url",jdbcType=JdbcType.VARCHAR),
		@Result(property="categoryId",column="category_id",typeHandler=com.xumengba.handler.IdTypeHandler.class),
		@Result(property="imageSeq",column="image_seq",jdbcType=JdbcType.TINYINT),
		@Result(property="memo",column="memo",jdbcType=JdbcType.VARCHAR),
		@Result(property="createdTime",column="created_time",jdbcType=JdbcType.TIMESTAMP),
		@Result(property="updatedTime",column="updated_time",jdbcType=JdbcType.TIMESTAMP),
		@Result(property="deleted",column="deleted",jdbcType=JdbcType.TINYINT)
	})
	public Image queryById(String id);
	/**
	 * @Description: 根据图片排序序号获取图片的id
	 * @author:朱俊亮
	 * @date: 2018年10月10日 下午2:08:52
	 * @param: imageSeq 传入图片的排序序号
	 * @return: Long 返回图片id
	 */
	@Select("select id from t_image where image_seq = #{imageSeq}")
	public Long getIdBySeq(@Param("imageSeq")Integer imageSeq);
	/**
	 * @Description: 修改图片排序序号
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午2:09:20
	 * @param: id 传入一个图片id
	 * @param: imageSeq 传入图片排序序号
	 * @return: Integer 返回受影响行数
	 */
	@Update("update t_image set image_seq = #{imageSeq} where id = #{id}")
	public Integer changeSeqById(@Param("id")Long id,@Param("imageSeq")Integer imageSeq);
	/**
	 * @Description: 查询文章的分页数据
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:25:18
	 * @param: sql 传入一个sql
	 * @return: List<Image> 返回文章分页数据
	 */
	@Select("${sql}")
	@Results(id = "imageResultPageMap",value={
		@Result(property="id",column="id",typeHandler=com.xumengba.handler.IdTypeHandler.class),
		@Result(property="imageTitle",column="image_title",jdbcType=JdbcType.VARCHAR),
		@Result(property="imageUrl",column="image_url",jdbcType=JdbcType.VARCHAR),
		@Result(property="categoryId",column="category_id",typeHandler=com.xumengba.handler.IdTypeHandler.class),
		@Result(property="categoryName",column="category_name",jdbcType=JdbcType.VARCHAR),
		@Result(property="imageSeq",column="image_seq",jdbcType=JdbcType.TINYINT),
		@Result(property="memo",column="memo",jdbcType=JdbcType.VARCHAR),
		@Result(property="createdTime",column="created_time",jdbcType=JdbcType.TIMESTAMP),
		@Result(property="updatedTime",column="updated_time",jdbcType=JdbcType.TIMESTAMP),
		@Result(property="deleted",column="deleted",jdbcType=JdbcType.TINYINT)
	})
	public List<Image> selectPageData(@Param("sql")String sql);
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
	 * @Description: 查询文章的列表数据不带分页
	 * @author: 朱俊亮
	 * @date: 2018年10月12日 下午11:31:18
	 * @param: sql 传入一个sql
	 * @return: List<Image> 返回文章列表数据
	 */
	@Select("${sql}")
	@ResultMap(value="imageResultPageMap")
	public List<Image> queryData(@Param("sql")String sql);
}
