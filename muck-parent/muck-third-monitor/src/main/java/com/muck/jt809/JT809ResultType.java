package com.muck.jt809;

public class JT809ResultType {
	/**连接成功*/
	public static  final byte CONNECTION_SUCCESS=0x00;
	/**连接IP地址不正确*/
	public static  final byte CONNECTION_IP_INCORRECT=0x01;
	/**连接接入码不正确*/
	public static  final byte CONNECTION_GNSS_INCORRECT=0x02;
	/**用户没有注册*/
	public static  final byte CONNECTION_USERID_NOT_REGISTER=0x03;
	/**密码错误*/
	public static  final byte CONNECTION_PASSWORD_INCORRECT=0x04;
	/**资源紧张稍后再连接*/
	public static  final byte CONNECTION_RESOURCE_PINCH=0x05;
	/**其它情况*/
	public static  final byte CONNECTION_OTHER_REASON=0x06;
}
