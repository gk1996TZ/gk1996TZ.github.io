package com.muck.utils;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
* @Description: Json工具类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月17日 下午6:19:29
 */
public final class JsonUtils {

	private JsonUtils(){}
	
	public static String objectToJson(Object object, PropertyNamingStrategy strategy) {
		if (null == object) {
			return "";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			// 过滤null
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(fmt);
			mapper.setSerializationInclusion(Include.NON_NULL);
			if(strategy != null) {
				mapper.setPropertyNamingStrategy(strategy);
			}
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException("参数转化失败,请重试!");
		}
	}
	
	/**
	 * jsonToBean
	 * @param jsonString
	 * @param clazz 对象类型
	 * @param genericClazz 泛型类型
	 * @param strategy 属性命名策略
	 * @return
	 */
	public static <T> T jsonToBean(String jsonString, Class<T> clazz, Class<?> genericClazz, PropertyNamingStrategy strategy) {
		if(StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat fmt = null;
			try {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} catch (Exception e) {
				e.printStackTrace();
				fmt = new SimpleDateFormat("yyyy-MM-dd");
			}
			mapper.setDateFormat(fmt);
			if(strategy != null) {
				mapper.setPropertyNamingStrategy(strategy);
			}
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			if(genericClazz == null) { 
				return mapper.readValue(jsonString, clazz);
			} else {
				return mapper.readValue(jsonString, getJavaType(mapper, clazz,null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("参数转化失败,请确定参数格式!");
		}
	}
	
	/**
	 * jsonToBean
	 * @param jsonString
	 * @param clazz 对象类型
	 * @param strategy 属性命名策略
	 * @return
	 */
	public static <T> T jsonToBean(String jsonString, Class<T> clazz, PropertyNamingStrategy strategy) {
		return jsonToBean(jsonString, clazz, null, strategy);
	}
	
	/**
	 * 获取对象泛型
	 * @param mapper
	 * @param clazz
	 * @param genericClazz
	 * @return
	 */
	private static JavaType getJavaType(ObjectMapper mapper, Class<?> clazz, Class<?> genericClazz) {
		return mapper.getTypeFactory().constructParametricType(clazz, genericClazz);
	}
	
	
	/**
	 * jsonToList
	 * @param jsonString
	 * @param clazz 对象类型
	 * @param genericClazz 泛型类型
	 * @param strategy 属性命名策略
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonToList(String jsonListData, Class<T> clazz,PropertyNamingStrategy strategy) {
		if (StringUtils.isEmpty(jsonListData)) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(fmt);
			if(strategy != null) {
				mapper.setPropertyNamingStrategy(strategy);
			}
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
			List<T> list = (List<T>)mapper.readValue(jsonListData, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("参数转化失败,请确定参数格式!");
		}
	}
}