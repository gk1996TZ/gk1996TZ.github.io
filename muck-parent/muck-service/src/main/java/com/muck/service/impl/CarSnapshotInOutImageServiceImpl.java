package com.muck.service.impl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.annotation.ExcelTemplate;
import com.muck.domain.Car;
import com.muck.domain.CarSnapshotInOutImage;
import com.muck.domain.Device;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.CarSnapshotInOutImageMapper;
import com.muck.mapper.DeviceMapper;
import com.muck.response.CarSnapshotResponse;
import com.muck.response.SimpleDeviceTypeInfoResponse;
import com.muck.response.StatusCode;
import com.muck.service.CarService;
import com.muck.service.CarSnapshotInOutImageService;

/**
 * 车辆进入进出工地处置场Service实现
 */
@Service
public class CarSnapshotInOutImageServiceImpl extends BaseServiceImpl<CarSnapshotInOutImage>
		implements CarSnapshotInOutImageService {

	@Autowired
	private CarSnapshotInOutImageMapper carSnapshotInOutImageMapper;
	
	@Autowired
	private CarService carService;

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private HttpSession session;

	//存放车辆识别单个车辆的最新的一条记录
	private static Map<String,Object> carSnapshotMap = new HashMap<String,Object>();
	@Override
	protected BaseMapper<CarSnapshotInOutImage> getMapper() {
		return carSnapshotInOutImageMapper;
	}

	@Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate,
			List<CarSnapshotInOutImage> list) {
		if (list != null) {
			// 声明一个存放表格数据的集合
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 存放到表格中的数据列表，包括表头和表身数据
			// 声明一个表头键值对容器，这里存放所有的表头信息
			Map<String, String> tableHead = new TreeMap<String, String>();

			Map<String, String> tableBody = null;

			// 获取传入的表格模版的类的对象
			Class<?> cls = excelTemplate.getClass();
			// 获取这个类的属性集合
			Field[] fields = cls.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				Integer temp = 0;
				for (Field field : fields) {
					// 设置属性是可访问的
					field.setAccessible(true);

					String fieldName = field.getName();
					// 获取属性上的注解
					ExcelTemplate template = field.getAnnotation(ExcelTemplate.class);
					// 将注解为表头字段
					tableHead.put(temp+fieldName, template.name());
					temp ++ ;
				}
			}
			// 将表头放入总集合
			data.add(tableHead);
			// 生成表身数据
			// 遍历传入的数据列表
			for (CarSnapshotInOutImage carSnapshotInOutImage : list) {
				// 获取类的对象
				Class<?> clazz = carSnapshotInOutImage.getClass();

				Set<String> keySet = tableHead.keySet();
				tableBody = new TreeMap<String, String>();
				Iterator<String> iterator = keySet.iterator();
				Integer temp = 0;
				while (iterator.hasNext()) {
					String key = iterator.next();
					try {
						if (key.equals("4has_placeName")) {
							String placeName = carSnapshotInOutImage.getSiteName() == null
									? carSnapshotInOutImage.getDisposalName() : carSnapshotInOutImage.getSiteName();
							tableBody.put(temp+key, placeName);
							continue;
						}/* else if (key.equals("has_snapshotType")) {
							String strType = carSnapshotInOutImage.getSnapshotType();
							if (StringUtils.isNotBlank(strType)) {
								Integer type = Integer.parseInt(strType);
								switch (type) {
								case 1:
									tableBody.put(temp+key, DeviceType.SITE.getName());
									break;
								case 3:
									tableBody.put(temp+key, DeviceType.DISPOSAL.getName());
									break;
								}
							} else {
								tableBody.put(temp+key, null);
							}
							continue;
						}*/

						Field filed = clazz.getDeclaredField(key.replace(temp+"has_", ""));
						filed.setAccessible(true);
						String type = filed.getGenericType().toString();
						// 获取该对象的此属性的值
						Object obj = filed.get(carSnapshotInOutImage);
						// 如果获取到的是时间类型，则转化一下时间格式
						if ("date".equalsIgnoreCase(type.substring(type.lastIndexOf(".") + 1, type.length()))) {
							Date date = (Date) obj;
							if (date == null) {
								tableBody.put(temp+key, null);
							} else {
								// 将该key与属性值添加到当前条数据的Excel表单元格中
								tableBody.put(temp+key, sdf.format(date));
							}
						} else {
							if (obj == null) {
								tableBody.put(temp+key, null);
							} else {
								if("1has_carCardColor".equals(key)){
									String color = obj.toString();
									if("1".equals(color)){
										tableBody.put(temp+key, "蓝色");
									}else if ("2".equals(color)){
										tableBody.put(temp+key, "黄色");
									}
								}else {
									// 将该key与属性值添加到当前条数据的Excel表单元格中
									tableBody.put(temp+key, obj.toString());
								}
							}
						}
						temp++;
					} catch (Exception e) {
						logger.error("",e);
						throw new BizException(StatusCode.INTERNAL_ERROR);
					}
				}
				data.add(tableBody);
			}
			return data;
		}
		return null;
	}

	// 根据条件查询信息组装CarSnapshotOutImage对象
	@Override
	public CarSnapshotResponse saveCarSnapshotOutInfoByCondition(String carCode, String carCardColor,String registerId, String snapshotTime,
			String picBigPath,String picSmailPath) {
		CarSnapshotResponse carSnapshotResponse = new CarSnapshotResponse();
		SimpleDateFormat sdf = null;
		CarSnapshotInOutImage carSnapshotInOutImage = new CarSnapshotInOutImage();
		if (StringUtils.isNotBlank(registerId)) {
			// 根据通道号获取简单实体
			SimpleDeviceTypeInfoResponse simpleDeviceTypeResponse = deviceMapper
					.queryDeviceTypeByRegisterId(registerId);
			if (simpleDeviceTypeResponse != null) {
				String deviceId = simpleDeviceTypeResponse.getDeviceId();
				if (StringUtils.isNotBlank(deviceId)) {
					// 根据id查询设备
					Device device = deviceMapper.selectById(deviceId);
					if (device != null) {
						// 设备类型
						Integer deviceType = device.getDeviceType();
						if (deviceType != null) {
							switch (deviceType) {
								// 工地
								case 1:
									carSnapshotInOutImage.setSiteName(device.getDeviceName());
									carSnapshotInOutImage.setSiteId(device.getSite().getId());
									carSnapshotInOutImage.setSnapshotType("工地");
									carSnapshotResponse.setTypeId(device.getSite().getId());
									carSnapshotResponse.setTypeName("工地");
									carSnapshotResponse.setRelevantName(device.getSite().getSiteName());
									break;
								// 处置场
								case 3:
									carSnapshotInOutImage.setDisposalName(device.getDeviceName());
									carSnapshotInOutImage.setDisposalId(device.getDisposal().getId());
									carSnapshotInOutImage.setSnapshotType("处置场");
									carSnapshotResponse.setTypeId(device.getDisposal().getId());
									carSnapshotResponse.setTypeName("处置场");
									carSnapshotResponse.setRelevantName(device.getDisposal().getDisposalName());
									break;
								default:
									carSnapshotInOutImage.setSiteName(device.getDeviceName());
									carSnapshotInOutImage.setSiteId(device.getSite().getId());
									carSnapshotInOutImage.setSnapshotType("工地");
									carSnapshotResponse.setTypeId(device.getSite().getId());
									carSnapshotResponse.setTypeName("工地");
									carSnapshotResponse.setRelevantName(device.getSite().getSiteName());
							}
						}else {
							carSnapshotInOutImage.setSiteName(device.getDeviceName());
							carSnapshotInOutImage.setSiteId(device.getSite().getId());
							carSnapshotInOutImage.setSnapshotType("工地");
							carSnapshotResponse.setTypeId(device.getSite().getId());
							carSnapshotResponse.setTypeName("工地");
							carSnapshotResponse.setRelevantName(device.getSite().getSiteName());
						}
						carSnapshotInOutImage.setAreaId(device.getArea().getAreaId());
						carSnapshotInOutImage.setAreaName(device.getDeviceAreaName());
						carSnapshotInOutImage.setDeleted(true);
						carSnapshotInOutImage.setDeviceId(deviceId);
						Manager manager = (Manager) session.getAttribute("manager");
						if (manager != null) {
							carSnapshotInOutImage.setOperatorId(manager.getOperatorId());
							carSnapshotInOutImage.setOperatorName(manager.getOperatorName());
							carSnapshotInOutImage.setOperatorPhone(manager.getManagerPhone());
						}
					}
				}
			} else {
				carSnapshotResponse.setTypeId("");
				carSnapshotResponse.setTypeName("");
				carSnapshotResponse.setRelevantName("");
				carSnapshotResponse.setCompanyId("");
				carSnapshotResponse.setCompanyName("");
				carSnapshotResponse.setCompanyContact("");
			}
		}
		
		
		// 获取企业
		Car car = null; 
		try {
			car = carService.queryByCarCode(carCode);
		} catch (Exception e) {
			logger.error("根据车牌号查询出多个车辆信息");
		}
		if (car != null) {
			carSnapshotInOutImage.setCompanyId(car.getCompanyId());
			carSnapshotInOutImage.setCompanyName(car.getCompanyName());
			carSnapshotInOutImage.setCompanyContact(car.getCompanyContact());
			carSnapshotResponse.setCompanyId(car.getCompanyId());
			carSnapshotResponse.setCompanyName(car.getCompanyName());
			carSnapshotResponse.setCompanyContact(car.getCompanyContact());
		} else {
			carSnapshotResponse.setCompanyId("");
			carSnapshotResponse.setCompanyName("");
			carSnapshotResponse.setCompanyContact("");
		}
		
		
		if (StringUtils.isNotBlank(snapshotTime)) {
			sdf = new SimpleDateFormat("yyyy—MM-dd HH:mm:ss");
			try {
				// TODO 暂时创建一个时间
				Date date = new Date();
				carSnapshotInOutImage.setSnapshotTime(date);
				carSnapshotInOutImage.setCreatedTime(new Date());
				carSnapshotInOutImage.setUpdatedTime(new Date());
				carSnapshotResponse.setTime(sdf.format(date));
			} finally {
				carSnapshotResponse.setCarCode(carCode);
				carSnapshotResponse.setCarCardColor(carCardColor);
				carSnapshotResponse.setBigImage(picBigPath);
				carSnapshotResponse.setSmailImage(picSmailPath);
				
				
				carSnapshotInOutImage.setCarCode(carCode);
				carSnapshotInOutImage.setCarCardColor(carCardColor);
				carSnapshotInOutImage.setSnapshotBigImagePath(picBigPath);
				carSnapshotInOutImage.setSnapshotSmailImagePath(picSmailPath);
				carSnapshotInOutImage.setDeviceRegisterId(registerId);
				// 保存数据库
				
				
				
				
				carSnapshotInOutImageMapper.insert(carSnapshotInOutImage);
				carSnapshotResponse.setId(carSnapshotInOutImage.getId());
				/*
				 * SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
				 * String day = sdfDay.format(new Date());
				 */
				/*
				 * Integer count =
				 * queryCarSnapshotCountOneDay(day,carCode,carSnapshotResponse.
				 * getRelevantName()); carSnapshotResponse.setCount(count);
				 */
			}
		}
		return carSnapshotResponse;
	}

	@Override
	public Integer queryCarSnapshotCountOneDay(String day, String carCode, String relevantName) {
		return carSnapshotInOutImageMapper.queryCarSnapshotCountOneDay(day, carCode, relevantName);
	}

	@Override
	public List<CarSnapshotResponse> getTopTen() {
		return carSnapshotInOutImageMapper.getTopTen();
	}

	@Override
	protected String getFields() {
		return " * ";
	}
}
