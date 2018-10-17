package com.xumengba.mapper.provider;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xumengba.annotation.Table;
import com.xumengba.handler.IdTypeHandler;
public class UpdateMapperProvider {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 修改的sql
	 * @param obj 传入一个需要被修改的对象
	 * @return 返回一个sql
	 */
	public String updateById(final Object obj){
		// 得到类对象
		final Class<Object> stuCla = (Class<Object>) obj.getClass();
		//得到属性集合
		final Field[] fs = stuCla.getDeclaredFields();
		return new SQL(){
			{
				Table table = stuCla.getAnnotation(Table.class);
				String tableName = table.name();
				if(tableName == null | "".equals(tableName)){
					throw new RuntimeException("实体类"+stuCla.getSimpleName()+"上缺少table注解");
				}
				UPDATE(tableName);
				 try {
					//遍历属性
					for (Field f : fs) {
						// 设置属性是可以访问的(私有的也可以)
				        f.setAccessible(true);
				       // 得到此属性的值
				        Object val = f.get(obj);
				        //如果属性值不为空执行的操作
				        if(val!=null && !"".equals(val.toString())&&!"serialVersionUID".equals(f.getName())) {
				        	String type = f.getGenericType().toString();
				        	com.xumengba.annotation.Field fieldName = f.getAnnotation(com.xumengba.annotation.Field.class);
				        	if(fieldName == null){
				        		throw new RuntimeException("执行修改操作时需要被修改的字段缺少数据库字段注解");
				        	}
				        	String name = fieldName.name();
				        	if(name == null | "".equals(name)){
				        		throw new RuntimeException("执行修改操作时需要被修改的字段的字段注解name属性值为空");
				        	}
				        	//如果添加的时间类型，则转化一下时间格式
				        	if("date".equalsIgnoreCase(type.substring(type.lastIndexOf(".")+1, type.length()))){
				        		Date date = (Date)val;
				        		SET(name+"='"+sdf.format(date)+"'");
				        	} else if ("boolean".equalsIgnoreCase(type.substring(type.lastIndexOf(".") + 1, type.length()))){
				        		if((boolean)val){
				        			SET(name+"=1");
								}else {
									SET(name+"=0");
								}
				        	}else if(f.getName().toLowerCase().contains("id")){
				        		SET(name+"='"+IdTypeHandler.decode(val.toString())+"'");
				        	}else {
				        		SET(name+"='"+val.toString()+"'");
				        	}
				        }
					}
					try {
						Field fieldId = stuCla.getDeclaredField("id");
						fieldId.setAccessible(true);
						WHERE(" id= '"+IdTypeHandler.decode(fieldId.get(obj).toString())+"'");
					} catch (Exception e) {
						logger.error("修改数据时id为空");
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}.toString();
	}
	
	public String update(final Object obj){
		return new SQL(){
			{
				// 得到类对象
				final Class<Object> stuCla = (Class<Object>) obj.getClass();
				//得到属性集合
				final Field[] fs = stuCla.getDeclaredFields();
				
				Table table = stuCla.getAnnotation(Table.class);
				String tableName = table.name();
				if(tableName == null | "".equals(tableName)){
					throw new RuntimeException("实体类"+stuCla.getSimpleName()+"上缺少table注解");
				}
				UPDATE(tableName);
				try {
					//遍历属性
					for (Field f : fs) {
						// 设置属性是可以访问的(私有的也可以)
				        f.setAccessible(true);
				       // 得到此属性的值
				        Object val = f.get(obj);
				        //如果属性值不为空执行的操作
				        if(val!=null && !"".equals(val.toString()) &&  !"serialVersionUID".equals(f.getName())) {
				        	String type = f.getGenericType().toString();
				        	com.xumengba.annotation.Field fieldName = f.getAnnotation(com.xumengba.annotation.Field.class);
				        	if(fieldName == null){
				        		throw new RuntimeException("执行修改操作时需要被修改的字段缺少数据库字段注解");
				        	}
				        	String name = fieldName.name();
				        	if(name == null | "".equals(name)){
				        		throw new RuntimeException("执行修改操作时需要被修改的字段的字段注解name属性值为空");
				        	}
				        	//如果添加的时间类型，则转化一下时间格式
				        	if("date".equalsIgnoreCase(type.substring(type.lastIndexOf(".")+1, type.length()))){
				        		Date date = (Date)val;
				        		SET(name+"='"+sdf.format(date)+"'");
				        	} else if ("boolean".equalsIgnoreCase(type.substring(type.lastIndexOf(".") + 1, type.length()))){
				        		if((boolean)val){
				        			SET(name+"=1");
								}else {
									SET(name+"=0");
								}
				        	}else if(f.getName().toLowerCase().contains("id")){
				        		SET(name+"='"+IdTypeHandler.decode(val.toString())+"'");
				        	}else {
				        		SET(name+"='"+val.toString()+"'");
				        	}
				        }
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}.toString();
	}
}
