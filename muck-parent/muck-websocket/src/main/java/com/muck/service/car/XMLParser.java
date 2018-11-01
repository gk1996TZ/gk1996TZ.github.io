package com.muck.service.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.muck.domain.GpsData;
import com.muck.utils.CollectionUtils;

/**
 * 	xml解析
*/
public final class XMLParser {
	
	private XMLParser(){
		
	}

	/***
	 * @param xml格式的字符串
	*/
	@SuppressWarnings("unchecked")
	public static List<GpsData> parseXML(String xml) throws Exception{
		
		List<GpsData> result = new ArrayList<GpsData>();
		
		if(StringUtils.isBlank(xml)){
			return Collections.EMPTY_LIST;
		}
		
		Document document = DocumentHelper.parseText(xml);
		
		Element root = document.getRootElement();
		
		String content = root.getTextTrim();

		List<GpsData> datas = JSONArray.parseArray(content,GpsData.class);
		if(datas != null && datas.size() > 0){
			for(GpsData data : datas){
				if(data.getCode() != -1 && data.getCode() == 1){
					result.add(data);
				}
			}
		}
		return CollectionUtils.toList(result);
	}
}

