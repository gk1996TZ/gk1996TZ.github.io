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
import com.muck.domain.Channel;
import com.muck.domain.Company;
import com.muck.domain.Site;
import com.muck.domain.SiteGroup;
import com.muck.domain.SiteProjectInfo;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.SiteAndSiteGroupMapper;
import com.muck.mapper.SiteGroupMapper;
import com.muck.mapper.SiteMapper;
import com.muck.mapper.SiteProjectInfoMapper;
import com.muck.page.PageView;
import com.muck.response.StatusCode;
import com.muck.service.SiteService;
import com.muck.utils.ArrayUtils;
import com.muck.utils.UniqueUtils;

/**
 * @Description: 工地Service
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月15日 上午10:41:53
 */
@Service
public class SiteServiceImpl extends BaseServiceImpl<Site> implements SiteService {

	@Autowired
	private SiteProjectInfoMapper siteProjectInfoMapper;

	@Autowired
	private SiteGroupMapper siteGroupMapper;

	@Autowired
	private SiteAndSiteGroupMapper siteAndSiteGroupMapper;

	/**
	 * @Description: 保存工地 1、保存工地基本信息 2、保存工地所属的企业 3、保存工地的项目负责人
	 * @author: 展昭
	 * @date: 2018年5月15日 上午10:42:05
	 */
	@Override
	public void save(Site site) {

		// 1、保存工地基本信息
		site.setSiteId("S" + UniqueUtils.generateUniqueCode()); // 工地编码
		super.save(site);

		// 2、保存工地所关联的项目详情
		SiteProjectInfo info = site.getSiteProjectInfo();
		info.setCreatedTime(site.getCreatedTime());
		if (info != null) {
			info.setSite(site);
			siteProjectInfoMapper.insert(info);
		}
	}

	@Autowired
	SiteMapper siteMapper;

	@Override
	public String querySiteIdsByManagerIds(String managerIds) {
		String siteIds = null;
		Set<String> setSiteIds = siteMapper.getSiteIdsByManagerIds(managerIds);
		if (setSiteIds != null) {
			siteIds = ArrayUtils.array2str(setSiteIds.toArray());
		}
		return siteIds;
	}

	@Override
	public String querySiteIdsAll() {
		List<String> siteIds = siteMapper.getSiteIdsAll();
		if (siteIds != null) {
			return ArrayUtils.array2str(siteIds.toArray());
		}
		return null;
	}

	/**
	 * public PageView<Site> queryPageData(Long currentPageNum, Long pageSize,
	 * String wherSQL, List<Object> whereParams, LinkedHashMap<String, String>
	 * orderBy) { // 第一步、获取要查询的字段 String queryFields = getFields(); if
	 * (StringUtils.isBlank(queryFields)) { throw new
	 * BizException(StatusCode.QUERY_FIELDS_NOT_FOUND); }
	 * 
	 * // 第二步、获取表名 //String tableName = getTableNameByEntity(this.entityClass);
	 * String tableName = " t_site ts,t_site_manager tsm "; // 第三步、获取where条件
	 * String where = StringUtils.isBlank(wherSQL) ?
	 * " where 1 = 1  and ts.id = tsm.site_id " :
	 * " where ts.site_id = tsm.site_id and " + wherSQL;
	 * 
	 * // 第四步、获取orderby条件 String orderby = buildOrderby(orderBy);
	 * 
	 * // 第五步、生成查询总条数sql语句 String countSQL = " SELECT COUNT(ts.id) from " +
	 * tableName + where;
	 * 
	 * // 第六步、设置countSQL并返回sql语句 countSQL = setQueryParams(countSQL ,
	 * whereParams,null); // select id from t_car where id = 1
	 * 
	 * // 第七步、获取总页数 Long totalRecord =
	 * getMapper().selectTotalRecoreds(countSQL);
	 * 
	 * // 第八步、生成limit语句 String limit = buildLimit(currentPageNum,pageSize);
	 * 
	 * // 第九步、生成查询记录数sql语句 String selectSQL = " SELECT " + queryFields +
	 * " from " + tableName + where + orderby + limit;
	 * 
	 * // 第十步、设置pageView,主要是用来计算limit的数据 PageView<Site> pageView = new
	 * PageView<>(totalRecord, currentPageNum, pageSize);
	 * 
	 * // 第十一步、设置参数信息 selectSQL = setQueryParams(selectSQL ,
	 * whereParams,pageView);
	 * 
	 * // 第十一步、查询数据 List<Site> datas = getMapper().selectPageData(selectSQL);
	 * 
	 * pageView.setDatas(datas);
	 * 
	 * return pageView; }
	 */

	@Override
	public PageView<Site> queryPageData() {
		return queryPageData(-1L, -1L, null, null, null);
	}

