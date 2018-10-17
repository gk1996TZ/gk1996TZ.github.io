package com.xumengba.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xumengba.handler.IdTypeHandler;

/***
 * @Description: 用来拼接查询where语句
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月24日 上午11:11:09
 */
public final class QueryHelper {

	private StringBuilder whereSQL = new StringBuilder("");

	private List<Object> whereParams = new ArrayList<Object>();

	/**
	 * @Description: 该函数的功能描述
	 * @param: queryFiledValue:要查询的字段值.比如说要查询area_id
	 *             = 'b8qp',那么queryFiledValue的值 就b8qp,对应的areaId field : 要查询的字段
	 *             isEncode : 是否需要编码,一般情况下是不需要的,但是对于涉及到id的查询是需要编码的
	 * @author: 展昭
	 * @date: 2018年4月24日 上午11:11:29
	 */
	public QueryHelper addCondition(Object queryFiledValue, String field, boolean isEncode) {

		if (queryFiledValue != null) {
			if (StringUtils.isNotBlank(queryFiledValue.toString())) {
				if (whereParams.size() > 0) {
					this.whereSQL = this.whereSQL.append(" ").append("and").append(" ");
				}
				this.whereSQL = this.whereSQL.append(" ").append(field).append(" ");

				// 如果为true则表示需要解密,如果为false,则不需要解密
				if (isEncode) {
					this.whereParams.add(IdTypeHandler.decode(queryFiledValue.toString()));
				} else {
					this.whereParams.add(queryFiledValue);
				}
			}
		}
		return this;
	}

	public String getWhereSQL() {
		return whereSQL.toString();
	}

	public List<Object> getWhereParams() {
		return whereParams;
	}
}
