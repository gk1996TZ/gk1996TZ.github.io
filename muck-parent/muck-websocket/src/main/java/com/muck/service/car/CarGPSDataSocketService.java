package com.muck.service.car;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.muck.domain.CarType;
import com.muck.domain.ElectricFenceCar;
import com.muck.domain.GpsData;
import com.muck.domain.Warning;
import com.muck.init.InitCarsDataService;
import com.muck.response.SimpleCompanyResponse;
import com.muck.service.CarService;
import com.muck.service.ElectricFenceCarService;
import com.muck.service.GpsDataService;
import com.muck.service.PropertiesService;
import com.muck.service.WarningService;
import com.muck.utils.HttpClientUtil;
import com.muck.utils.PolygonSelectBoxUtil;

/**
 * 获取车辆GPS数据
 * 北斗gps数据socket
 */
@ServerEndpoint(value = "/getCarGPSDatas.ws")
@Component
public class CarGPSDataSocketService {

	Logger logger = LoggerFactory.getLogger(getClass());

	// 标志变量,表示是否已经初始化了bean对象,默认为false
	private boolean isInitBean;

	private PropertiesService propertiesService; // 属性配置文件service

	private GpsDataService gpsDataService; // 车辆

	private CarService carService;

	private ElectricFenceCarService electricFenceCarService;

	private WarningService warningService;

	private InitCarsDataService initCarsDataService; // 系统初始化后获取车辆数据的service

	private Session session;

	// 用来存储所有的车辆Gps数据
	private static CopyOnWriteArrayList<CarGPSDataSocketService> webSocketSet = new CopyOnWriteArrayList<CarGPSDataSocketService>();

	// 车牌号
	private static String carCode;

	/**
	 * 初始化service的bean对象
	 */
	public void initBean() {
		if (!isInitBean) {
			synchronized (CarGPSDataSocketService.class) {
				if (!isInitBean) {
					this.initCarsDataService = applicationContext.getBean(InitCarsDataService.class);
					this.propertiesService = applicationContext.getBean(PropertiesService.class);
					this.gpsDataService = applicationContext.getBean(GpsDataService.class);
					this.carService = applicationContext.getBean(CarService.class);
					this.electricFenceCarService = applicationContext.getBean(ElectricFenceCarService.class);
					this.warningService = applicationContext.getBean(WarningService.class);
					isInitBean = true;
				}
			}
		}
	}

