package com.litang.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litang.annotation.Table;
import com.litang.domain.BaseEntity;
import com.litang.exception.base.BizException;
import com.litang.mapper.BaseMapper;
import com.litang.page.PageView;
import com.litang.response.StatusCode;
import com.litang.service.BaseService;
import com.litang.utils.GenericsUtils;

/**
 * 业务层实现基类
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

	// 创建记录日志的对象
	Logger logger = LoggerFactory.getLogger(this.getClass());

	// 定义为抽象的,则必须要子类重写,此方法的功能是定义要查询的字段
	protected abstract String getFields();

	// 此方法之所以定义为抽象的,是必须要交给子类去重写
	protected abstract BaseMapper<T> getMapper();

	// 获取当前的实体
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	@Override
	public boolean insert(T t) {
		try {
			t.setDeleted(true);
			Date date = new Date();
			t.setCreatedTime(date);
			t.setUpdatedTime(date);
		} catch (NullPointerException e) {
			logger.info("需要保存的" + getEntityName() + "不能为空", e);
			throw new NullPointerException("需要保存的" + getEntityName() + "不能为空");
		}
		return getMapper().insert(t) == 1;
	}

	@Override
	public boolean deleteById(String id) {
		return getMapper().deleteById(id) == 1;
	}

	@Override
	public boolean deleteByIdReal(String id) {
		return getMapper().deleteByIdReal(id) == 1;
	}

	@Override
	public boolean editById(T t) {
		return getMapper().editById(t) == 1;
	}

	@Override
	public T queryById(String id) {
		return getMapper().queryById(id);
	}

	@Override
	public List<T> queryData(String wherSQL, List<Object> whereParams) {
		// 第一步、获取要查询的字段
		String queryFields = getFields();
		if (StringUtils.isBlank(queryFields)) {
			throw new BizException(StatusCode.QUERY_FIELDS_NOT_FOUND);
		}

		// 第二步、获取表名
		String tableName = getTableNameByEntity(this.entityClass);

		// 第三步、获取where条件
		String where = StringUtils.isBlank(wherSQL) ? " where 1=1 " : " where " + wherSQL;
		// 第四步、拼接sql语句
		String selectSQL = " SELECT " + queryFields + " from " + tableName + where;
		// 第五步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return getMapper().queryData(selectSQL);
	}

	@Override
	public List<T> queryData() {
		return this.queryData(null, null);
	}

	@Override
	public PageView<T> queryPageData(Long currentPageNum, Long pageSize, String wherSQL, List<Object> whereParams,
			LinkedHashMap<String, String> orderBy) {
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
		countSQL = setQueryParams(countSQL, whereParams, null); // select id
																// from t_car
																// where id = 1

		// 第七步、获取总页数
		Long totalRecord = getMapper().selectTotalRecoreds(countSQL);

		// 第八步、生成limit语句
		String limit = buildLimit(currentPageNum, pageSize);

		// 第九步、生成查询记录数sql语句
		String selectSQL = " SELECT " + queryFields + " from " + tableName + where + orderby + limit;

		// 第十步、设置pageView,主要是用来计算limit的数据
		PageView<T> pageView = new PageView<T>(totalRecord, currentPageNum, pageSize);

		// 第十一步、设置参数信息
		selectSQL = setQueryParams(selectSQL, whereParams, pageView);

		// 第十一步、查询数据
		List<T> datas = getMapper().selectPageData(selectSQL);

		pageView.setDatas(datas);

		return pageView;
	}

	// 查询全部数据
	public PageView<T> queryPageData() {
		return this.queryPageData(-1L, -1L, null, null, null);
	}

	/**
	 * @Description: 获取当前的实体的名称
	 * @Return: 返回当前的实体的名称
	 */
	private String getEntityName() {
		return this.entityClass.getSimpleName();
	}

	// 获取实体所对应的表名
	protected String getTableNameByEntity(Class<T> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if (table == null || "".equals(table.name().trim())) {
			throw new BizException(StatusCode.TABLE_ANNOTATION_NOT_FOUND);
		}
		return table.name();
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
	protected String setQueryParams(String sql, List<Object> queryParams, PageView<T> pageView) {

		List<Object> allParams = new ArrayList<Object>();
		if (queryParams != null && queryParams.size() > 0) {
			allParams.addAll(queryParams);
		}
		if (sql.contains("limit")) {
			allParams.add(pageView.getStartIndex());
			allParams.add(pageView.getPageSize());
		}
		sql = String.format(sql, allParams.toArray());
		return sql;
	}
}
