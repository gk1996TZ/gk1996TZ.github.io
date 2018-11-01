package com.muck.utils;


import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 	http请求工具类
*/
public final class HttpClientUtil {
	static Logger logger=LoggerFactory.getLogger("HttpClientUtil");
	/**
	 * 	GET请求
	 * 		@param url 请求url地址
	 * 		@param param 请求的参数
	 */
	public static String doGet(String url, Map<String, String> param) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null && !param.isEmpty()) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
				
			HttpGet httpGet = new HttpGet(uri);

			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("北斗抓取数据错误", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	public static void main(String[] args) {
		
		JSONObject object = new JSONObject();
		object.put("UserKey", "14926");
		
		JSONArray array = new JSONArray();
		
		JSONObject car2 = new JSONObject();
		car2.put("VehicleColor", "2");
		car2.put("VehicleNO", "浙G62139");
		
//		JSONObject car3 = new JSONObject();
//		car3.put("VehicleColor", "1");
//		car3.put("VehicleNO", "浙G22633");
		
		array.add(car2);
//		array.add(car3);
		
		object.put("VehicleList", array);
		
        Map<String, String> map = new HashMap<>();
		String json = object.toJSONString();
		
		map.put("reqParam", json);

		String url = "http://211.139.216.188:38244/JRGPSInterface.asmx/GetVehicleLocationInfo";
		
		String content = doGet(url, map);
		
		System.out.println(content);
	}
}