package com.muck.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/*import redis.clients.jedis.Jedis;
//@Component
public class JedisUtils {
	
	private static MJedisUtil mJedisUtil;
	
	@Autowired
	public void setmJedisUtil(MJedisUtil mJedisUtil) {
		JedisUtils.mJedisUtil = mJedisUtil;
	}

	*//**
	 * 判断某一个键是否存在，不用考虑是通过字节还是字符串方式存储
	 * @param str 键值
	 * @return 如果存在则返回true,否则返回false
	 * *//*
	public static boolean exists(String str) {
		 Jedis jedis = null;
		 
		 try {
			jedis =  mJedisUtil.getJedis();
			if(str!=null&&!str.isEmpty()) {
				return jedis.exists(str)||jedis.exists(str.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
		return false;
	}
	
	
	public static String setStr(String key,int expire,String value){
		 Jedis jedis = null;
		 try {
			jedis = mJedisUtil.getJedis();
			String code=jedis.setex(key, expire, value);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
		 return null;
	}
	public static String setStr(String key,String value){
		 Jedis jedis = null;
		 try {
			jedis = mJedisUtil.getJedis();
			 String code=jedis.set(key, value);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
		 return null;
	}
	
	public static String getStr(String key){
		 Jedis jedis = null;
		 try {
			jedis = mJedisUtil.getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
		 return null;
	}
	
	public static void setObject(String key,Object object){
		 Jedis jedis = null;
		try {
			jedis = mJedisUtil.getJedis();
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(bos);
			oos.writeObject(object);
			jedis.set(key.getBytes(), bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
	}
	public static Object getObject(String key){
		 Jedis jedis = null;
		 try {
			jedis = mJedisUtil.getJedis();
			byte[] bt=jedis.get(key.getBytes());
			if(bt!=null){
				ByteArrayInputStream bis=new ByteArrayInputStream(bt);
				ObjectInputStream ois;
					ois = new ObjectInputStream(bis);
					Object object= ois.readObject();
					ois.close();
					return object;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
		return null;
	}
	public static void del(String key){
		 Jedis jedis = null;
		 try {
			jedis = mJedisUtil.getJedis();
			 jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
	}
	public static void setList(String key,String...strings){
		 Jedis jedis = null;
		 try {
			jedis = mJedisUtil.getJedis();
			jedis.rpush(key, strings);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
		
	}
	
	public static List<String> getList(String key,long start,long end){
		 Jedis jedis = null;
		 try {
			jedis = mJedisUtil.getJedis();
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
		return null;
		
	}
	
	*//**
	 * @Describe 往订阅的通道里面发布一则消息
	 * @author 黄福寿
	 * @param channel 订阅通道
	 * @param pubStr 往通道里面发送消息
	 * *//*
	public static void publish(String channel, String pubStr) {
		 Jedis jedis = null;
		 try {
			jedis = mJedisUtil.getJedis();
			jedis.publish(channel, pubStr);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mJedisUtil.release(jedis);
		}
	}
}*/
