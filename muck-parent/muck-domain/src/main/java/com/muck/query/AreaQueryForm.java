package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description: 查询区域数据的请求条件
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月19日 下午8:08:58
 */
public class AreaQueryForm extends BaseForm{

	/**区域编号*/
	private String areaCode;
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	@Override
	public StatusCode validate() {
		return null;
	}
	
}
