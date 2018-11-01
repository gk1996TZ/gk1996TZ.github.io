package com.muck.domain;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 * @Description: 告警/预警 
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月4日 上午10:01:50
 */
@Table(name = "t_warnings")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Warning extends BaseEntity {

	// 告警名称
	private String warningName;

	// 设备id
	private String deviceId;

	// 设备编号
	private String deviceCode;

	//设备名称
	private String deviceName;
	
	// 处置场id
	private String disposalId;

	// 工地id
	private String siteId;

	// 停车场id
	private String carParkId;
	
	// 车牌号
	private String carCode;

	// 设备信息
	private Device device;

	// 告警类型
	private Integer warningType;

	// 告警时间
	private Date warningTime;

	// 告警内容
	private String warningContent;

	// 告警地址
	private String warningAddress;
	
	// 告警状态
	private Integer warningStatus;

	// 处理时间
	private Date handleTime;
	
	// 是否处理(默认是未处理)
	private Boolean isHandle = false;

	// 告警说明描述
	private String memo;

	// 工地详情
	private Site site;

	// 处置场详情
	private Disposal disposal;

	// 停车场
	private CarPark carPark;
	
	// 车辆详情
	private Car car;

	// 企业详情
	private Company company;

	public String getWarningName() {
		return warningName;
	}

	public void setWarningName(String warningName) {
		this.warningName = warningName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getWarningAddress() {
		return warningAddress;
	}

	public void setWarningAddress(String warningAddress) {
		this.warningAddress = warningAddress;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public CarPark getCarPark() {
		return carPark;
	}

	public void setCarPark(CarPark carPark) {
		this.carPark = carPark;
	}

	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	public String getCarParkId() {
		return carParkId;
	}

	public void setCarParkId(String carParkId) {
		this.carParkId = carParkId;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Integer getWarningType() {
		return warningType;
	}

	public void setWarningType(Integer warningType) {
		this.warningType = warningType;
	}

	public Date getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(Date warningTime) {
		this.warningTime = warningTime;
	}

	public String getWarningContent() {
		return warningContent;
	}

	public void setWarningContent(String warningContent) {
		this.warningContent = warningContent;
	}

	public Integer getWarningStatus() {
		return warningStatus;
	}

	public void setWarningStatus(Integer warningStatus) {
		this.warningStatus = warningStatus;
	}

	public Boolean getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Boolean isHandle) {
		this.isHandle = isHandle;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Disposal getDisposal() {
		return disposal;
	}

	public void setDisposal(Disposal disposal) {
		this.disposal = disposal;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}

class MySerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object value, JsonGenerator jgen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		Class<? extends Object> zclass = value.getClass();
		Field [] fields = zclass.getDeclaredFields();
		try {
			for(Field field : fields){
				field.setAccessible(true);
				String type = field.getGenericType().toString();
				Object val=field.get(value);
				String typeReal = "";
				if (type.contains("<") && type.contains(">")) {
					typeReal = type.substring(type.lastIndexOf(".", type.indexOf("<")) + 1,
							type.indexOf("<"));
				} else {
					typeReal = type.substring(type.lastIndexOf(".") + 1, type.length());
				}
				if(val == null){
					if(typeReal.equals("list")){
						List list = (List)val;
						if(list.size() == 0){
							jgen.writeObjectField(field.getName(), value);
						}
					}else if (typeReal.equals("")){
						
					}else{
					}
				}
				
			}
			System.out.println(">>>>>"+value);
		} catch (IllegalArgumentException e) {
			System.out.println("参数异常");
		} catch (IllegalAccessException e) {
			System.out.println("得到的");
		}
	}
}