	/*
	 * @Override public PageView<Site> querySitePageData(SiteQueryForm
	 * siteQueryForm){ String limit =
	 * super.buildLimit(siteQueryForm.getPageNum(),
	 * siteQueryForm.getPageSize()); //用来查询数据总数的sql String sql =
	 * "select count(ts.id) from t_site ts"; //用来查询数据的sql String selectSQL =
	 * "select " +
	 * " ts.id,ts.site_name ,tsm.site_manager,tsm.site_manager_phone " +
	 * " from t_site ts "; //用来排序的sql StringBuilder orderBy = new
	 * StringBuilder(""); // 根据企业id查询 if(siteQueryForm.getCompanyIds() != null
	 * && siteQueryForm.getCompanyIds().length > 0){ selectSQL +=
	 * " ,t_site_company tsc,t_company tc "; sql +=
	 * " ,t_site_company tsc,t_company tc "; orderBy.append(
	 * " tsc.company_id asc,"); } selectSQL += " ,t_site_manager tsm "; sql +=
	 * " ,t_site_manager tsm "; orderBy.append(" tsm.id asc,");
	 * 
	 * selectSQL +=" where 1=1 "; sql += " where 1=1 "; // 根据区域id查询
	 * in('abc',xyz) if(!StringUtils.isBlank(siteQueryForm.getAreaCode())){
	 * String areaCodes =
	 * areaService.querySubAreaCodesByParentCode(siteQueryForm.getAreaCode());
	 * selectSQL += " and ts.area_code in ("+areaCodes+")"; sql +=
	 * " and ts.area_code in ("+areaCodes+")"; } // 根据企业id查询
	 * if(siteQueryForm.getCompanyIds() != null &&
	 * siteQueryForm.getCompanyIds().length > 0){ String [] companyIds =
	 * siteQueryForm.getCompanyIds(); StringBuilder str_companyIds = new
	 * StringBuilder(""); for(String companyId : companyIds){
	 * str_companyIds.append(IdTypeHandler.decode(companyId));
	 * str_companyIds.append(","); } if(str_companyIds.length() > 0){
	 * str_companyIds.deleteCharAt(str_companyIds.length()-1); } selectSQL +=
	 * " and tsc.company_id in ("+str_companyIds+")"; selectSQL +=
	 * " and tsc.company_id = tc.id "; selectSQL += " and tsc.site_id = ts.id ";
	 * sql += " and tsc.company_id in ("+str_companyIds+")"; sql +=
	 * " and tsc.company_id = tc.id "; sql += " and tsc.site_id = ts.id "; } //
	 * 根据工地名称查询 if(!StringUtils.isBlank(siteQueryForm.getSiteName())){ selectSQL
	 * += " and ts.site_name = '"+siteQueryForm.getSiteName()+"'"; sql +=
	 * " and ts.site_name = '"+siteQueryForm.getSiteName()+"'"; } // 根据负责人联系方式查询
	 * if(!StringUtils.isBlank(siteQueryForm.getManagerName())){ String
	 * managerName = "" + siteQueryForm.getManagerName() + ""; selectSQL +=
	 * " and tsm.site_manager = '"+ managerName +"'"; selectSQL +=
	 * " and tsm.site_id = ts.id"; sql += " and tsm.site_manager = '"+
	 * managerName +"'"; sql += " and tsm.site_id = ts.id"; } // 根据负责人联系方式查询
	 * if(!StringUtils.isBlank(siteQueryForm.getManagerPhone())){ selectSQL +=
	 * " and tsm.site_manager_phone = '"+siteQueryForm.getManagerPhone()+"'";
	 * selectSQL += " and tsm.site_id = ts.id"; sql +=
	 * " and tsm.site_manager_phone = '"+siteQueryForm.getManagerPhone()+"'";
	 * sql += " and tsm.site_id = ts.id"; } if(orderBy.length() > 0){ orderBy =
	 * orderBy.deleteCharAt(orderBy.length()-1); selectSQL += " order by "
	 * +orderBy; } selectSQL += limit; Long totalRecord =
	 * siteMapper.selectTotalRecoreds(sql); PageView<Site> pageView = new
	 * PageView<>(totalRecord, siteQueryForm.getPageNum(),
	 * siteQueryForm.getPageSize()); selectSQL = super.setQueryParams(selectSQL,
	 * null, pageView); List<Site> datas = siteMapper.selectPageData(selectSQL);
	 * pageView.setDatas(datas); return pageView; }
	 **/

	@Override
	public Site querySiteInfoByChannelCode(String channelCode) {

		return siteMapper.selectSiteInfoByChannelCode(channelCode);
	}

	@Override
	public String querySiteIdByDeviceCode(String deviceCode) {
		return siteMapper.querySiteIdByDeviceCode(deviceCode);
	}

	@Override
	public List<Channel> queryChannelBySiteId(String siteId) {
		return siteMapper.queryChannelBySiteId(siteId);
	}

