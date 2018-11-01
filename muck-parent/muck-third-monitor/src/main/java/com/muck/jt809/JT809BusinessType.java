package com.muck.jt809;

public class JT809BusinessType {
	/**主链路车辆动态信息交换*/
	public static final int UP_EXG_MSG=0x1200;
	/**上传车辆注册信息*/
	public static final int UP_EXG_MSG_REGISTER=0x1201;
	/**实时上传车辆定位信息*/
	public static final short UP_EXG_MSG_REAL_LOCATION=0x1202;
	/**车辆定位信息自动补报*/
	public static final int UP_EXG_MSG_HISTORY_LOCATION=0x1203;
	/**主链路登录请求消息*/
	public static final int UP_CONNECT_REQ=0x1001;
	/**主链路登录请求消息长度*/
	public static final int UP_CONNECT_REQ_LENGTH=72;
	/**主链路登录应答消息*/
	public static final int UP_CONNECT_RSP=0x1002;
	/**主链路登录应答消息长度*/
	public static final int UP_CONNECT_RSP_LENGTH=31;
	/**主链路注销请求请求*/
	public static final int UP_DISCONNECT_REQ=0x1003;
	/**主链路注销应答请求*/
	public static final int UP_DISCONNECT_RSP=0x1004;
	/**主链路连接保持请求消息*/
	public static final int UP_LINKTEST_REQ=0x1005;
	/**主链路连接保持应答消息*/
	public static final int UP_LINKTEST_RSP=0x1006;
	/**主链路连接保持应答消息长度*/
	public static final int UP_LINKTEST_RSP_LENGTH=26;
	
	
	
	
}
