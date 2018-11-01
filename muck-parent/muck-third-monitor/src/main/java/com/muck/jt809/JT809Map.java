package com.muck.jt809;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muck.domain.Car;
import com.muck.domain.GpsData;

/**
 * 为了避免重复推送数据 进行重复数据过滤 存储所有长宝推送的车辆数据 总数统计用两个map统计是因为企业id和车辆组id可能有重复
 */
public class JT809Map {
	static Logger logger = LoggerFactory.getLogger("JT809Map");
	/**
	 * 为了避免重复推送数据 进行重复数据过滤
	 */
	private static Map<String, GpsData> carsMap = new HashMap<String, GpsData>();
	/** 企业总数统计 */
	private static Map<String, Integer> compMap = new HashMap<String, Integer>();
	/** 车辆组总数统计 */
	private static Map<String, Integer> carGroupMap = new HashMap<String, Integer>();
	/** 所有车辆数据 */
	private static Map<String, Car> allcarsMap = new HashMap<String, Car>();

	/** 所有企业id对应的企业名称数据 */
	public static Map<String, String> carIdAndNameMap = new HashMap<String, String>();

	/**
	 * 添加数据 根据车牌号 经纬度 判断
	 * 
	 * @param GpsData
	 * @return 有新数据返回true 否则返回false
	 */
	public static synchronized boolean addOrRemoveCars(GpsData gpsData) {
		if (gpsData == null || gpsData.getVehicleNo() == null) {
			return false;
		}
		GpsData ogpsdata = carsMap.get(gpsData.getVehicleNo().trim());
		if (ogpsdata == null) {
			carsMap.put(gpsData.getVehicleNo().trim(), gpsData);
			carIdAndNameMap.put(gpsData.getCompanyId(), gpsData.getCompanyName());
			// 总数++
			//logger.info("总数添加数据：" + gpsData);
			int ccount = getCountById(gpsData.getCompanyId(), (byte) 0);
			// int gcount=getCountById(gpsData.getCarGroupId(),(byte)1);
			// 统计车辆组下车辆总数
			setCountById(gpsData.getCompanyId(), ++ccount, (byte) 0);
			// setCountById(gpsData.getCarGroupId(), ++gcount, (byte)0);

		} else if (gpsData.getStatus() == 0) {
			carsMap.remove(gpsData.getVehicleNo().trim());
			// 总数--
			//logger.info("移除数据：" + gpsData);
			// 统计企业下车辆总数
			int ccount = getCountById(gpsData.getCompanyId(), (byte) 0);
			// int gcount=getCountById(gpsData.getCarGroupId(),(byte)1);
			// 统计车辆组下车辆总数
			setCountById(gpsData.getCompanyId(), --ccount, (byte) 0);
			// setCountById(gpsData.getCarGroupId(), --gcount, (byte)0);
		} else {
			if (ogpsdata.getLongitude() != gpsData.getLongitude() || ogpsdata.getLatitude() != gpsData.getLatitude()) {
				//logger.info("添加数据：" + gpsData);
				carsMap.put(gpsData.getVehicleNo().trim(), gpsData);
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 根据车牌号读取数据
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @return GpsData
	 */
	public static synchronized GpsData getByNo(String vehicleNo) {
		return carsMap.get(vehicleNo);
	}

	/**
	 * 根据车牌号移除数据
	 * 
	 * @param vehicleNo
	 *            车牌号
	 */
	public static synchronized void removeByNo(String vehicleNo) {
		carsMap.remove(vehicleNo);
	}

	/**
	 * 企业或者车辆组总数更新
	 * 
	 * @param id
	 *            企业或者车辆组id
	 * @param flag
	 *            true 表示+ false表示减
	 * @param type类型
	 *            0.表示企业 1.表示车辆组
	 * @return 返回新的总数
	 */
	public static synchronized int updateCout(String id, boolean flag, byte type) {
		Integer ct = null;
		if (type == 0) {
			ct = compMap.get(id);
		} else if (type == 1) {
			ct = carGroupMap.get(id);
		}

		if (ct != null) {
			if (flag) {
				++ct;
			} else {
				--ct;
			}
			return ct;
		}
		return 0;
	}

	/**
	 * 企业或者车辆组总数获取
	 * 
	 * @param id
	 *            企业或者车辆组id
	 * @param type类型
	 *            0.表示企业 1.表示车辆组
	 * @return 返回总数
	 */
	public static synchronized Integer getCountById(String id, byte type) {
		Integer ct = compMap.get(id);
		return ct == null ? 0 : ct;
	}

	/**
	 * 设置企业或者车辆组总数
	 * 
	 * @param id
	 *            企业或者车辆组id
	 * @param count
	 *            总数
	 * @param type类型
	 *            0.表示企业 1.表示车辆组
	 */
	public static synchronized void setCountById(String id, Integer count, byte type) {
		if (type == 0) {
			compMap.put(id, count);
		} else if (type == 1) {
			carGroupMap.put(id, count);
		}
	}

	/**
	 * 根据车牌号获取车辆数据
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @param Car
	 *            车辆数据
	 */
	public static synchronized Car getCarByNo(String vehicleNo) {
		return allcarsMap.get(vehicleNo);
	}

	/**
	 * 更新车的状态
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @param status
	 *            车辆状态 1:运行中0:停止运行
	 * @return true or false true操作成功
	 */
	public static synchronized void updateStatus(String vehicleNo, byte status) {
		boolean flag = status == 1 ? true : false;
		Car car = allcarsMap.get(vehicleNo);
		car.setRunning(flag);
	}

	/**
	 * 添加车辆数据
	 * 
	 * @param car
	 *            车
	 */
	public static synchronized void addCar(Car car) {
		allcarsMap.put(car.getCarCode(), car);
	}

	/** 获取所有在线车辆数据 */
	public static synchronized Map<String, GpsData> getAllMap() {
		return carsMap;
	}

	/** 获取所有企业在线总数 */
	public static synchronized Map<String, Integer> getCountByComp() {
		return compMap;
	}

	/** 获取企业名称 */
	public static synchronized String getCompanyNameById(String id) {
		return carIdAndNameMap.get(id);
	}
}
