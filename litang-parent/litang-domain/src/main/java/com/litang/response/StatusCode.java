package com.litang.response;

public enum StatusCode {

	SUCCESS(200,"操作成功"),
	NOT_FOUND(404,"要操作的资源不存在"),
	ERROR(500,"服务器异常，请重新登陆"),
	// 参数异常
	PARAMETER_ILLEGAL(-2, "请求参数异常..."),
	// 未知错误
	UNKNOWN(-1, "系统繁忙，请重试..."),
	
	LOGIN_RESTART(30001,"请重新登陆"),
	NAME_OR_PWD_IS_NULL(30002,"用户名或密码为空"),
	USER_NOT_EXISTS(30003,"用户不存在"),
	PASSWORD_IS_NOT_TRUE(30004,"请输入正确的用户密码"),
	CODE_IS_NULL(30005,"请输入验证码"),
	TOKEN_IS_NULL(30006,"登录时所需TOKEN为空"),
	TOKEN_IS_NOT_TRUE(30007,"登录时TOKEN不正确"),
	NAME_EXIST(30008,"用户名已存在"),
	USER_ID_NULL(30009,"用户id为空"),
	USER_REVIEW_FAIL(30011,"该用户被拒绝"),
	USER_STATE_ERROR(30012,"用户状态输入错误"),
	USER_STATE_NULL(30013,"用户状态为空"),
	USER_NAME_NULL(30014,"用户名为空"),
	USER_OLD_PWD_NULL(30015,"用户密码为空"),
	USER_NEW_PWD_NULL(30016,"用户新密码为空"),
	USER_PWD_SAME(30017,"新旧密码一致请重输"),
	USER_DISABLE(30018,"该用户正在禁用中"),
	USER_ENABLE(30019,"该用户正在启用中"),
	QUERY_FIELDS_NOT_FOUND(1003 , "select语句中没有查询的字段"),
	TABLE_ANNOTATION_NOT_FOUND(1002 , "domain实体上的table注解不存在,请添加..."),
	POST_MESSAGE_ID_NULL(40001,"发布信息id为空"),
	QUERY_POST_MESSAGE_CONTENT_NULL(40002,"查询内容为空"),
	POST_MESSAGE_CONTENT_NULL(40003,"发布的消息内容为空"),
	POST_MESSAGE_IMAGE_NULL(40004,"发布的消息图片为空"),
	ROLE_NAME_NULL(60001,"角色名为空"),
	AUTHORITY_ROLE_NULL(70001,"给角色赋予的权限为空"),
	ROLE_USED_NOW(70002,"该角色正在被使用无法删除"),
	ROLE_NOT_EXIST(70003,"该角色并不存在"),
	JUMP_LOGIN(50000,"跳过登陆"),
	
	
	//更新客户端的信息
	CURRENT_VERSION_NULL(60000,"当前客户端版本号为空"),
	
	;
	
	
	private Integer code;
	private String message;
	
	private StatusCode(Integer code,String message){
		this.code = code;
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
