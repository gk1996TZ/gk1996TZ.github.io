package com.muck.jt809;

import java.awt.geom.Point2D;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.muck.domain.Car;
import com.muck.domain.GpsData;
import com.muck.service.CarService;
import com.muck.service.ElectricFenceCarService;
import com.muck.service.GpsDataService;
import com.muck.service.car.EFPool;
import com.muck.socket.ChannelHanderMap;
import com.muck.utils.PolygonSelectBoxUtil;
import com.muck.utils.map.CoordinateConverter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class MyJT809BusiHander extends SimpleChannelInboundHandler<Message> {

	Logger logger = LoggerFactory.getLogger(getClass());

	// 静态的上下文对象
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		MyJT809BusiHander.applicationContext = applicationContext;
	}

	// 标志变量,表示是否已经初始化了bean对象,默认为false
	private boolean isInitBean;
	private CarService carService;
	private GpsDataService gpsDataService; // 车辆
	private ElectricFenceCarService electricFenceCarService; // 轨迹

	java.util.concurrent.ExecutorService executorService = Executors.newFixedThreadPool(150);

	public void initBean() {
		if (!isInitBean) {
			synchronized (MyJT809BusiHander.class) {
				if (!isInitBean) {
					this.carService = applicationContext.getBean(CarService.class);
					this.gpsDataService = applicationContext.getBean(GpsDataService.class);
					this.electricFenceCarService = applicationContext.getBean(ElectricFenceCarService.class);
					isInitBean = true;
				}
			}
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
		try {
			logger.info("msgID--->" + message.getMsgId());
			switch (message.getMsgId()) {
			case JT809BusinessType.UP_CONNECT_REQ:
				//logger.info("登录 msgID:" + message.getMsgId());
				//logger.info("登录");
				loginAndRes(ctx, message);
				break;
			case JT809BusinessType.UP_DISCONNECT_REQ:
				//logger.info("断开 msgID:" + message.getMsgId());
				break;
			case JT809BusinessType.UP_LINKTEST_REQ:
				//logger.info("保持连接msgID:" + message.getMsgId());
				//logger.info("保持心跳");
				keepConnection(ctx, message);// 保持心跳
				break;
			case JT809BusinessType.UP_EXG_MSG:
				//logger.info("交换车辆定位信息msgID:" + message.getMsgId());
				connection_gps(ctx, message);
				break;
			case JT809BusinessType.UP_EXG_MSG_REAL_LOCATION:
				//logger.info("实时上传车辆定位信息msgID:" + message.getMsgId());
				break;
			case JT809BusinessType.UP_EXG_MSG_HISTORY_LOCATION:
				//logger.info("车辆定位信息自动补报msgID:" + message.getMsgId());
				break;
			case JT809BusinessType.UP_EXG_MSG_REGISTER:
				//logger.info("上传车辆注册msgID:" + message.getMsgId());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(message);
		}
	}

	private void loginAndRes(ChannelHandlerContext ctx, Message msg) {
		ByteBuf byteBuf = msg.getMsgBody();
		long userid = byteBuf.readUnsignedInt();// 用户登录id
		byte[] by = new byte[8];
		byteBuf.readBytes(by, 0, 8);
		String password = new String(by, Charset.forName("GBK"));
		byteBuf.release();
		msg.setMsgLength(JT809BusinessType.UP_CONNECT_RSP_LENGTH);
		msg.setMsgId(JT809BusinessType.UP_CONNECT_RSP);// 登录应答
		// ByteBuf msgBody=Unpooled.buffer(5);
		ByteBuf msgBody = Unpooled.buffer(5);
		if (msg.getMsgGnsscenterid() != JT809User.MSG_GNSSCENTERID) {// 接入码不相等
			msgBody.writeByte(JT809ResultType.CONNECTION_GNSS_INCORRECT);
		} else if (userid != JT809User.USERID) {// 登录id不正确
			msgBody.writeByte(JT809ResultType.CONNECTION_USERID_NOT_REGISTER);
		} /*
			 * else if(!JT809User.DOWN_LINK_IP.equals(ip)){//ip不正确
			 * msgBody.writeByte(JT809ResultType.CONNECTION_IP_INCORRECT); }
			 */else if (!JT809User.PASSWORD.equals(password)) {// 密码不正确
			msgBody.writeByte(JT809ResultType.CONNECTION_PASSWORD_INCORRECT);
		} else {
			msgBody.writeByte(JT809ResultType.CONNECTION_SUCCESS);// 登录成功
		}

		msgBody.writeInt(msg.getCrcCode());// 校验码
		msg.setMsgBody(msgBody);
		ByteBuf bb = Unpooled.directBuffer(27);
		// 数据头
		bb.writeInt(msgBody.capacity() + 26);
		bb.writeInt(msg.getMsgSN());
		bb.writeShort(msg.getMsgId());
		bb.writeInt(msg.getMsgGnsscenterid());
		bb.writeBytes(msg.getVersionFlag());
		bb.writeByte(msg.getEncryptFlag());
		bb.writeInt(msg.getEncryptKey());

		// 数据体
		bb.writeBytes(msgBody.array());
		byte[] bt = new byte[5 + 22];
		bb.getBytes(0, bt);
		// System.out.println("应答校验码：" + CRC16CCITT.crc16(bt));
		msg.setCrc16code(CRC16CCITT.crc16(bt));
		// bb.release();
		ctx.write(msg);
		ctx.flush();
	}

	/** 保持连接应答 */
	private void keepConnection(ChannelHandlerContext ctx, Message msg) {
		msg.setMsgBody(null);
		msg.setMsgLength(JT809BusinessType.UP_LINKTEST_RSP_LENGTH);
		msg.setMsgId(JT809BusinessType.UP_LINKTEST_RSP);

		ctx.write(msg);
		ctx.flush();
	}

	/**
	 * 实时获取车辆定位数据
	 */
	private void connection_gps(ChannelHandlerContext ctx, Message msg) {

		//logger.info("----------begin--------------");

		try {
			ByteBuf body = msg.getMsgBody();
			//logger.info("开始获取车辆定位数据");
			if (body.readableBytes() > 0) {
				//logger.info("进入获取车辆定位数据");
				Charset charset = Charset.forName("gbk");
				JT809Car car = new JT809Car();
				byte[] rb = new byte[21];
				body.readBytes(rb, 0, 21);
				String str = new String(rb, charset);
				car.setVehicle_no(str.trim());
				car.setVehicle_color(body.readByte());
				car.setData_type(body.readShort());
				car.setData_length(body.readInt());
				//logger.info("进入获取车辆定位数据car:" + car);
				//logger.info("子业务类型id:" + car.getData_type() + ";实时上传id:" + JT809BusinessType.UP_EXG_MSG_REAL_LOCATION);
				// buffer.release(); // 关闭
				if (car.getData_type() == JT809BusinessType.UP_EXG_MSG_REAL_LOCATION) {
					initBean();
					readCarData(body, car);
				}
			}
		} catch (Exception e) {
			logger.info("----------GPS Car push Exception-----------------");
			logger.error("gps异常", e.getMessage());
		} finally {
			ReferenceCountUtil.release(msg);
		}

		//logger.info("----------end--------------");
	}

	private void readCarData(ByteBuf body, JT809Car car) {
		final GpsData gpsData = realCar(car, body);
		// 更新数据库状态
		GpsData rcar = JT809Map.getByNo(gpsData.getVehicleNo());
		if (rcar == null) {
			Car cars = carService.queryByCarCode(gpsData.getVehicleNo());
			if (cars != null) {
				gpsData.setCompanyId(cars.getCompanyId());
				// gpsData.setCarGroupId(cars.getCarGroupId());
				gpsData.setCompanyName(cars.getCompanyName().trim());
			} else {
				//logger.error("车辆的企业没有查询到：" + gpsData.getVehicleNo());
			}
		} else {
			gpsData.setCompanyId(rcar.getCompanyId());
			gpsData.setCompanyName(rcar.getCompanyName());
		}

		JSONObject result = new JSONObject();
		GpsData cGpsData = JT809Map.getByNo(gpsData.getVehicleNo());
		if (cGpsData == null) {
			result.put("isChange", true);
		} else if (cGpsData.getLongitude() != gpsData.getLongitude()
				|| cGpsData.getLatitude() != gpsData.getLatitude()) {
			result.put("isChange", true);

		} else if (gpsData.getStatus() == 0) {
			result.put("isChange", true);
		} else {
			result.put("isChange", false);
		}

		boolean flag = JT809Map.addOrRemoveCars(gpsData);
		result.put("gpsData", gpsData);
		if (flag) {
			try {
				result.put("companyId", gpsData.getCompanyId());
				result.put("companyName", JT809Map.getCompanyNameById(gpsData.getCompanyId()).trim());
				result.put("companyCount", JT809Map.getCountById(gpsData.getCompanyId(), (byte) 0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String contentResult = JSONObject.toJSONString(result);

			//logger.info("-------------长宝推送数据开始啦Begin----------");

			ChannelHanderMap.sendMessage(contentResult);

			//logger.info("-------------长宝推送数据结束啦End----------");

		}
		// body.release();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				gpsDataService.save(gpsData);
			}
		});
		
		System.out.println("----- electric car begin--------");
		
		try {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					String vehicleNo = gpsData.getVehicleNo();
					Long latitude = gpsData.getLatitude();
					Long longitude = gpsData.getLongitude();
					if(vehicleNo != null && !"".equals(vehicleNo.trim()) && latitude != null && longitude != null){
						List<Map<String,Object>> electricFenceCars =  electricFenceCarService.queryElectricFenceCarByCarCode(vehicleNo.trim());
						if(electricFenceCars != null && !electricFenceCars.isEmpty()){
							for(Map<String,Object> map : electricFenceCars){
								Object electricFenceCoordinateObj = map.get("electricFenceCoordinate");
								if(electricFenceCoordinateObj != null){
									String electricFenceCoordinate = electricFenceCoordinateObj.toString();
									// 去掉所有的中括号
									if (!StringUtils.isBlank(electricFenceCoordinate)) {
										electricFenceCoordinate = electricFenceCoordinate.replaceAll("\\[", "");
										electricFenceCoordinate = electricFenceCoordinate.replaceAll("\\]", "");
									}
									String[] ps = electricFenceCoordinate.split(",");
									// 创建一个存放多边形各个点的列表
									List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
									// 将传入的点放到列表中
									for (int i = 0; i < ps.length; i++) {
										// 判断下标是否是偶数
										if (i % 2 == 0) {
											pts.add(new Point2D.Double(Double.parseDouble(ps[i]),Double.parseDouble(ps[i + 1])));
										}
									}
									double latDouble = ((double) latitude) / 1000000;
									double lonDouble = ((double) longitude) / 1000000;
									double[] lonlat = CoordinateConverter.wgs2GCJ(latDouble, lonDouble);
									
									// lonlat[0] = lng , lonlat[1] = lat
									// 创建当前获取到的车辆的经纬度点
									Point2D.Double point = new Point2D.Double(lonlat[0],lonlat[1]);
									if (!PolygonSelectBoxUtil.IsPtInPoly(point, pts)) {
										// 表示不在电子围栏中,则产生告警信息
										Object electricId = map.get("id");
										Object electricFenceName = map.get("electricFenceName");
										
										JSONObject result = new JSONObject();
										result.put("electricId", electricId);  // 电子围栏的唯一标识,id
										result.put("electricFenceName", electricFenceName); // 电子围栏的名称
										result.put("carCode", gpsData.getVehicleNo()) ; // 车牌号
										result.put("datetime", gpsData.getGpsTime()); // 车辆跑出去的时间
										result.put("carCardColor", gpsData.getVehicleColor()); // 车辆颜色
										
										System.out.println("----- electric car run out --------");
										
										EFPool.sendMessageToAll(result.toJSONString());
									}
								}
							}
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 表示运行
		Byte carIsRunning = gpsData.getStatus();
		Long gpsTimeStamp = gpsData.getGpsTime().getTime();
		Long currentTimeStamp = new Date().getTime();
		if (carIsRunning != null) {
			// 更改数据库中的状态
			Car car1 = new Car();
			car1.setCarCode(gpsData.getVehicleNo());
			if ((carIsRunning == 1) && (currentTimeStamp - gpsTimeStamp <= 1000 * 60 * 10)) {
				car1.setRunning(true);
			} else {
				car1.setRunning(false);
			}
			car1.setUpdatedTime(new Date());
			car1.setCarCardColor(gpsData.getVehicleColor() + "");
			carService.editByCarCode(car1);
			//logger.info("修改了数据中的车辆状态和车牌颜色：" + car);
		}
	}

	/**
	 * 实时上传车辆数据
	 */
	private GpsData realCar(JT809Car car, ByteBuf body) {
		car.setEncrypt(body.readByte());
		// ByteBuf bb = body.readBytes(4).copy();
		byte[] bt = new byte[4];
		// bb.getBytes(0, bt);
		body.readBytes(bt, 0, bt.length);
		car.setDate(bt);
		ByteBuf bb = Unpooled.buffer(4);
		bb.writeBytes(bt);
		byte day = bb.readByte();// d 日
		byte month = bb.readByte();// m 月
		short year = bb.readShort();// yy 年
		bb.release();
		// ByteBuf bbf = body.readBytes(3).copy();
		byte[] btf = new byte[3];
		body.readBytes(btf, 0, 3);
		// bbf.getBytes(0, btf);
		byte hour = btf[0];
		byte minute = btf[1];
		byte seconds = btf[2];
		// bbf.release();
		car.setTime(btf);
		Integer x1 = body.readInt();
		Integer x2 = body.readInt();
		// System.out.println(" x1 ==== > " + x1 + "\n" + " x2 ==== > " + x2);
		car.setLon(x1);
		car.setLat(x2);
		car.setVec1(body.readShort());
		car.setVec2(body.readShort());
		car.setVec3(body.readInt());
		car.setDirection(body.readShort());
		car.setAltitude(body.readShort());
		car.setState(body.readInt());
		car.setAlarm(body.readInt());
		GpsData gpsData = new GpsData();

		// dmyy 日月年
		/**
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();// 默认为当前时间
		try {
			date = sdf.parse(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + seconds);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/

		// hms 时分秒
		gpsData.setGpsTime(new Date());
		gpsData.setDirection(car.getDirection() + "度");
		gpsData.setVehicleColor((int) car.getVehicle_color());
		gpsData.setLatitude((long) car.getLat());
		gpsData.setLongitude((long) car.getLon());

		double latDouble = ((double) gpsData.getLatitude()) / 1000000;
		double lonDouble = ((double) gpsData.getLongitude()) / 1000000;
		double[] lonlat = CoordinateConverter.wgs2GCJ(latDouble, lonDouble);
		gpsData.setLnglat(lonlat);

		Short vec1 = car.getVec1();
		Short vec2 = car.getVec2();
		if (vec1 != null && vec1 > 0) {
			gpsData.setSpeed((double) vec1);
		} else if (vec2 != null && vec2 > 0) {
			gpsData.setSpeed((double) vec2);
		} else {
			gpsData.setSpeed((double) 0);
		}
		gpsData.setMileage(car.getVec3() + "");
		gpsData.setVehicleNo(car.getVehicle_no().trim());
		gpsData.setDeleted(true);
		String str = Integer.toBinaryString(car.getState());
		StringBuffer sb = new StringBuffer(str);
		if (str.length() < 32) {
			for (int i = 0; i < 32 - str.length(); i++) {
				sb.insert(0, '0');
			}
		}
		if (Byte.parseByte(sb.charAt(4) + "") == 1) {// 二进制状态位 1表示停止，0.表示运行
			gpsData.setStatus((byte) 0);
		} else {
			gpsData.setStatus((byte) 1);
		}
		body.release();
		return gpsData;
	}
}
