package com.muck.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Channel;
import com.muck.handler.IdTypeHandler;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.SiteVedioMapper;
import com.muck.page.PageView;
import com.muck.query.SiteVedioQueryForm;
import com.muck.service.AreaService;
import com.muck.service.SiteVedioService;

/**
 * @Description: 工地视频service
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月14日 上午11:16:46
 */
@Service
public class SiteVedioServiceImpl extends BaseServiceImpl<Channel> implements SiteVedioService {

	@Autowired
	private SiteVedioMapper siteVedioMapper; // 工地视频Mapper
	@Autowired
	private AreaService areaService;
	/**
	 * 查询视频数据
	 */
	public PageView<Channel> querySiteVedio(SiteVedioQueryForm siteVedioForm) {
		
		String limit = super.buildLimit(siteVedioForm.getPageNum(), siteVedioForm.getPageSize());
		//用来查询数据总数的sql
		String sql = "select count(tc.id) from t_channel tc,t_device_channel tdc,t_device td ";
		//用来查询数据的sql
		String selectSQL = "select "
							+ " tc.channel_name as channelName,tc.channel_code as channelCode,td.device_area_name as deviceAreaName "
							+ " from t_channel tc,t_device_channel tdc,t_device td ";
		//用来排序的sql
		StringBuilder orderBy = new StringBuilder("");
		
			   // 根据区域id查询
			   if(!StringUtils.isBlank(siteVedioForm.getAreaCode())){
				   selectSQL += " ,t_area_device tad ";
				   sql += " ,t_area_device tad ";
				   orderBy.append(" tad.area_code asc,");
			   }
			   // 根据企业id查询
			   if(siteVedioForm.getCompanyIds() != null && siteVedioForm.getCompanyIds().length > 0){
				   selectSQL += " ,t_company_device tcd ";
				   sql += " ,t_company_device tcd ";
				   orderBy.append(" tcd.company_id asc,");
			   }
			   // 根据工地名称查询
			   if(!StringUtils.isBlank(siteVedioForm.getSiteName())){
				   selectSQL += " ,t_site_device tsd,t_site ts ";
				   sql += " ,t_site_device tsd,t_site ts ";
				   orderBy.append(" tsd.site_id asc,");
			   }
			   // 根据负责人姓名或联系方式查询
			   if(!StringUtils.isBlank(siteVedioForm.getManagerName())){
				   selectSQL += " ,t_site_manager tsm ";
				   sql += " ,t_site_manager tsm ";
				   orderBy.append(" tsm.id asc,");
			   }
			   if(!StringUtils.isBlank(siteVedioForm.getManagerPhone())){
				   selectSQL += " ,t_site_manager tsm ";
				   sql += " ,t_site_manager tsm ";
				   orderBy.append(" tsm.id asc,");
			   }
			   // 根据工地id列表查询
			   if(siteVedioForm.getSiteIds() != null && siteVedioForm.getSiteIds().length > 0){
				   selectSQL += " ,t_site_device tsd,t_site ts ";
				   sql += " ,t_site_device tsd,t_site ts ";
				   orderBy.append(" tsd.site_id asc,");
			   }
			   
			   
			   
			   selectSQL +=" where  tc.channel_status=1  and tc.channel_code = tdc.channel_code and tdc.device_code = td.device_code ";
			   sql += " where  tc.channel_status=1  and tc.channel_code = tdc.channel_code and tdc.device_code = td.device_code ";
			   // 根据区域id查询
			   if(!StringUtils.isBlank(siteVedioForm.getAreaCode())){
				   String areaCodes = areaService.querySubAreaCodesByParentCode(siteVedioForm.getAreaCode());
				   selectSQL += " and tad.area_code in ("+areaCodes+")";
				   selectSQL += " and tad.device_code = td.device_code";
				   sql += " and tad.area_code in ("+areaCodes+")";
				   sql += " and tad.device_code = td.device_code";
			   }
			   // 根据企业id查询
			   if(siteVedioForm.getCompanyIds() != null && siteVedioForm.getCompanyIds().length > 0){
				   String [] companyIds = siteVedioForm.getCompanyIds();
				   StringBuilder str_companyIds = new StringBuilder("");
				   for(String companyId : companyIds){
					   str_companyIds.append(IdTypeHandler.decode(companyId));
					   str_companyIds.append(",");
				   }
				   if(str_companyIds.length() > 0){
					   str_companyIds.deleteCharAt(str_companyIds.length()-1);
				   }
				   selectSQL += " and tcd.company_id in ("+str_companyIds+")";
				   selectSQL += " and tcd.device_code = td.device_code";
				   sql += " and tcd.company_id in ("+str_companyIds+")";
				   sql += " and tcd.device_code = td.device_code";
			   }
			   // 根据工地名称查询
			   if(!StringUtils.isBlank(siteVedioForm.getSiteName())){
				   selectSQL += " and ts.site_name = '"+siteVedioForm.getSiteName()+"'";
				   selectSQL += " and ts.id = tsd.site_id";
				   selectSQL += " and tsd.device_code = td.device_code";
				   sql += " and ts.site_name = '"+siteVedioForm.getSiteName()+"'";
				   sql += " and ts.id = tsd.site_id";
				   sql += " and tsd.device_code = td.device_code";
			   }
			   // 根据负责人联系方式查询
			   if(!StringUtils.isBlank(siteVedioForm.getManagerName())){
				   selectSQL += " and tsm.site_manager = '"+siteVedioForm.getManagerName()+"'";
				   selectSQL += " and tsm.site_id = tsd.site_id";
				   selectSQL += " and tsd.device_code = td.device_code";
				   sql += " and tsm.site_manager = '"+siteVedioForm.getManagerName()+"'";
				   sql += " and tsm.site_id = tsd.site_id";
				   sql += " and tsd.device_code = td.device_code";
			   }
			   // 根据负责人联系方式查询
			   if(!StringUtils.isBlank(siteVedioForm.getManagerPhone())){
				   selectSQL += " and tsm.site_manager_phone = '"+siteVedioForm.getManagerPhone()+"'";
				   selectSQL += " and tsm.site_id = tsd.site_id";
				   selectSQL += " and tsd.device_code = td.device_code";
				   sql += " and tsm.site_manager_phone = '"+siteVedioForm.getManagerPhone()+"'";
				   sql += " and tsm.site_id = tsd.site_id";
				   sql += " and tsd.device_code = td.device_code";
			   }
			   // 根据工地id列表查询
			   if(siteVedioForm.getSiteIds() != null && siteVedioForm.getSiteIds().length > 0){
				  
				   String[] siteIds = siteVedioForm.getSiteIds();
				   StringBuilder str_siteIds = new StringBuilder("");
				   for(String siteId : siteIds){
					   str_siteIds.append(IdTypeHandler.decode(siteId));
					   str_siteIds.append(",");
				   }
				   if(str_siteIds.length() > 0){
					   str_siteIds.deleteCharAt(str_siteIds.length()-1);
				   }
				   selectSQL += " and ts.id in ("+str_siteIds+")";
				   selectSQL += " and ts.id = tsd.site_id";
				   selectSQL += " and tsd.device_code = td.device_code";
				   sql += " and ts.id in ("+str_siteIds+")";
				   sql += " and ts.id = tsd.site_id";
				   sql += " and tsd.device_code = td.device_code";
			   }
			   if(orderBy.length() > 0){
				   orderBy = orderBy.deleteCharAt(orderBy.length()-1);
				   selectSQL += " order by "+orderBy;
			   }
			   selectSQL += limit;
	    Long totalRecord = siteVedioMapper.selectTotalRecoreds(sql);
	    PageView<Channel> pageView = new PageView<>(totalRecord, siteVedioForm.getPageNum(), siteVedioForm.getPageSize());
	    selectSQL = super.setQueryParams(selectSQL, null, pageView);
	    List<Channel> datas = siteVedioMapper.selectPageData(selectSQL);
		pageView.setDatas(datas);
		return pageView;
	}

	
	//-----------------------------------------------------------------
	
	protected BaseMapper<Channel> getMapper() {
		return null;
	}

	@Override
	protected String getFields() {
		return "channel_name,channel_code,device_area_name";
	}
}
