package com.xumengba.request;

import org.apache.commons.lang3.StringUtils;

import com.xumengba.annotation.Unique;
import com.xumengba.response.StatusCode;

@Unique(uniqueFields = { "category_name" }, tableName = "t_category")
public class CategoryForm extends BaseForm {

	// 分类名称
	
	private String id;
	
	private String categoryName;

	private String memo;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public StatusCode validate() {
		StatusCode statusCode = null;
		if(StringUtils.isBlank(categoryName)){
			statusCode = StatusCode.CATEGORY_NAME_NULL;
		}
		return statusCode;
	}

	
	@Override
	public Object[] validateUnique() {
		return new Object[]{categoryName};
	}
}
