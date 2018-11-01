package com.muck.service.impl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.annotation.ExcelTemplate;
import com.muck.domain.Area;
import com.muck.domain.SiteProjectInfo;
import com.muck.domain.SnapshotImage;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.SiteProjectInfoMapper;
import com.muck.mapper.SnapshotImageMapper;
import com.muck.page.PageView;
import com.muck.response.SnapshotImageRecord;
import com.muck.response.StatusCode;
import com.muck.service.SnapshotImageService;

/**
 * @Description: 视频抓拍图片service实现
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月12日 下午5:03:10
 */
@Service
public class SnapshotImageServiceImpl extends BaseServiceImpl<SnapshotImage> implements SnapshotImageService {

	@Autowired
	private SnapshotImageMapper snapshotImageMapper;

	@Autowired
	private SiteProjectInfoMapper siteProjectInfoMapper;
	
	public void testTx(PageView<SnapshotImage> pageView) {

		// 设置分页

	}

	@Override
	public SnapshotImageRecord querySnapshotImageTree() {
		// 封装抓拍记录树
		SnapshotImageRecord snapshotImageRecord = new SnapshotImageRecord();
		// 获取工地抓拍图片树
		snapshotImageRecord.setSites(querySnapshotImageSiteTree());
		// 获取处置场抓拍图片树
		snapshotImageRecord.setDisposals(querySnapshotImageDisposalTree());
		// 获取停车场抓拍图片树
		snapshotImageRecord.setCarParks(querySnapshotImageCarPark());
		return snapshotImageRecord;
	}

	/** 生成表格数据 */
	@Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate, List<SnapshotImage> list) {
		if (list != null && list.size() > 0) {
			// 声明一个存放表格数据的集合
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 存放到表格中的数据列表，包括表头和表身数据
			// 声明一个表头键值对容器，这里存放所有的表头信息
			Map<String, String> tableHead = new TreeMap<String, String>();

			// 存放表格数据
			Map<String, String> tableBody = null;

			// 获取传入的表格模版的类的对象
			Class<?> cls = excelTemplate.getClass();
			// 获取这个类的属性集合
			Field[] fields = cls.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					// 设置属性是可访问的
					field.setAccessible(true);
					String fieldName = field.getName();
					// 获取属性上的注解
					ExcelTemplate template = field.getAnnotation(ExcelTemplate.class);
					// 将注解为表头字段
					tableHead.put(fieldName, template.name());
				}
			}
			// 将表头放入总集合
			data.add(tableHead);
			// 生成表身数据
			// 遍历传入的数据列表
			for (SnapshotImage snapshotImage : list) {
				// 获取类的对象
				Class<?> clazz = snapshotImage.getClass();
				// 迭代表头获取存放的字段
				Set<String> keySet = tableHead.keySet();
				tableBody = new TreeMap<String, String>();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					try {
						if (key.equals("has_projectName")) {
							String siteId = snapshotImage.getSiteId();
							if (siteId != null) {
								// 根据工地号查询工地详情
								SiteProjectInfo projectInfo = siteProjectInfoMapper
										.querySiteProjectInfoBySiteId(siteId);
								if (projectInfo != null) {
									tableBody.put(key, projectInfo.getProjectName());
								} else {
									tableBody.put(key, null);
								}
							} else {
								tableBody.put(key, null);
							}
							continue;
						}
						Field filed = clazz.getDeclaredField(key.replace("has_", ""));
						filed.setAccessible(true);
						String type = filed.getGenericType().toString();
						// 获取该对象的此属性的值
						Object obj = filed.get(snapshotImage);
						// 如果获取到的是时间类型，则转化一下时间格式
						if ("date".equalsIgnoreCase(type.substring(type.lastIndexOf(".") + 1, type.length()))) {
							Date date = (Date) obj;
							if (date == null) {
								tableBody.put(key, null);
							} else {
								// 将该key与属性值添加到当前条数据的Excel表单元格中
								tableBody.put(key, sdf.format(date));
							}
						} else {
							if (obj == null) {
								tableBody.put(key, null);
							} else {
								// 将该key与属性值添加到当前条数据的Excel表单元格中
								tableBody.put(key, obj.toString());
							}
						}
					} catch (Exception e) {
						throw new BizException(StatusCode.INTERNAL_ERROR);
					}
				}
				data.add(tableBody);
			}
			return data;
		}
		return null;
	}

	@Override
	public List<Area> querySnapshotImageSiteTree() {
		return null;
	}

	@Override
	public List<Area> querySnapshotImageDisposalTree() {
		return null;
	}

	@Override
	public List<Area> querySnapshotImageCarPark() {
		return null;
	}

	// ------------------------------------------------------------------
	protected BaseMapper<SnapshotImage> getMapper() {
		return snapshotImageMapper;
	}

	// 要获取的字段
	protected String getFields() {
		return "*";
	}
}
