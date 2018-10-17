package com.xumengba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import com.xumengba.domain.Company;
import com.xumengba.mapper.provider.UpdateMapperProvider;

public interface CompanyMapper extends BaseMapper<Company> {

	/**
	 * 根据id获取企业
	 * @param id 传入一个id
	 * @return 返回用户
	 */
	@Select("select * from t_company where id = #{id,typeHandler=com.xumengba.handler.IdTypeHandler}")
	@Results(id="companyResultMap",value={
		@Result(property = "id", column = "id" ,typeHandler=com.xumengba.handler.IdTypeHandler.class),
		@Result(property = "companyName", column = "company_name", jdbcType=JdbcType.VARCHAR),
		@Result(property = "companyMail", column = "company_mail",jdbcType=JdbcType.VARCHAR),
		@Result(property = "companyLegalPerson", column = "company_legal_person",jdbcType=JdbcType.VARCHAR),
		@Result(property = "companyPhone", column = "company_phone",jdbcType=JdbcType.VARCHAR),
		@Result(property = "companyLogo", column = "company_logo",jdbcType=JdbcType.VARCHAR),
		@Result(property = "companyWxOfficial", column = "company_wx_official",jdbcType=JdbcType.VARCHAR),
		@Result(property = "companyAddress", column = "company_address",jdbcType=JdbcType.VARCHAR),
		@Result(property = "memo", column = "memo",jdbcType=JdbcType.VARCHAR),
		@Result(property = "createdTime", column = "created_time",jdbcType=JdbcType.TIMESTAMP),
		@Result(property = "updatedTime", column = "updated_time",jdbcType=JdbcType.TIMESTAMP),
		@Result(property = "deleted", column = "deleted",jdbcType=JdbcType.TINYINT),
	})
	public Company queryById(String id);
	
	/**
	 * @Description:更新企业
	 * @author:甘坤
	 * @date: 2018年10月10日 下午4:21:34
	 * @param: @param company
	 * @return: void
	 */
	@UpdateProvider(type=UpdateMapperProvider.class,method="update")
	public void updateCompany(Company company);
	
	@Select("select id,company_name,company_mail,company_legal_person,company_phone,company_logo,company_wx_official,company_address,memo,created_time,updated_time from t_company where deleted=1")
	@ResultMap("companyResultMap")
	public List<Company> getCompany();
}
