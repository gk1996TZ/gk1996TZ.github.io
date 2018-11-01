package com.muck.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/***
 * 	车辆抓拍进出工地处置场实体
*/
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@Table(name="t_car_snapshot_in_out_image")
public class CarSnapshotInOutImage extends BaseEntity implements Comparable<CarSnapshotInOutImage>{

    /** 车牌号*/
    private String carCode;

    /**车牌颜色*/
    private String carCardColor;
    
    /** 设备id*/
    private String deviceId;

    /** 设备注册id*/
    private String deviceRegisterId;

    /** 车辆抓拍的时间,默认当前时间*/
    private Date snapshotTime;

    /** 抓拍的大图图片路径*/
    private String snapshotBigImagePath;
    /**抓拍的小图的图片路径*/
    private String snapshotSmailImagePath;
    /** 工地id*/
    private String siteId;

    /** 工地名称*/
    private String siteName;

    /** 处置场id*/
    private String disposalId;

    /** 处置场名称*/
    private String disposalName;

    /** 停车场id*/
    private String carParkId;

    /** 停车场名称*/
    private String carParkName;

    /** 区域编号*/
    private String areaId;

    /** 区域名称*/
    private String areaName;

    /** 企业id*/
    private String companyId;

    /** 企业名称*/
    private String companyName;
    
    /** 企业联系方式*/
    private String companyContact;

    /** 抓拍图片组id	*/
    private String snapshotImageGroupId;

    /** 抓拍图片组名称*/
    private String snapshotImageGroupName;

    /** 抓拍图片类别*/
    private String snapshotType;

    /** 抓拍记录说明信息*/
    private String memo;

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode == null ? null : carCode.trim();
    }

    public String getCarCardColor() {
		return carCardColor;
	}

	public void setCarCardColor(String carCardColor) {
		this.carCardColor = carCardColor;
	}

	public String getDeviceRegisterId() {
        return deviceRegisterId;
    }

    public void setDeviceRegisterId(String deviceRegisterId) {
        this.deviceRegisterId = deviceRegisterId == null ? null : deviceRegisterId.trim();
    }

    public Date getSnapshotTime() {
        return snapshotTime;
    }

    public void setSnapshotTime(Date snapshotTime) {
        this.snapshotTime = snapshotTime;
    }

    public String getSnapshotBigImagePath() {
		return snapshotBigImagePath;
	}

	public void setSnapshotBigImagePath(String snapshotBigImagePath) {
		this.snapshotBigImagePath = snapshotBigImagePath;
	}

	public String getSnapshotSmailImagePath() {
		return snapshotSmailImagePath;
	}

	public void setSnapshotSmailImagePath(String snapshotSmailImagePath) {
		this.snapshotSmailImagePath = snapshotSmailImagePath;
	}

	public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName == null ? null : siteName.trim();
    }


    public String getDisposalName() {
        return disposalName;
    }

    public void setDisposalName(String disposalName) {
        this.disposalName = disposalName == null ? null : disposalName.trim();
    }


    public String getCarParkName() {
        return carParkName;
    }

    public void setCarParkName(String carParkName) {
        this.carParkName = carParkName == null ? null : carParkName.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}

	public String getSnapshotImageGroupName() {
        return snapshotImageGroupName;
    }

    public void setSnapshotImageGroupName(String snapshotImageGroupName) {
        this.snapshotImageGroupName = snapshotImageGroupName == null ? null : snapshotImageGroupName.trim();
    }

    public String getSnapshotType() {
        return snapshotType;
    }

    public void setSnapshotType(String snapshotType) {
        this.snapshotType = snapshotType == null ? null : snapshotType.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	public String getCarParkId() {
		return carParkId;
	}

	public void setCarParkId(String carParkId) {
		this.carParkId = carParkId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSnapshotImageGroupId() {
		return snapshotImageGroupId;
	}

	public void setSnapshotImageGroupId(String snapshotImageGroupId) {
		this.snapshotImageGroupId = snapshotImageGroupId;
	}

	@Override
	public int hashCode() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String thisSnapShotTime = sdf.format(snapshotTime);
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carCode == null) ? 0 : carCode.hashCode());
		result = prime * result + thisSnapShotTime.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarSnapshotInOutImage other = (CarSnapshotInOutImage) obj;
		if (carCode == null) {
			if (other.carCode != null)
				return false;
		} else if (!carCode.equals(other.carCode))
			return false;
		if (snapshotTime == null) {
			if (other.snapshotTime != null)
				return false;
		} 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String thisSnapShotTime = sdf.format(snapshotTime);
		String otherSnapShotTime = sdf.format(other.snapshotTime);
		if(!thisSnapShotTime.equals(otherSnapShotTime)){
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(CarSnapshotInOutImage o) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String thisSnapShotTime = sdf.format(this.snapshotTime);
		String otherSnapShotTime = sdf.format(o.snapshotTime);
		
		return thisSnapShotTime.compareTo(otherSnapShotTime);
	}
}