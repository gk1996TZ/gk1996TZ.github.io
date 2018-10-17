package com.xumengba.response;

/**
* @Description: 系统各类状态码
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月13日 下午12:34:57
 */
public enum StatusCode {
	
	//-----------------公共操作状态码及错误提示--------------------------------
	
	// 成功
	SUCESS(200, "操作成功"), 

	// 服务器异常
	INTERNAL_ERROR(500, "服务器异常,请稍后再试..."),
	
	// 操作的资源找不到
	NOT_FOUND(404,"业务异常：要操作的资源不存在"),
	
	// 操作的数据不存在
	BASE_NOT_FOUND(404,"业务异常：要操作的数据不存在"),

	// 参数异常
	PARAMETER_ILLEGAL(-2, "请求参数异常..."),
	
	MANAGER_TOKEN_VALIDATE(-3, "用户信息已失效,请重新登录"),
	SESSION_VALIDATE(-4, "session已失效,请重新登录"),
	
	// 未知错误
	UNKNOWN(-1, "系统繁忙，请重试..."),
	
	ID_BLANK(1000 , "要执行此操作,id的值不为空"),
	
	UPLOAD_FILE_CONTENT_BLANK(1001 , "上传的文件内容为空"),
	
	TABLE_ANNOTATION_NOT_FOUND(1002 , "domain实体上的table注解不存在,请添加..."),
	
	QUERY_FIELDS_NOT_FOUND(1003 , "select语句中没有查询的字段"),
	
	ENTITY_BLANK(1004,"要执行此操作，传入的对象不能为空"),
	
	INSERT_ENTITY_EXCEPTION(1005,"添加数据对象时出现异常"),
	
	INSERT_ALL_EXCEPTION(1006,"添加数据列表时出现异常"),
	
	UPDATE_ENTITY_EXCEPTION(1007,"根据id修改数据时出现异常"),
	
	USER_LOGIN_ERROR(1008,"用户名或密码错误"),
	
	CHANGE_IMAGE_SEQ_BLANK(1009,"修改图片的排序顺序时传入的序号为空"),
	CATEGORY_NAME_REPEAT(1010,"分类名称重复"),
	CATEGORY_NAME_NULL(1011,"分类名称为空"),
	CATEGORY_EXIST_SUB(1012,"该分类有子分类无法删除"),
	CATEGORY_USED(1013,"该分类正在使用无法删除"),
	USER_NAME_NULL(1014,"用户名为空"),
	OLD_CODE_IS_NULL(1015,"旧密码为空"),
	USER_NEW_PWD_NULL(1016,"新密码为空"),
	USER_PWD_SAME(1017,"新旧密码一致"),
	PASSWORD_IS_NOT_TRUE(1018,"密码错误"),
	USER_CODE_IS_NULL(1019,"密码为空"),
	USER_MAIL_ERROR(1120,"邮箱格式输入有误,请检查"),
	USER_PHONE_ERROR(1121,"手机号格式输入有误,请检查"),
	USER_MAIL_EXIST(1122,"该邮箱已经注册，请换一个输入"),
	USER_PHONE_EXIST(1123,"该手机号码已经注册，请换一个手机号码"),
	USER_NAME_ERROR(1124,"用户名输入有误:由字母、数字、汉字、下划线、逗号组成且第一位必须是由字母或者汉字开头，长度为2~18位"),
	USER_PWD_ERROR(1125,"新密码格式错误请检查"),
	USER_NAME_EXIST(1126,"用户名已注册请更换"),
	
	;
	private int code;
	
	private String message;
	
public static StatusCode setMemo(String tableName){
		
		switch (tableName) {
		case "t_category":
			return CATEGORY_NAME_REPEAT;
		default:
			break;
		}
		return UNKNOWN;
	}
	
	private StatusCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
