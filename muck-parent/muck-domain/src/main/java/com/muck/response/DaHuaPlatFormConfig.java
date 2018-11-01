package com.muck.response;

/**
* @Description: 大华登录平台配置信息
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月9日 上午10:37:17
*/
public class DaHuaPlatFormConfig {

	// 大华平台登录ip
	private String dahua_ip;	
	
	// 大华平台登录port
	private String dahua_port;	
	
	// 大华登录平台登录名
	private String	dahua_login_name;  
 	
	// 大华登录平台密码
	private String dahua_login_pwd;		

	public DaHuaPlatFormConfig(String dahua_ip, String dahua_port, String dahua_login_name, String dahua_login_pwd) {
		this.dahua_ip = dahua_ip;
		this.dahua_port = dahua_port;
		this.dahua_login_name = dahua_login_name;
		this.dahua_login_pwd = dahua_login_pwd;
	}

	public String getDahua_ip() {
		return dahua_ip;
	}

	public void setDahua_ip(String dahua_ip) {
		this.dahua_ip = dahua_ip;
	}

	public String getDahua_port() {
		return dahua_port;
	}

	public void setDahua_port(String dahua_port) {
		this.dahua_port = dahua_port;
	}

	public String getDahua_login_name() {
		return dahua_login_name;
	}

	public void setDahua_login_name(String dahua_login_name) {
		this.dahua_login_name = dahua_login_name;
	}

	public String getDahua_login_pwd() {
		return dahua_login_pwd;
	}

	public void setDahua_login_pwd(String dahua_login_pwd) {
		this.dahua_login_pwd = dahua_login_pwd;
	}

	@Override
	public String toString() {
		return "[dahua_ip=" + dahua_ip + ", dahua_port=" + dahua_port + ", dahua_login_name="
				+ dahua_login_name + ", dahua_login_pwd=" + dahua_login_pwd + "]";
	}
}
