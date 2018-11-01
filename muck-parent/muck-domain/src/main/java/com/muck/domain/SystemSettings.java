package com.muck.domain;

/**
* @Description: 系统设置
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月25日 上午10:46:03
 */
public class SystemSettings extends BaseEntity{

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
    
    // 视频间隔时间,单位为秒,默认是60秒,也就是1分钟
	private Integer sysVideoRotationTime = 60;
	
	// 设备异常检测时间,单位为秒,默认是1800秒,也就是30分钟
	private Integer sysDeviceAnomalyDetectionTime = 1800;

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

	public Integer getSysVideoRotationTime() {
		return sysVideoRotationTime;
	}

	public void setSysVideoRotationTime(Integer sysVideoRotationTime) {
		this.sysVideoRotationTime = sysVideoRotationTime;
	}

	public Integer getSysDeviceAnomalyDetectionTime() {
		return sysDeviceAnomalyDetectionTime;
	}

	public void setSysDeviceAnomalyDetectionTime(Integer sysDeviceAnomalyDetectionTime) {
		this.sysDeviceAnomalyDetectionTime = sysDeviceAnomalyDetectionTime;
	}

	@Override
	public String toString() {
		return "[sysCompanyLogo=" + sysCompanyLogo + ", sysIp=" + sysIp + ", sysPort=" + sysPort
				+ ", sysVideoRotationTime=" + sysVideoRotationTime + ", sysDeviceAnomalyDetectionTime="
				+ sysDeviceAnomalyDetectionTime + "]";
	}
}