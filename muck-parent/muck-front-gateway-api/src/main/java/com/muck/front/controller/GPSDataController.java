package com.muck.front.controller;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Collections;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Car;
import com.muck.domain.GpsDataLine;
import com.muck.helper.QueryHelper;
import com.muck.response.ResponseResult;
import com.muck.service.CarService;
import com.muck.service.GpsDataLineService;
import com.muck.utils.PolygonSelectBoxUtil;
import com.muck.utils.map.CoordinateConverter;

/**
 * 车辆GPS信息Controller
 */
@RestController("FrontGPSDataController")
@RequestMapping("/front/gps/")
public class GPSDataController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	ExecutorService cachedThreadPool=Executors.newCachedThreadPool(); //用线程池

	@Autowired
	private CarService carService;

	@Autowired
	private GpsDataLineService gpsDataLineService;

	/*
	 * @param: points 多边形选框的各个点坐标
	 * 
	 * @param: startTime 根据时间段查询的开始时间
	 * 
	 * @param: endTime 根据时间段查询的结束时间
	 * 
	 * @param: carNumber 根据车牌号查询
	 * 
	 * @return:ResponseResult 返回含有车辆GPS数据的结果集
	 * 
	 * @Description: 轨迹回放
	 * 
	 */
	@RequestMapping(value = "getCarGPSInfoReturn.action", method = RequestMethod.POST)
	public ResponseResult getCarGPSInfoReturn(String[] points, String startTime, String endTime, String carCode,
			String carCardColor) {
		List<GpsDataLine> listDevice = null;
		QueryHelper queryHelper = new QueryHelper();
		// 拼接查询条件
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			queryHelper.addCondition(startTime, "gps_time between '%s'", false);
			queryHelper.addCondition(endTime, " '%s' ", false);
		}
		if (StringUtils.isNotBlank(carCode)) {
			queryHelper.addCondition(carCode, "vehicle_no = '%s'", false);
		}
		if (StringUtils.isNotBlank(carCardColor)) {
			queryHelper.addCondition(carCardColor, "vehicle_color = '%s'", false);
		}
		queryHelper.addCondition(30000000, "latitude <= %d", false).addCondition(29000000, "latitude >= %d", false);
		// 查询到设备的列表
		listDevice = gpsDataLineService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());

		// 如果传入的点不为空
		if (points != null) {
			List<GpsDataLine> devices = new ArrayList<GpsDataLine>();
			// 创建一个存放多边形各个点的列表
			List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
			// 如果传入的坐标点数组长度不是偶数，说明传入的有可能不是一个坐标
			if (points.length % 2 != 0) {
				logger.error("调用getCarGPSInfoReturn接口时POINT_FORMAT_ERROR异常");
			}
			// 如果长度小于六，则说明小于三个坐标，不能构成一个多边形
			if (points.length < 6) {
				logger.error("调用getCarGPSInfoReturn接口时POINT_SIZE_LESS_THREE异常");
			}
			// 将传入的点放到列表中
			for (int i = 0; i < points.length; i++) {
				// 判断下标是否是偶数
				if (i % 2 == 0) {
					pts.add(new Point2D.Double(Double.parseDouble(points[i]), Double.parseDouble(points[i + 1])));
				}
			}
			for (GpsDataLine gpsData : listDevice) {
				if (gpsData.getLatitude() != null && gpsData.getLongitude() != null) {
					Point2D.Double point = new Point2D.Double(
							Double.parseDouble(String.valueOf((double) gpsData.getLongitude() / 1000000)),
							Double.parseDouble(String.valueOf((double) gpsData.getLatitude() / 1000000)));
					if (PolygonSelectBoxUtil.IsPtInPoly(point, pts)) {
						devices.add(gpsData);
					}
				}
			}
			listDevice = devices;
		}
		return ResponseResult.ok(listDevice);

	}

	/*
	 * @param: points 多边形选框的各个点坐标
	 * 
	 * @param: currentTime 根据当前时间返回前三十分钟的数据
	 * 
	 * @return:ResponseResult 返回含有车辆GPS数据的结果集
	 * 
	 * @Description: 显示轨迹
	 * 
	 */
	@RequestMapping(value = "showTrajectory.action", method = RequestMethod.POST)
	public ResponseResult showTrajectory(String[] points, String currentTime, String carCode, String carCardColor) {

		List<GpsDataLine> listDevice = null;
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition("DATE_SUB(NOW(),INTERVAL  0.5 HOUR)", "gps_time >= %s", false);
		if (StringUtils.isNotBlank(carCode)) {
			queryHelper.addCondition(carCode, "vehicle_no = '%s'", false);
		}
		if (StringUtils.isNotBlank(carCardColor)) {
			queryHelper.addCondition(carCardColor, "vehicle_color='%s'", false);
		}
		queryHelper.addCondition(30000000, "latitude <= %d", false).addCondition(29000000, "latitude >= %d", false);

		// 查询到设备的列表
		listDevice = gpsDataLineService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());

		// 如果传入的点不为空
		if (points != null) {
			List<GpsDataLine> devices = new ArrayList<GpsDataLine>();
			// 创建一个存放多边形各个点的列表
			List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
			// 如果传入的坐标点数组长度不是偶数，说明传入的有可能不是一个坐标
			if (points.length % 2 != 0) {
				logger.error("调用getCarGPSInfoReturn接口时POINT_FORMAT_ERROR异常");
			}
			// 如果长度小于六，则说明小于三个坐标，不能构成一个多边形
			if (points.length < 6) {
				logger.error("调用getCarGPSInfoReturn接口时POINT_SIZE_LESS_THREE异常");
			}
			// 将传入的点放到列表中
			for (int i = 0; i < points.length; i++) {
				// 判断下标是否是偶数
				if (i % 2 == 0) {
					pts.add(new Point2D.Double(Double.parseDouble(points[i]), Double.parseDouble(points[i + 1])));
				}
			}
			for (GpsDataLine gpsData : listDevice) {
				if (gpsData.getLatitude() != null && gpsData.getLongitude() != null) {
					Point2D.Double point = new Point2D.Double(
							Double.parseDouble(String.valueOf((double) gpsData.getLongitude() / 1000000)),
							Double.parseDouble(String.valueOf((double) gpsData.getLatitude() / 1000000)));
					if (PolygonSelectBoxUtil.IsPtInPoly(point, pts)) {
						devices.add(gpsData);
					}
				}
			}
			listDevice = devices;
		}
		return ResponseResult.ok(listDevice);
	}

	/**
	 * 
	 * 多边形选框返回车辆列表
	 * 
	 * @RequestMapping(value = "showCarInfo.action", method =
	 *                       RequestMethod.POST) public ResponseResult
	 *                       showCarInfo(String[] points, String startTime,
	 *                       String endTime, String carCode) {
	 * 
	 *                       QueryHelper queryHelper = new QueryHelper(); //
	 *                       拼接查询条件 if (StringUtils.isNotBlank(startTime)) {
	 *                       queryHelper.addCondition(startTime,
	 *                       "gps_time between '%s'", false); } if
	 *                       (StringUtils.isNotBlank(endTime)) {
	 *                       queryHelper.addCondition(endTime, "'%s'", false); }
	 *                       if (StringUtils.isNotBlank(carCode)) {
	 *                       queryHelper.addCondition(carCode,
	 *                       "vehicle_no = '%s'", false); }
	 *                       queryHelper.addCondition(30000000, "latitude <= %d"
	 *                       , false).addCondition(29000000, "latitude >= %d",
	 *                       false);
	 * 
	 *                       // 查询到设备的列表 List<GpsDataLine> listGpsData =
	 *                       gpsDataLineService.queryData(queryHelper.
	 *                       getWhereSQL(), queryHelper.getWhereParams());
	 * 
	 *                       List<Car> datas = null; // 如果传入的点不为空 if (points !=
	 *                       null && !listGpsData.isEmpty()) {
	 * 
	 *                       datas = new ArrayList<Car>(); // 创建一个存放多边形各个点的列表
	 *                       List<Point2D.Double> pts = new ArrayList
	 *                       <Point2D.Double>(); //
	 *                       如果传入的坐标点数组长度不是偶数，说明传入的有可能不是一个坐标 if (points.length %
	 *                       2 != 0) { logger.error(
	 *                       "调用getCarGPSInfoReturn接口时POINT_FORMAT_ERROR异常"); }
	 *                       // 如果长度小于六，则说明小于三个坐标，不能构成一个多边形 if (points.length <
	 *                       6) { logger.error(
	 *                       "调用getCarGPSInfoReturn接口时POINT_SIZE_LESS_THREE异常");
	 *                       } // 将传入的点放到列表中 for (int i = 0; i < points.length;
	 *                       i++) { // 判断下标是否是偶数 if (i % 2 == 0) { pts.add(new
	 *                       Point2D.Double(Double.parseDouble(points[i]),
	 *                       Double.parseDouble(points[i + 1]))); } } for
	 *                       (GpsDataLine gpsData : listGpsData) { if
	 *                       (gpsData.getLatitude() != null &&
	 *                       gpsData.getLongitude() != null) { Point2D.Double
	 *                       point = new Point2D.Double(
	 *                       Double.parseDouble(String.valueOf((double)
	 *                       gpsData.getLongitude() / 1000000)),
	 *                       Double.parseDouble(String.valueOf((double)
	 *                       gpsData.getLatitude() / 1000000))); if
	 *                       (PolygonSelectBoxUtil.IsPtInPoly(point, pts)) { //
	 *                       判断传入的车牌号是否为空，不为空则根据车牌号查询车辆 if
	 *                       (StringUtils.isNotBlank(carCode)) { Car car =
	 *                       carService.queryByCarCodeAndColor(carCode,
	 *                       gpsData.getVehicleColor() + ""); datas.add(car);
	 *                       break; } else { String code =
	 *                       gpsData.getVehicleNo(); if
	 *                       (StringUtils.isNotBlank(code)) { Car car =
	 *                       carService.queryByCarCodeAndColor(code,
	 *                       gpsData.getVehicleColor() + ""); if
	 *                       (!datas.contains(car)) { datas.add(car); } } } } }
	 *                       } } return ResponseResult.ok(datas); } *
	 */

	@RequestMapping(value = "showCarInfo.action", method = RequestMethod.POST)
	public ResponseResult showCarInfo(String[] points, String startTime, String endTime, String carCode) {

		try {
			List<GpsDataLine> listGpsData = getGPS(startTime, endTime, carCode);

			Set<GpsDataLine> gpsDataInLine = new HashSet<GpsDataLine>();
			List<Car> cars = null;

			// 如果传入的点不为空
			if (points != null && listGpsData != null && !listGpsData.isEmpty()) {

				// 创建一个存放多边形各个点的列表
				List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
				// 如果传入的坐标点数组长度不是偶数，说明传入的有可能不是一个坐标
				if (points.length % 2 != 0) {
					logger.error("调用getCarGPSInfoReturn接口时POINT_FORMAT_ERROR异常");
				}
				// 如果长度小于六，则说明小于三个坐标，不能构成一个多边形
				if (points.length < 6) {
					logger.error("调用getCarGPSInfoReturn接口时POINT_SIZE_LESS_THREE异常");
				}
				// 将传入的点放到列表中
				for (int i = 0; i < points.length; i++) {
					// 判断下标是否是偶数
					if (i % 2 == 0) {
						
						// 将绘制的多边形的高德坐标系转成WSG84坐标系
						double lon = Double.parseDouble(points[i]);
						double lat = Double.parseDouble(points[i + 1]);
						
						double[] res = CoordinateConverter.gcj2WGS(lat,lon);
						
						pts.add(new Point2D.Double(res[1],res[0]));
					}
				}

				if (pts.size() > 0) {
					for (GpsDataLine gpsData : listGpsData) {
						if (gpsData.getLatitude() != null && gpsData.getLongitude() != null) {
							Point2D.Double point = new Point2D.Double(((double) gpsData.getLongitude()) / 1000000,
									((double) gpsData.getLatitude()) / 1000000);
							if (PolygonSelectBoxUtil.IsPtInPoly(point, pts)) {
								// 判断传入的车牌号是否为空，不为空则根据车牌号查询车辆
								gpsDataInLine.add(gpsData);
							}
						}
					}

					if (gpsDataInLine.size() > 0) {
						// 当有数据数循环遍拼接sql查询车辆数据
						String sql = "";
						List listgps = new ArrayList<>(gpsDataInLine);
						for (int i = 0; i < listgps.size(); i++) {
							GpsDataLine gpsData = (GpsDataLine) listgps.get(i);
							sql = sql + " select id,car_code,car_card_color,car_color,company_id,company_name,"
									+ "is_lock,is_running,factory_type from t_car " + " where " + "car_code= " + "'"
									+ gpsData.getVehicleNo() + "'" + " and " + "car_card_color= "
									+ gpsData.getVehicleColor() + " union";
						}
						StringBuilder sb = new StringBuilder(sql);
						sb.delete(sb.length() - 5, sb.length());

						cars = carService.queryBySql(sb.toString());
					}
				}
				if (cars == null) {
					cars = Collections.EMPTY_LIST;
				}
				return ResponseResult.ok(cars);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.ok();
	}


	
	private List<GpsDataLine> getGPS(String startTime, String endTime, String carCode) {
		QueryHelper queryHelper = new QueryHelper();
		// 拼接查询条件
		if (StringUtils.isNotBlank(startTime)) {
			queryHelper.addCondition(startTime, "gps_time between '%s'", false);
		}
		if (StringUtils.isNotBlank(endTime)) {
			queryHelper.addCondition(endTime, "'%s'", false);
		}
		if (StringUtils.isNotBlank(carCode)) {
			queryHelper.addCondition(carCode, "vehicle_no = '%s'", false);
		}
		queryHelper.addCondition(30000000, "latitude <= %d", false).addCondition(29000000, "latitude >= %d", false);

		// 查询到设备的列表
		List<GpsDataLine> listGpsData = gpsDataLineService.queryData(queryHelper.getWhereSQL(),
				queryHelper.getWhereParams());
		return listGpsData;
	}
	
	/**
	 * 分片处理查询轨迹数据，分割时间片段为半个小时一个片段
	 * @param points  矩形坐标点
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param carCode 车牌号码
	 * */
	public void queryGPSByL(String[] points, String startTime, String endTime, final String carCode){
		final long startl=System.currentTimeMillis();//开始时间
		logger.info("开始执行时间："+startl);
		final Map<String,Integer> count_thread=new HashMap<>();//线程总数
		count_thread.put("count_thread", 0);//默认为0个线程
		final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate=null;
		 Date endDate=null;
		try {
			startDate = sdf.parse(startTime);
			endDate=sdf.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		final Calendar calendar=Calendar.getInstance();
		calendar.setTime(startDate);
		final StringBuffer addTime=new StringBuffer(startTime);
		final List<GpsDataLine> gpsDataLineList=new ArrayList<>();
		while(calendar.getTime().compareTo(endDate)<0){
			final Date endDatef=endDate;
			Integer count=count_thread.get("count_thread");
			++count;
			count_thread.put("count_thread",count);
			cachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					calendar.add(Calendar.MINUTE, 30);
					//叠加的半个小时大于等于最后截止时间
					if(calendar.getTime().compareTo(endDatef)>=0){
						calendar.setTime(endDatef);//设置最后一次的时间
					}
					String str_startTime=addTime.toString();
					addTime.delete(0, addTime.length()-1);
					addTime.append(sdf.format(calendar.getTime()));//记录下往后移动的时间点
					List<GpsDataLine> list=getGPS(str_startTime,addTime.toString(),carCode);
					gpsDataLineList.addAll(list);
					logger.info(Thread.currentThread().getId()+"："+gpsDataLineList.size());
					Integer countr=count_thread.get("count_thread");
					--countr;
					count_thread.put("count_thread",countr);
					if(count_thread.get("count_thread")==0){
						logger.info("线程执行结束");
						logger.info("数据一共是："+gpsDataLineList.size());
						long endl=System.currentTimeMillis();//开始时间
						logger.info("结束执行时间："+endl);
						logger.info("总共执行时间："+(endl-startl));
					}
				}
			});
			
			
		}
	}

	public static void main(String[] args) {
		Point2D.Double n1 = new Point2D.Double(114.303217, 30.583553);
		Point2D.Double n2 = new Point2D.Double(114.307336,30.597592);  
		Point2D.Double n3 = new Point2D.Double(114.286565,30.590056);  
		Point2D.Double y1 = new Point2D.Double(114.227342,30.587987);  
		Point2D.Double y2 = new Point2D.Double(120.1866 , 30.2672);  
		Point2D.Double y4 = new Point2D.Double(120.1869 , 30.2718);  
		
		List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
		pts.add(new Point2D.Double(114.309914,30.599556));
		pts.add(new Point2D.Double(114.295688,30.592879));
		pts.add(new Point2D.Double(114.292812,30.587726)); 
		pts.add(new Point2D.Double(114.292812,30.587726));
		pts.add(new Point2D.Double(114.30058,30.580318));
		pts.add(new Point2D.Double(114.303606,30.586959));
		pts.add(new Point2D.Double(114.304534,30.594751));
		pts.add(new Point2D.Double(114.30838,30.590131));
		pts.add(new Point2D.Double(114.308651,30.584182));
        pts.add(new Point2D.Double(114.304495,30.584015));
        pts.add(new Point2D.Double(114.301301,30.578759));
        pts.add(new Point2D.Double(114.309437,30.578528));
        pts.add(new Point2D.Double(114.323282,30.592786));
        
        System.out.println(pts.toString());
		
		boolean res1 = PolygonSelectBoxUtil.IsPtInPoly(n1, pts);
		boolean res2 = PolygonSelectBoxUtil.IsPtInPoly(n2, pts);
		boolean res3 = PolygonSelectBoxUtil.IsPtInPoly(n3, pts);
		boolean res4 = PolygonSelectBoxUtil.IsPtInPoly(y1, pts);
		boolean res5 = PolygonSelectBoxUtil.IsPtInPoly(y2, pts);
		boolean res6 = PolygonSelectBoxUtil.IsPtInPoly(y4, pts);
		
		System.out.println(res1);
		System.out.println(res2);
		System.out.println(res3);
		System.out.println(res4);
		System.out.println(res5);
		System.out.println(res6);
		// Point n2 = new Point(114.307336,30.597592);
		// Point n3 = new Point(114.286565,30.590056);
		// Point y1 = new Point(114.227342,30.587987);
		// Point y2 = new Point(120.1866 , 30.2672);
		// Point y4 = new Point(120.1869 , 30.2718);
	}

}
