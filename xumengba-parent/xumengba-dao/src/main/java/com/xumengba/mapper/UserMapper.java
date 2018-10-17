package com.xumengba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.xumengba.domain.User;
@Mapper
public interface UserMapper extends BaseMapper<User>{

	/**
	 * 根据id获取用户
	 * @param id 传入一个id
	 * @return 返回用户
	 */
	@Select("select * from t_user where id = #{id,typeHandler=com.xumengba.handler.IdTypeHandler}")
	@Results(id="userResultMap",value={
		@Result(property = "id", column = "id" ,typeHandler=com.xumengba.handler.IdTypeHandler.class),
		@Result(property = "userName", column = "user_name", jdbcType=JdbcType.VARCHAR),
		@Result(property = "userRealName", column = "user_real_name",jdbcType=JdbcType.VARCHAR),
		@Result(property = "userNickName", column = "user_nick_name",jdbcType=JdbcType.VARCHAR),
		@Result(property = "userPwd", column = "user_pwd",jdbcType=JdbcType.VARCHAR),
		@Result(property = "userPhone", column = "user_phone",jdbcType=JdbcType.VARCHAR),
		@Result(property = "userMail", column = "user_mail",jdbcType=JdbcType.VARCHAR),
		@Result(property = "createdTime", column = "created_time",jdbcType=JdbcType.TIMESTAMP),
		@Result(property = "updatedTime", column = "updated_time",jdbcType=JdbcType.TIMESTAMP),
		@Result(property = "deleted", column = "deleted",jdbcType=JdbcType.TINYINT),
	})
	public User queryById(String id);
	/**
	 * 查询分页数据
	 * */
	@Select("${sql}")
	@ResultMap(value="userResultMap")
	public List<User> selectPageData(@Param("sql")String sql);
	/**
	 * 查询数据条数
	 * */
	@Select("${sql}")
	public Long selectTotalRecoreds(@Param("sql")String sql);
	/**
	 * 根据条件查询数据列表
	 * @param sql 传入一个sql
	 * @return 返回数据列表
	 */
	@Select("${sql}")
	@ResultMap(value="userResultMap")
	public List<User> queryData(@Param("sql")String sql);
	
	/**
	 * 根据用户名查询用户
	 * */
	@Select("select id,user_name,user_real_name,user_nick_name,user_pwd,user_phone,user_mail from t_user where user_name=#{userName,jdbcType=VARCHAR} and deleted=1")
	@ResultMap(value="userResultMap")
	public User getByUserName(@Param("userName")String userName);
	
	/**
	 * 根据用户名密码查询用户
	 * */
	@Select("select id,user_name,user_real_name,user_nick_name,user_pwd,user_phone,user_mail from t_user where user_name=#{userName,jdbcType=VARCHAR} and user_pwd=#{password,jdbcType=VARCHAR} and deleted=1")
	@ResultMap(value="userResultMap")
	public User login(@Param("userName")String userName,@Param("password")String password);
	
	//
	@Select("select id,user_name,user_real_name,user_nick_name,user_pwd,user_phone,user_mail from t_user where user_mail=#{userMail,jdbcType=VARCHAR} and deleted=1")
	@ResultMap(value="userResultMap")
	public User getUserByMail(@Param("userMail")String userMail);
	
	@Select("select id,user_name,user_real_name,user_nick_name,user_pwd,user_phone,user_mail from t_user where user_phone=#{userPhone,jdbcType=VARCHAR} and deleted=1")
	@ResultMap(value="userResultMap")
	public User getUserByPhone(@Param("userPhone")String userPhone);
}
