package com.xumengba.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xumengba.annotation.Table;
import com.xumengba.domain.BaseEntity;
import com.xumengba.exception.BizException;
import com.xumengba.mapper.BaseMapper;
import com.xumengba.page.PageView;
import com.xumengba.response.StatusCode;
import com.xumengba.service.BaseService;
import com.xumengba.utils.GenericsUtils;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T>{

	// 定义为抽象的,则必须要子类重写,此方法的功能是定义要查询的字段
	protected abstract String getFields();
	// 此方法之所以定义为抽象的,是必须要交给子类去重写
	protected abstract BaseMapper<T> getMapper();
	// 获取实体
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
	
	@Override
	public void save(T t) {
		try {
			getMapper().insert(t);
		} catch (Exception e) {
			throw new BizException(StatusCode.INSERT_ENTITY_EXCEPTION);
		}
	}
	@Override
	public void saveAll(List<T> list) {
		try {
			List<BaseEntity> listObj = (List<BaseEntity>) list;
			getMapper().insertAll(listObj);
		} catch (Exception e) {
			throw new BizException(StatusCode.INSERT_ALL_EXCEPTION);
		}
	}
	@Override
	public void deleteByIdReal(String id){
		getMapper().deleteByIdReal(getTableNameByEntity(this.entityClass),id);
	}
	@Override
	public void deleteById(String id) {
		getMapper().deleteById(getTableNameByEntity(this.entityClass),id);
	}
	@Override
	public void editById(T t) {
		if(t == null){
			throw new BizException(StatusCode.ENTITY_BLANK);
		}
		try {
			getMapper().updateById(t);
		} catch (Exception e) {
			throw new BizException(StatusCode.UPDATE_ENTITY_EXCEPTION);
		}
	}
	protected String getTableNameByEntity(Class<T> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if (table == null || "".equals(table.name().trim())) {
			throw new BizException(StatusCode.TABLE_ANNOTATION_NOT_FOUND); 
		}
		return table.name();
	}
	@Override
	public T queryById(String id){
		if(id == null){
			throw new BizException(StatusCode.ID_BLANK);
		}
		T t = getMapper().queryById(id);
		if(t == null){
			throw new BizException(StatusCode.BASE_NOT_FOUND);
		}
		return t;
	}
	@Override
	public PageView<T> queryPageData(Long currentPageNum, Long pageSize, String wherSQL, List<Object> whereParams,
			LinkedHashMap<String, String> orderBy){
		// 第一步、获取要查询的字段
		String queryFields = getFields();
		if (StringUtils.isBlank(queryFields)) {
			throw new BizException(StatusCode.QUERY_FIELDS_NOT_FOUND);
		}
	
		// 第二步、获取表名
		String tableName = getTableNameByEntity(this.entityClass);
	
		// 第三步、获取where条件
		String where = StringUtils.isBlank(wherSQL) ? "" : " where " + wherSQL;
	
		// 第四步、获取orderby条件
		String orderby = buildOrderby(orderBy);
	
		// 第五步、生成查询总条数sql语句
		String countSQL = " SELECT COUNT(id) from " + tableName + where;
	
		// 第六步、设置countSQL并返回sql语句
		countSQL = setQueryParams(countSQL , whereParams,null); // select id from t_car where id = 1
		
		// 第七步、获取总页数
		Long totalRecord = getMapper().selectTotalRecoreds(countSQL);
	
		// 第八步、生成limit语句
		String limit = buildLimit(currentPageNum,pageSize);
	
		// 第九步、生成查询记录数sql语句
		String selectSQL = " SELECT " + queryFields + " from " + tableName + where + orderby + limit;
	
		// 第十步、设置pageView,主要是用来计算limit的数据
		PageView<T> pageView = new PageView<>(totalRecord, currentPageNum, pageSize);
		
		// 第十一步、设置参数信息
		selectSQL = setQueryParams(selectSQL , whereParams,pageView);
	
		// 第十一步、查询数据
		List<T> datas = getMapper().selectPageData(selectSQL);
	
		pageView.setDatas(datas);
	
		return pageView;
	}

	 // 查询全部数据
	 public PageView<T> queryPageData() {
		 return this.queryPageData(-1L, -1L, null, null, null);
	 }
	 @Override
	 public List<T> queryData(String wherSQL,List<Object> whereParams) {
		// 第一步、获取要查询的字段
		String queryFields = getFields();
		if (StringUtils.isBlank(queryFields)) {
			throw new BizException(StatusCode.QUERY_FIELDS_NOT_FOUND);
		}

		// 第二步、获取表名
		String tableName = getTableNameByEntity(this.entityClass);

		// 第三步、获取where条件
		String where = StringUtils.isBlank(wherSQL) ? "" : " where " + wherSQL;
		// 第四步、拼接sql语句
		String selectSQL = " SELECT " + queryFields + " from " + tableName + where ;
		// 第五步、设置参数
		selectSQL = setQueryParams(selectSQL , whereParams,null);
		return getMapper().queryData(selectSQL);
	 }
	 @Override
	 public List<T> queryData() {
		 return this.queryData(null,null);
	 }
	 
	// 组装order by语句
	protected String buildOrderby(LinkedHashMap<String, String> orderBy) {
		StringBuffer orderBySql = new StringBuffer("");
		if (orderBy != null && !orderBy.isEmpty()) {
			orderBySql.append(" order by ");
			for (String key : orderBy.keySet()) {
				orderBySql.append(key).append(" ").append(orderBy.get(key)).append(",");
			}
			orderBySql.deleteCharAt(orderBySql.length() - 1);
		}
		return orderBySql.toString();
	}
	
	// 组装limit语句
	protected String buildLimit(Long currentPageNum, Long pageSize) {
		StringBuilder sb = new StringBuilder("");
		if (currentPageNum != null && pageSize != null && currentPageNum != -1L && pageSize != -1L) {
			sb.append(" ").append("limit %d,%d").append(" ");
		}
		return sb.toString();
	}
	 
	// 设置参数信息并返回此sql
	protected String setQueryParams(String sql,List<Object> queryParams,PageView<T> pageView ){
		
		List<Object> allParams = new ArrayList<Object>();
		if(queryParams != null && queryParams.size() > 0){
			allParams.addAll(queryParams);
		}
		if(sql.contains("limit")){
			if(pageView != null){
				allParams.add(pageView.getStartIndex());
				allParams.add(pageView.getPageSize());
			}
		}
		sql = String.format(sql, allParams.toArray());
		return sql;
	}
}
