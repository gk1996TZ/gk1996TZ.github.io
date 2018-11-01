package com.muck.jt809;

import java.util.Arrays;

public class JT809Car {
	private String vehicle_no;//车牌号
	private byte vehicle_color;//车牌颜色
	private  short data_type;//子业务类型标识
	private int data_length;//后续数据长度
	private byte encrypt;//加密标识 1.已加密 0.未加密
	private byte[] date;//日月年
	private byte[] time;//时分秒
	private int lon;//经度 
	private int lat;//纬度
	private short vec1;//速度 km/h
	private short vec2;//行车记录仪车速 km/h
	private int vec3;//车辆当前总里程数 km
	private short direction;//方向 0~359 单位是度  正北为0 顺时针
	private short altitude;//海拔高度 单位为m
	private int state ;//车辆状态
	private int alarm;//报警状态
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public byte getVehicle_color() {
		return vehicle_color;
	}
	public void setVehicle_color(byte vehicle_color) {
		this.vehicle_color = vehicle_color;
	}
	public short getData_type() {
		return data_type;
	}
	public void setData_type(short data_type) {
		this.data_type = data_type;
	}
	public int getData_length() {
		return data_length;
	}
	public void setData_length(int data_length) {
		this.data_length = data_length;
	}
	public byte getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(byte encrypt) {
		this.encrypt = encrypt;
	}
	public byte[] getDate() {
		return date;
	}
	public void setDate(byte[] date) {
		this.date = date;
	}
	public byte[] getTime() {
		return time;
	}
	public void setTime(byte[] time) {
		this.time = time;
	}
	public int getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public short getVec1() {
		return vec1;
	}
	public void setVec1(short vec1) {
		this.vec1 = vec1;
	}
	public short getVec2() {
		return vec2;
	}
	public void setVec2(short vec2) {
		this.vec2 = vec2;
	}
	public int getVec3() {
		return vec3;
	}
	public void setVec3(int vec3) {
		this.vec3 = vec3;
	}
	public short getDirection() {
		return direction;
	}
	public void setDirection(short direction) {
		this.direction = direction;
	}
	public short getAltitude() {
		return altitude;
	}
	public void setAltitude(short altitude) {
		this.altitude = altitude;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getAlarm() {
		return alarm;
	}
	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}
	@Override
	public String toString() {
		return "JT809Car [vehicle_no=" + vehicle_no + ", vehicle_color=" + vehicle_color + ", data_type=" + data_type
				+ ", data_length=" + data_length + ", encrypt=" + encrypt + ", date=" + Arrays.toString(date)
				+ ", time=" + Arrays.toString(time) + ", lon=" + lon + ", lat=" + lat + ", vec1=" + vec1 + ", vec2="
				+ vec2 + ", vec3=" + vec3 + ", direction=" + direction + ", altitude=" + altitude + ", state=" + state
				+ ", alarm=" + alarm + "]";
	}
	
	
}