	// ------------------------------------------

	/**
	 * 根据工地id更新工地项目进度
	 */
	public void editSiteProjectProcess(String siteId, String siteProcessMemo) {
		Site site = new Site();
		site.setId(siteId);
		site.setSiteProcessMemo(siteProcessMemo);

		siteMapper.updateById(site);
	}

	/**
	 * 根据工地id修改工地 修改工地的同时可能也会去修改工地项目详情。
	 */
	public void editById(Site site) {

		// 更新工地
		super.editById(site);

		// 更新工地详情
		siteProjectInfoMapper.updateById(site.getSiteProjectInfo());
	}

	/***
	 * 初始化工地组织树
	 */
	public void initTreeSites(List<Area> areas) {

		if (areas != null && !areas.isEmpty()) {
			for (Area area : areas) {
				// 根据区域id查询区域下面的所有的工地
				List<Site> sites = this.querySitesByAreaId(area.getId());
				area.setSites(sites);
				// 根据区域id查询区域下面的所有的工地组
				List<SiteGroup> siteGroups = this.querySiteGroupsByAreaId(area.getId());
				area.setSiteGroups(siteGroups);
				// 根据每一个工地组id查询此工地组下面所有的工地
				if (siteGroups != null && !siteGroups.isEmpty()) {
					for (SiteGroup siteGroup : siteGroups) {
						// 根据指定的工地组id查询此工地组下面所有的工地
						siteGroup.setSites(this.querySitesBySiteGroupId(siteGroup.getId()));
					}
				}
				initTreeSites(area.getChildAreas());
			}
		}
	}

	/**
	 * 根据区域id查询区域下面所有的工地
	 */
	public List<Site> querySitesByAreaId(String areaId) {

		return siteMapper.selectByAreaId(areaId);
	}

	/**
	 * 根据区域id查询区域下面所有的工地组
	 */
	public List<SiteGroup> querySiteGroupsByAreaId(String areaId) {

		return siteGroupMapper.selectByAreaId(areaId);
	}

	/***
	 * 根据工地组id查询工地组下面所有的工地
	 */
	public List<Site> querySitesBySiteGroupId(String siteGroupId) {

		return siteAndSiteGroupMapper.selectSitesBySiteGroupId(siteGroupId);
	}

	/*
	 * 导出单条或多条工地信息
	 * 
	 **/
	@Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate, List<Site> list) {
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
			for (Site site : list) {
				// 获取类的对象
				Class<?> clazz = site.getClass();
				// 迭代表头获取存放的字段
				Set<String> keySet = tableHead.keySet();
				tableBody = new TreeMap<String, String>();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					try {
						if (key.equals("has_company")) {
							Company company = site.getCompany();
							if (company != null && company.getCompanyName() != null) {
								// 根据工地号查询工地详情
								tableBody.put(key, company.getCompanyName());
							} else {
								tableBody.put(key, null);
							}
							continue;
						} else if (key.equals("has_area")) {
							Area area = site.getArea();
							if (area != null && area.getAreaName() != null) {
								// 根据工地号查询工地详情
								tableBody.put(key, area.getAreaName());
							} else {
								tableBody.put(key, null);
							}
							continue;
						} else if (key.equals("has_siteProjectInfo")) {
							SiteProjectInfo projectInfo = site.getSiteProjectInfo();
							if (projectInfo != null && projectInfo.getProjectName() != null) {
								// 根据工地号查询工地详情
								tableBody.put(key, projectInfo.getProjectName());
							} else {
								tableBody.put(key, null);
							}
							continue;
						}
						Field filed = clazz.getDeclaredField(key.replace("has_", ""));
						filed.setAccessible(true);
						String type = filed.getGenericType().toString();
						// 获取该对象的此属性的值
						Object obj = filed.get(site);
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

	// -----------------------------------------
	@Override
	protected BaseMapper<Site> getMapper() {
		return siteMapper;
	}

	@Override
	protected String getFields() {
		return "id, site_name,site_id, site_address, created_time, updated_time,"
				+ "site_cleaner_name, site_cleaner_phone,area_name,company_name,"
				+ "site_project_manager_one, site_project_manager_phone_one,"
				+ "site_project_manager_two, site_project_manager_phone_two, memo,"
				+ "area_id, area_code, area_name, company_id, company_name," + "site_process_memo";
	}

	@Override
	public String getSiteIdByRegisterCode(String registerCode) {
		
		Integer siteId=siteMapper.getIdByRegisterCode(registerCode);
		
		if(siteId==null){
			throw new BizException(StatusCode.SITE_PROJECT_NOT_BIND);
		}
		//进行加密
		String code=IdTypeHandler.encode(siteId);
		return code;
	}

	@Override
	public void updateBatch(String registerCode,String channelCodes) {
		
		siteMapper.updateBatch(registerCode, channelCodes);
	
	}
}
