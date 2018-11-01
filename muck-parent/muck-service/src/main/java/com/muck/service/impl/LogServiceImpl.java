package com.muck.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Log;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.LogMapper;
import com.muck.page.PageView;
import com.muck.service.LogService;
import com.muck.utils.DateUtils;
import com.muck.utils.LogUtils;

/**
 * @Description: 日志Service实现
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月28日 下午3:33:36
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {

	@Autowired
	private LogMapper logMapper;

	// ---------------------------------------------

	/***
	 * 保存日志
	 */
	public void save(Log log) {

		// 1、生成唯一主键
		UUIDHexGenerator uuid = new UUIDHexGenerator();
		String id = (String) uuid.generate(null, uuid);

		// 2、生成sql
		String sql = "insert into " + LogUtils.generateLogTableName(0)
				+ "(id,operator_type,operator_time,operator_params,operator_result,operator_result_msg,operator_account,"
				+ "memo,operator_model,deleted,operator_id,operator_name,created_time)" + " values('" + id + "','"
				+ log.getOperatorType() + "','" + DateUtils.formatDate(log.getOperatorTime(), null) + "','"
				+ log.getOperatorParams() + "'," + "'" + log.getOperatorResult() + "','" + log.getOperatorResultMsg()
				+ "'," + "'" + log.getOperatorAccount() + "','" + log.getMemo() + "','" + log.getOperatorModel() + "',"
				+ "" + log.isDeleted() + "," + log.getOperatorId() + ",'" + log.getOperatorName() + "','"
				+ DateUtils.formatDate(log.getCreatedTime(), null) + "')";

		// 3、保存
		logMapper.executeSQL(sql);
	}

	/**
	 * 通过表名日志表
	 */
	public void createLogTable(String tableName) {
		String sql = "create table if not exists " + tableName + " like t_log";
		logMapper.executeSQL(sql);
	}

	/***
	 * 查询最近指定月份数的日志
	 */
	public PageView<Log> queryNearestLogs(Long currentPageNum, Long pageSize, int n) {

		// 第一步：获取表名
		String tableName = LogUtils.generateLogTableName(0);

		// 第二步：查询出最近的日志表名称
		String sql = "select table_name from information_schema.tables " + "where table_schema='muck' "
				+ "and table_name like 't_log_%' " + "and table_name <= '" + tableName + "' "
				+ "order by table_name desc limit 0," + n;

		// 第三步、获取表名
		List<String> tableNames = logMapper.selectTableName(sql);

		// 第四步、查询最近若干月的日志
		String logSQL = "";
		String logName = null;

		for (int i = 0; tableNames != null && i < tableNames.size(); i++) {
			logName = tableNames.get(i);
			if (i == (tableNames.size() - 1)) {
				logSQL = logSQL + " select * from " + logName;
			} else {
				logSQL = logSQL + " select * from " + logName + " union ";
			}
		}

		// ---------------------------------

		// 1、生成查询总条数的sql
		String countSQL = "select count(tmp.id) FROM (" + logSQL + ") as tmp ";

		// 总记录数
		Long totalRecord = logMapper.selectTotalRecoreds(countSQL);

		// 判断有没有limit
		String limit = super.buildLimit(currentPageNum, pageSize);

		// 2、生成查询记录的sql
		String selectSQL = "select * FROM (" + logSQL + ") as tmp " + limit;

		PageView<Log> pageView = new PageView<>(totalRecord, currentPageNum, pageSize);
		selectSQL = super.setQueryParams(selectSQL, null, pageView);

		// 3、查询
		List<Log> logs = logMapper.selectPageData(selectSQL);
		pageView.setDatas(logs);

		return pageView;
	}
	@Override
	public PageView<Log> queryPageData(Long currentPageNum, Long pageSize, String wherSQL, List<Object> whereParams) {

		// 第一步：获取表名
		String tableName = LogUtils.generateLogTableName(0);

		// 第二步：查询出最近的日志表名称
		String sql = "select table_name from information_schema.tables " + "where table_schema='muck' "
				+ "and table_name like 't_log_%' " + "and table_name <= '" + tableName + "' "
				+ "order by table_name desc";

		// 第三步、获取表名
		List<String> tableNames = logMapper.selectTableName(sql);

		// 第四步、查询最近若干月的日志
		String logSQL = "";
		String logName = null;

		for (int i = 0; tableNames != null && i < tableNames.size(); i++) {
			logName = tableNames.get(i);
			if (i == (tableNames.size() - 1)) {
				logSQL = logSQL + " select * from " + logName;
			} else {
				logSQL = logSQL + " select * from " + logName + " union ";
			}
		}

		// ---------------------------------

		String where = StringUtils.isBlank(wherSQL) ? "" : " where " + wherSQL;
		
		// 1、生成查询总条数的sql
		String countSQL = "select count(tmp.id) FROM (" + logSQL + ") as tmp " + where;
		
		countSQL = super.setQueryParams(countSQL, whereParams, null);
		// 总记录数
		Long totalRecord = logMapper.selectTotalRecoreds(countSQL);

		// 判断有没有limit
		String limit = super.buildLimit(currentPageNum, pageSize);

		// 2、生成查询记录的sql
		String selectSQL = "select * FROM (" + logSQL + ") as tmp " + where + limit;

		PageView<Log> pageView = new PageView<>(totalRecord, currentPageNum, pageSize);
		selectSQL = super.setQueryParams(selectSQL, whereParams, pageView);

		// 3、查询
		List<Log> logs = logMapper.selectPageData(selectSQL);
		pageView.setDatas(logs);

		return pageView;
	}

	/***
	 * 查询所有的日志
	 */
	public List<Log> queryNearestLogs() {

		// 第一步：获取表名
		String tableName = LogUtils.generateLogTableName(0);

		// 第二步：查询出最近的日志表名称
		String sql = "select table_name from information_schema.tables " + "where table_schema='muck' "
				+ "and table_name like 't_log_%' " + "and table_name <= '" + tableName + "' "
				+ "order by table_name desc";

		// 第三步、获取表名
		List<String> tableNames = logMapper.selectTableName(sql);

		// 第四步、查询最近若干月的日志
		String logSQL = "";
		String logName = null;

		for (int i = 0; tableNames != null && i < tableNames.size(); i++) {
			logName = tableNames.get(i);
			if (i == (tableNames.size() - 1)) {
				logSQL = logSQL + " select * from " + logName;
			} else {
				logSQL = logSQL + " select * from " + logName + " union ";
			}
		}
		return logMapper.queryData(logSQL);
	}

	// ----------------------------------------------------------------------

	@Override
	protected BaseMapper<Log> getMapper() {
		return logMapper;
	}

	@Override
	protected String getFields() {
		return "id,operator_type,operator_time,operator_result,operator_result_msg,operator_params,"
				+ "operator_account,memo,operator_model";
	}
}
