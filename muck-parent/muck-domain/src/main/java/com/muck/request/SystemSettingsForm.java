package com.muck.request;

import com.muck.response.StatusCode;

public class SystemSettingsForm extends BaseForm{

	//企业id
	private String id;
	
	// 企业logo
    private String sysCompanyLogo;

    // ip地址
    private String sysIp;

    // 端口号
    private Integer sysPort;

    // 版权信息
    private String sysCopyright;

    // 其他额外说明
    private String memo;
	@Override
	public StatusCode validate() {
		return null;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSysCompanyLogo() {
		return sysCompanyLogo;
	}
	public void setSysCompanyLogo(String sysCompanyLogo) {
		this.sysCompanyLogo = sysCompanyLogo;
	}
	public String getSysIp() {
		return sysIp;
	}
	public void setSysIp(String sysIp) {
		this.sysIp = sysIp;
	}
	public Integer getSysPort() {
		return sysPort;
	}
	public void setSysPort(Integer sysPort) {
		this.sysPort = sysPort;
	}
	public String getSysCopyright() {
		return sysCopyright;
	}
	public void setSysCopyright(String sysCopyright) {
		this.sysCopyright = sysCopyright;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
