package com.litang.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import redis.clients.jedis.Jedis;
@Component
public class JedisUtils {
	private  static Jedis jedis;
	/*@Autowired
	private static MJedisUtil mJedisUtil;*/
	/*@Autowired
	public JedisUtils(MJedisUtil mJedisUtil){
		JedisUtils.jedis = mJedisUtil.getJedis();
		JedisUtils.jedis.configSet("timeout",60*30+"");
	}*/
	
	/**
	 * 判断某一个键是否存在，不用考虑是通过字节还是字符串方式存储
	 * @param str 键值
	 * @return 如果存在则返回true,否则返回false
	 * */
	public static boolean exists(String str) {
		if(str!=null&&!str.isEmpty()) {
			return jedis.exists(str)||jedis.exists(str.getBytes());
		}
		return false;
	}
	
	
	public static String setStr(String key,int expire,String value){
		String code=jedis.setex(key, expire, value);
		return code;
	}
	public static String setStr(String key,String value){
		String code=jedis.set(key, value);
		return code;
	}
	
	public static String getStr(String key){
		return jedis.get(key);
	}
	
	public static void setObject(String key,Object object){
		if(!StringUtils.isBlank(key)){
			synchronized (jedis) {
				try {
					ByteArrayOutputStream bos=new ByteArrayOutputStream();
					ObjectOutputStream oos=new ObjectOutputStream(bos);
					oos.writeObject(object);
					jedis.set(key.getBytes(), bos.toByteArray());
					//设置有效时间默认为7天
					jedis.expire(key.getBytes(), 60*60*24*7);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static Object getObject(String key){
		if(!StringUtils.isBlank(key)){
			synchronized (jedis) {
				byte[] bt=jedis.get(key.getBytes());
				if(bt!=null){
					ByteArrayInputStream bis=new ByteArrayInputStream(bt);
					ObjectInputStream ois;
					try {
						ois = new ObjectInputStream(bis);
						Object object= ois.readObject();
						ois.close();
						return object;
					} catch(EOFException e){
					}catch (IOException e) {
					} catch (ClassNotFoundException e) {
					}
				}
			}
		}
		return null;
	}
	/**
	 * 删除key
	 * @param key 传入需要被删除的key
	 * @return 返回受影响的条数
	 */
	public static Long del(String key){
		if(StringUtils.isBlank(key)){
			return -1l;
		}
		return jedis.del(key);
	}
	public static void setList(String key,String...strings){
		jedis.rpush(key, strings);
	}
	
	public static List<String> getList(String key,long start,long end){
		return jedis.lrange(key, start, end);
	}
	
	/**
	 * 向队列的左边入队一个值
	 * @param key 传入一个key
	 * @param obj 传入需要入队的值
	 */
	public static void lpush(String key,Object obj){
		try {
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(bos);
			oos.writeObject(obj);
			jedis.lpush(key.getBytes(),bos.toByteArray());
		} catch (IOException e) {
		}
	}
	/**
	 * 将队列的右边出队一个值
	 * @param key 传入一个key
	 * @return 返回删除的
	 */
	public static Object rpop(String key){
		try {
			byte [] bt = jedis.rpop(key.getBytes());
			ByteArrayInputStream bos=new ByteArrayInputStream(bt,0,bt.length);
			ObjectInputStream oos=new ObjectInputStream(bos);
			//将出队的对象返回
			return oos.readObject();
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		//如果出队失败，则返回空
		return null;
	}
	/**
	 * 清除redis所有的数据
	 * @return 返回是否清除成功，成功为OK
	 */
	public static String flushDB(){
		return jedis.flushDB();
	}
	/**
	 * 获取redis所有的key值
	 * @return 返回redis所有的key值
	 */
	public static List<String> keys(){
		List<String> list = new ArrayList<String>(jedis.keys("*"));
		Collections.sort(list, new Comparator<String>() {
            @Override  
            public int compare(String arg0, String arg1) {  
                String str1=(String) arg0;  
                String str2=(String) arg1;  
                if (str1.compareToIgnoreCase(str2)<0){    
                    return -1;    
                }    
                return 1;    
            }  
        });
		return list;
	}
}
