package com.muck.jt809;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * 连接长宝推送的资源信息
 * */
@Component
public class ChangBaoPro {

	/**用户的登录id*/
	public  int USERID;
	/**登录密码*/
	public static String PASSWORD;
	/**ip地址*/
	public static String DOWN_LINK_IP;
	/**端口号*/
	public static int DOWN_LINK_PORT;
	/**平台接入码*/
	public static int MSG_GNSSCENTERID;
	public int getUSERID() {
		return USERID;
	}
	@Value("${CHAOBAO.USERID}")
	public void setUSERID(int uSERID) {
		USERID = uSERID;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	@Value("${CHAOBAO.PASSWORD}")
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getDOWN_LINK_IP() {
		return DOWN_LINK_IP;
	}
	@Value("${CHAOBAO.DOWN_LINK_IP}")
	public void setDOWN_LINK_IP(String dOWN_LINK_IP) {
		DOWN_LINK_IP = dOWN_LINK_IP;
	}
	public int getDOWN_LINK_PORT() {
		return DOWN_LINK_PORT;
	}
	@Value("${CHAOBAO.DOWN_LINK_PORT}")
	public void setDOWN_LINK_PORT(int dOWN_LINK_PORT) {
		DOWN_LINK_PORT = dOWN_LINK_PORT;
	}
	public int getMSG_GNSSCENTERID() {
		return MSG_GNSSCENTERID;
	}
	@Value("${CHAOBAO.MSG_GNSSCENTERID}")
	public void setMSG_GNSSCENTERID(int mSG_GNSSCENTERID) {
		MSG_GNSSCENTERID = mSG_GNSSCENTERID;
	}
	
	
	
}
