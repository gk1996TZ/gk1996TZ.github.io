package com.muck.response;

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
	
	
	// 操作的数据找不到
	NOT_FOUND(404,"要操作的资源不存在"),
	
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
	
	ORGANIZATION_BLANK(1004,"传入的组织结构xml为空"),
	
	ORGANIZATION_DEVICES_BLANK(1005,"传入的组织结构xml中没有可添加的设备"),
	
	ORGANIZATION_AREA_BLANK(1006,"传入的组织结构xml中没有可添加的区域"),
	
	ORGANIZATION_CHANNEL_BLANK(1007,"传入的组织结构xml中没有可添加的通道"),
	
	//----------------------- car 车辆信息校验 --------------------------
	
	CAR_CODE_BLANK(2000,"车牌号码为空"),
	
	CAR_AREA_ID_BLANK(2001,"车辆所属区域为空"),
	
	CAR_COMPANY_ID_BLANK(2002,"车辆所属企业为空"),
	
	CAR_PERSON_NAME_BLANK(2003,"车主联系方式为空"),
	
	CAR_PERSON_PHONE_BLANK(2004,"车主姓名为空"),
	
	CAR_ID_BLANK(2005,"车辆ID为空"),
	
	CAR_ALREADY_EXISTS(2006,"车辆已经存在并且信息未更新"),
	
	//-----------------------carGroup 车辆组信息校验 -----------------
	
	CAR_GROUP_NAME_EXISTS(2101,"车辆组名称已存在"),
	//------------------------- Area 区域	信息校验    -------------------

	AREA_NAME_BLANK(2500,"区域名称为空"),
	
	AREA_ID_BLANK(2501,"区域id为空"),
	
	AREA_HAS_SUB_AREA(2502,"此区域下面存在子区域,不能删除"),
	
	AREA_NAME_REPEAT(2599,"区域名称已经存在"),
	
	AREA_CODE_BLANK(2503,"区域编码Code为空"),
	
	//-------------------------- Company 企业信息校验 -----------------------------------
	
	COMPANY_NAME_BLANK(3000,"企业名称为空"),
	COMPANY_NAME_REPEAT(3002,"企业已经存在"),
	COMPANY_ID_BLANK(3001,"企业id为空"),
	
	
	//-------------------------- Manager 系统用户信息校验 -----------------------------------
	
	MANAGER_NAME_BLANK(3500,"系统用户名为空"),
	MANAGER_PASSWORD_BLANK(3501,"系统用户密码为空"),
	MANAGER_PHONE_BLANK(3502,"系统用户手机号为空"),
	MANAGER_MANAGERIDS_BLANK(3503,"您还没有选择任何用户"),
	MANAGER_PRIVILEGEGROUPIDS_BLANK(3504,"您还没有选择任何权限组"),
	MANAGER_LOGIN_PHONE_BLANK(3505,"登录账号为空"),
	MANAGER_LOGIN_PASSWORD_BLANK(3506,"登录密码为空"),
	MANAGER_LOGIN_ERROR(3507,"账号或密码错误,登录失败"),
	MANAGER_LOGIN_RESTART(3508,"请重新登陆"),
	MANAGER_PASSWRD_VERIFY_SUCCESS(3509,"密码校验成功"),
	MANAGER_PASSWRD_VERIFY_FAIL(3510,"密码校验失败"),
	MANAGER_PHONE_REPEAT(3511,"系统用户手机号已经存在"),
	//-------------------------- PrivilegeGroup 权限组信息校验 -----------------------------------
	
	PRIVILEGE_GROUP_NAME_BLANK(4000,"权限组名称为空"),
	PRIVILEGE_GROUP_HAS_EXIST_ADD(4001,"要添加的权限组已经存在"),
	PRIVILEGE_GROUP_HAS_EXIST_EDIT(4002,"要修改权限组已经存在,换个名字吧"),
	PRIVILEGE_GROUP_ID_BLANK(4003,"请选择权限组"),
	//--------------------------SystemSettings系统设置信息校验------------------------------
	SYSTEMSET_LOGO_FAILE(3600,"logo上传失败"),
	
	//------------------------- Privilege 权限信息校验-----------------------------
	PRIVILEGE_NAME_BLANK(4500,"权限名称为空"),
	
	PRIVILEGE_URL_BLANK(4501,"权限的url为空"),
	
	// -------------------Device校验---------------
	DEVICE_NAME_BLANK(9000,"设备名称为空"),
	DEVICE_REGISITER_ID_BLANK(9001,"设备注册id为空"),
	DEVICE_ID_BLANK(9002,"设备id为空"),
	
	
	// -------------------Site信息校验---------------
	SITE_NAME_BLANK(7000,"工地名称为空"),
	SITE_NAME_REPEAT(7001,"工地名称已经存在"),
	SITE_ID_BLANK(7002,"工地ID为空"),
	SITE_PROCESS_MEMO_BLANK(7003,"工地项目进度描述为空"),
	SITE_PROJECT_INFO_NAME_BLANK(7005,"项目名为空"),
	SITE_PROJECT_INFO_ID_BLANK(7004,"工地项目情况id为空"),
	SITE_PROJECT_NOT_BIND(7006,"该工地暂时没有添加项目"),
	SITE_PROJECT_BIND_ERROR(7007,"工地项目维护异常"),
	
	
	CHANNEL_CODE_BLANK(5000,"通道编码为空"),
	
	//-------------------- 工地组校验---------------
	SITE_GROUP_REPEAT(8000,"工地组已经存在"),
	SITE_GROUP_NAME_BLANK(8001,"工地组已经存在"),
	SITE_GROUP_ID_BLANK(8002,"工地组ID不存在"),
	
	//-------------------- 驾驶员信息---------------
	CAR_DRIVER_NAME_BLANK(10000,"车辆驾驶员名称为空"),
	CAR_DRIVER_ALREADY_EXISTS_AND_NOUPDATE(10001,"驾驶员已存在并且信息未更新"),
	//-------------------- 处置场信息---------------
	DISPOSAL_ID_BLANK(20000,"处置场id为空"),
	
	//-------------------- 停车场信息---------------
	CAR_PARK_NAME_REPEAT(30000,"该企业下的停车场已存在"),
	
	//-------------------- 多边形选框信息 --------------
	POINT_NULL(40001,"请传入坐标点"),
	POINT_SIZE_LESS_THREE(40002,"传入的坐标点小于三个"),
	POINT_FORMAT_ERROR(40003,"请传入正确的坐标点格式"),
	
	//------------------- 导出导入信息 ----------------
	EXPORT_FAILE(50001,"导出失败"),
	IMPORT_FAILE(50002,"导入失败"),
	EXPORT_TEMPLATE_NULL(50003,"服务器上导出模版不存在"),
	IMPORT_COMPANY_DATA_FAILE(50004,"请检查企业信息是否填写正确，并阅读注意事项"),
	//数据转换异常
	REVERT_FAILE(60001,"时间转换异常"),
	ELECTRIC_FENCE_EXIST(70001,"电子围栏名称已存在，请修改！"),
	ELECTRIC_FENCE_NAME_NULL(70002,"电子围栏名称为空，请检查"),
	ELECTRIC_FENCE_CAR_NOT_NULL(70001,"该电子围栏下有车辆无法删除"),
	
	// 导入企业数据包含企业下所有数据异常
	EXTRACT_ERROR(80001,"上传的压缩包解压失败"),
	EXTRACT_NO_FILE(80002,"上传的压缩包内不包含文件"),
	IMPORT_COMPANY_CARDRIVER_IS_NOT_EXISTS(80003,"导入企业时的驾驶员表格文件不存在"),
	IMPORT_COMPANY_CARDRIVER_NULL(80003,"导入企业时的驾驶员为空"),
	IMPORT_COMPANY_NULL(80003,"导入企业时企业数据为空"),
	IMPORT_COMPANY_NAME_NULL(80004,"导入企业时企业数据为空"),
	DONT_FILE(80004,"请以正确的文件请求上传文件"),
	EXTRACT_DONT_EXCEL_FILE(80006,"上传的不是一个压缩包文件"),
	// 导出企业数据异常
	EXPORT_COMPANY_ID_NULL(80005,"请选择需要导出的企业"),
	;
	private int code;
	
	private String message;
	
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
	
	//--------------------------------------------
	
	public static StatusCode setMemo(String tableName){
		
		switch (tableName) {
		case "t_area":
			return AREA_NAME_REPEAT;
		case "t_manager":
			return MANAGER_PHONE_REPEAT;
		case "t_site":
			return StatusCode.SITE_NAME_REPEAT;
		case "t_company":
			return StatusCode.COMPANY_NAME_REPEAT;
		case "t_car_park":
			return StatusCode.CAR_PARK_NAME_REPEAT;
		case "t_car_group":
			return StatusCode.CAR_GROUP_NAME_EXISTS;
		default:
			break;
		}
		return UNKNOWN;
	}
}