	/**
	 * 群发自定义消息
	 * 
	 * @throws Exception
	 *
	 */
	public void sendInfo() throws Exception {

		// 初始化bean对象
		initBean();

		// 保存全部的车辆的GPS数据
		List<GpsData> allGpsDatas = new ArrayList<GpsData>();

		// 北斗全部数据
		List<Map<String, Object>> beidouCarList = initCarsDataService.getBeidouCarList();

		// 获取企业数据
		List<SimpleCompanyResponse> companys = initCarsDataService.getCompanys();

		// 分片List
		List<List<Map<String, Object>>> beidouList = splitList(beidouCarList, 10); // 暂定20

		// 切片总数
		Integer splitSize = (beidouList == null ? 0 : beidouList.size());// 存储总的切片总数

		if (splitSize > 0) {

			// 每一个分片就是一个线程
			CountDownLatch latch = new CountDownLatch(splitSize);

			for (int i = 0; i < splitSize; i++) {

				List<Map<String, Object>> carList = beidouList.get(i);

				new CarThread(latch, propertiesService.getBeidouWebserviceUrl(), propertiesService.getBeidouUserKey(),
						carList, allGpsDatas).start();
			}

			// 等待
			latch.await();

			if (!allGpsDatas.isEmpty()) {
				GpsData gpsData = null;
				if (StringUtils.isNotBlank(carCode)) {
					for (GpsData data : allGpsDatas) {
						data.setStyle(CarType.CHANG_BAO.getType());
						if (data.getVehicleNo().equals(CarGPSDataSocketService.carCode)) {
							if (data.getStyle() == CarType.CHANG_BAO.getType()) {
								data.setStyle(CarType.BEI_DOU.getType());
							} else if (data.getStyle() == CarType.BEI_DOU.getType()) {
								data.setStyle(CarType.CHANG_BAO.getType());
							}
							gpsData = new GpsData();
							gpsData.setLatitude(data.getLatitude());
							gpsData.setLongitude(data.getLongitude());
							break;
						}
					}

				}

				StringBuffer stringBuffer = new StringBuffer();
				for (GpsData data : allGpsDatas) {
					System.out.println("data--->" + data);
					stringBuffer.append("'");
					stringBuffer.append(data.getVehicleNo().trim());
					stringBuffer.append("'");
					stringBuffer.append(",");
				}
				// carService.updateRunning(false);
				carService.updateCarsIsRunning(1, stringBuffer.substring(0, stringBuffer.length() - 1).toString());
				logger.info(
						"更新数据库中北斗数据状态is_running为1：" + stringBuffer.substring(0, stringBuffer.length() - 1).toString());

				StringBuffer carCodes = new StringBuffer();
				for (GpsData data : allGpsDatas) {
					// 拼接车牌号
					carCodes.append("'");
					carCodes.append(data.getVehicleNo().trim());
					carCodes.append("'");
					carCodes.append(",");
				}
				if (carCodes.length() > 0) {
					carCodes.deleteCharAt(carCodes.length() - 1);
				}
				// 根据车牌号或取电子围栏
				List<ElectricFenceCar> list = electricFenceCarService
						.queryElectricFenceCarSimpleByCarCodes(carCodes.toString());
				List<Warning> listWarning = new ArrayList<Warning>();
				for (GpsData data : allGpsDatas) {
					// 车牌号
					String carCode = data.getVehicleNo().trim();
					// 根据车牌号或取电子围栏
					if (!StringUtils.isBlank(carCode) && list != null) {
						for (ElectricFenceCar elecCar : list) {
							if (carCode.equals(elecCar.getCarCode())) {
								// 获取多边形多个点的经纬度 字符串格式 str_point =
								// [经度,纬度],[经度,纬度],[经度,纬度]
								String str_point = elecCar.getElectricFenceCoordinate();
								// 去掉所有的中括号
								if (!StringUtils.isBlank(str_point)) {
									str_point = str_point.replaceAll("[", "");
									str_point = str_point.replaceAll("]", "");
								}
								String[] ps = str_point.split(",");
								// 创建一个存放多边形各个点的列表
								List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
								// 将传入的点放到列表中
								for (int i = 0; i < ps.length; i++) {
									// 判断下标是否是偶数
									if (i % 2 == 0) {
										pts.add(new Point2D.Double(Double.parseDouble(ps[i]),
												Double.parseDouble(ps[i + 1])));
									}
								}
								// 创建当前获取到的车辆的经纬度点
								Point2D.Double point = new Point2D.Double(
										Double.parseDouble(String.valueOf((double) data.getLongitude() / 1000000)),
										Double.parseDouble(String.valueOf((double) data.getLatitude() / 1000000)));
								// 判断当前点是否在多边形内，如果不在，创建一个告警信息
								if (!PolygonSelectBoxUtil.IsPtInPoly(point, pts)) {
									Warning warning = new Warning();
									warning.setCarCode(carCode);
									warning.setWarningType(2);
									warning.setWarningName("车辆" + carCode + "驶出电子围栏");
									warning.setWarningContent("车辆" + carCode + "驶出电子围栏");
									Date date = new Date();
									warning.setWarningTime(date);
									warning.setCreatedTime(date);
									warning.setUpdatedTime(date);
									listWarning.add(warning);
									// 将车牌号发送给前台，前台根据这个车牌号拼接一个车辆驶出电子围栏的告警信息
									EFPool.sendMessageToAll(carCode);
								}
								break;
							}
						}
					}
				}
				if (listWarning.size() > 0) {
					warningService.saveAll(listWarning);
				}

				for (int i = 0; i < companys.size(); i++) {
					SimpleCompanyResponse c = companys.get(i);
					List<String> carCodess = c.getCars();
					c.setRunningCarCount(0);
					c.getRunningCars().clear();
					for (GpsData data : allGpsDatas) {
						String vehicleNo = data.getVehicleNo();
						if (carCodess.contains(vehicleNo)) {
							c.setRunningCarCount(c.getRunningCarCount() + 1);
							c.getRunningCars().add(vehicleNo);
						}
					}
				}
				JSONObject result = new JSONObject();
				result.put("cares", allGpsDatas);
				result.put("gpsData", gpsData);
				result.put("companys", companys);
				for (CarGPSDataSocketService item : webSocketSet) {
					try {
						String contentResult = JSONObject.toJSONString(result);
						item.sendMessage(contentResult);
						gpsDataService.saveAll(allGpsDatas);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 连接建立成功调用此方法
	 */
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("连接成功一个websocket");
		webSocketSet.add(this);
		this.session = session;

		// 连接建立完成之后就调用发送方法,目的是给前台直接建立通讯,返回数据
		try {
			//this.sendInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接关闭之后调用的方法
	 */
	@OnClose
	public void onClose() {

		webSocketSet.remove(this);

		System.out.println("连接关闭之后调用的方法");
	}

	/**
	 * 收到客户端后消息后调用的方法
	 * 
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {

		JSONObject obj = JSONObject.parseObject(message);

		CarGPSDataSocketService.carCode = obj.getString("carCode");
	}

	/**
	 * 发生错误时调用
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误啦");

		System.out.println(error.getMessage());
	}

	public void sendMessage(String message) throws IOException {

		this.session.getBasicRemote().sendText(message);
	}

	// -----------------------------------

	// 静态的上下文对象
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		CarGPSDataSocketService.applicationContext = applicationContext;
	}

	/**
	 * 将一个List集合按照指定的大小进行分割几份。
	 */
	public <T> List<List<T>> splitList(List<T> source, int limitSize) {

		// 1、判断是否为空,如果源List为空,直接返回
		if (source == null || source.isEmpty()) {
			return null;
		}

		// 2、最终的分割好的List集合
		List<List<T>> resultList = new ArrayList<List<T>>();

		// 3、获取源集合的大小,判断是否超过指定的大小,如果没有超过则直接返回源集合
		int sourceSize = source.size();
		if (sourceSize <= limitSize) {
			resultList.add(source);
			return resultList;
		}

		// 4、计算份数
		int length = sourceSize / limitSize + 1;
		int remaider = sourceSize % limitSize; // 计算余数
		for (int i = 0; i < length; i++) {
			List<T> value = null;
			if (i != (length - 1)) {
				value = source.subList(i * limitSize, (i + 1) * limitSize);
			} else {
				value = source.subList(sourceSize - remaider, sourceSize);
			}
			resultList.add(value);
		}
		return resultList;
	}
}

class CarThread extends Thread {

	private CountDownLatch countDownLatch;

	private String url;

	private String userKey;

	private List<Map<String, Object>> carList;

	private List<GpsData> allGpsDatas;

	public CarThread(CountDownLatch countDownLatch, String url, String userKey, List<Map<String, Object>> carList,
			List<GpsData> allGpsDatas) {
		this.countDownLatch = countDownLatch;
		this.url = url;
		this.userKey = userKey;
		this.carList = carList;
		this.allGpsDatas = allGpsDatas;
	}

	@Override
	public void run() {
		JSONObject object = new JSONObject();
		object.put("UserKey", userKey);
		object.put("VehicleList", carList);

		String json = object.toJSONString();

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("reqParam", json);
		String content = HttpClientUtil.doGet(url, requestMap);

		if (StringUtils.isNotBlank(content)) {
			List<GpsData> datas = JSONArray.parseArray(content, GpsData.class);
			if (datas != null) {
				Set<GpsData> set = new java.util.HashSet<GpsData>(datas);
				for (GpsData data : set) {
					if (data.getCode() != -1) {
						allGpsDatas.add(data);
					}
				}
			}
		}
		// 每个线程执行完毕之后，计数器要减1
		countDownLatch.countDown();
	}
}
