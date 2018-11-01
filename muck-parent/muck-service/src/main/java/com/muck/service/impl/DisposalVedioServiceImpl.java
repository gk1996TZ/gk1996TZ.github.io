package com.muck.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Channel;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.DisposalVedioMapper;
import com.muck.page.PageView;
import com.muck.query.DisposalVedioQueryForm;
import com.muck.service.AreaService;
import com.muck.service.DisposalVedioService;
import com.muck.utils.ArrayUtils;

/**
 * @Description: 处置场查询视频service实现
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月17日 上午11:40:32
 */
@Service
public class DisposalVedioServiceImpl extends BaseServiceImpl<Channel> implements DisposalVedioService{

	@Autowired
	private DisposalVedioMapper disposalVedioMapper;
	@Autowired
	private AreaService areaService;
	@Override
	public PageView<Channel> queryDisposalVedio(DisposalVedioQueryForm disposalVedioQueryForm) {
		String limit = super.buildLimit(disposalVedioQueryForm.getPageNum(), disposalVedioQueryForm.getPageSize());
		//用来查询数据总数的sql
		String sql = "select count(tc.id) from t_channel tc,t_device_channel tdc,t_device td";
		//用来查询数据的sql
		String selectSQL = "select "
							+ "tc.channel_name ,tc.channel_code ,td.device_area_name "
							+ "from t_channel tc,t_device_channel tdc,t_device td ";
		//用来排序的sql
		StringBuilder orderBy = new StringBuilder();
			   // 根据区域id查询
			   if(!StringUtils.isBlank(disposalVedioQueryForm.getAreaCode())){
				   selectSQL += " ,t_area_device tad ";
				   sql += " ,t_area_device tad ";
				   orderBy.append(" tad.area_code asc,");
			   }
			   // 根据企业id查询
			   if(disposalVedioQueryForm.getCompanyIds() != null && disposalVedioQueryForm.getCompanyIds().length > 0){
				   selectSQL += " ,t_company_device tcd ";
				   sql += " ,t_company_device tcd ";
				   orderBy.append(" tcd.company_id asc,");
			   }
			   // 根据处置场名称查询
			   if(!StringUtils.isBlank(disposalVedioQueryForm.getDisposalName())){
				   selectSQL += " ,t_disposal_device tdd,t_disposal tdp ";
				   sql += " ,t_disposal_device tdd,t_disposal tdp ";
				   orderBy.append(" tdd.disposal_id asc,");
			   }
			   // 根据负责人姓名或联系方式查询
			   if(!StringUtils.isBlank(disposalVedioQueryForm.getManagerName())){
				   selectSQL += " ,t_disposal_manager tdm ";
				   sql += " ,t_disposal_manager tdm ";
				   orderBy.append(" tdm.id asc,");
			   }
			   if(!StringUtils.isBlank(disposalVedioQueryForm.getManagerPhone())){
				   selectSQL += " ,t_disposal_manager tdm ";
				   sql += " ,t_disposal_manager tdm ";
				   orderBy.append(" tdm.id asc,");
			   }
			   
			   selectSQL +=" where 1=1 tc.channel_code = tdc.channel_code and tdc.device_code = td.device_code ";
			   sql += " where 1=1 tc.channel_code = tdc.channel_code and tdc.device_code = td.device_code ";
			   // 根据区域id查询
			   if(!StringUtils.isBlank(disposalVedioQueryForm.getAreaCode())){
				   String areaCodes = areaService.querySubAreaCodesByParentCode(disposalVedioQueryForm.getAreaCode());
				   selectSQL += " and tad.area_code in ("+areaCodes+")";
				   selectSQL += " and tad.device_code = td.device_code";
				   sql += " and tad.area_code in ("+areaCodes+")";
				   sql += " and tad.device_code = td.device_code";
			   }
			   // 根据企业id查询
			   if(disposalVedioQueryForm.getCompanyIds() != null && disposalVedioQueryForm.getCompanyIds().length > 0){
				   selectSQL += " and tcd.company_id in ("+ArrayUtils.array2str(disposalVedioQueryForm.getCompanyIds())+")";
				   selectSQL += " and tcd.device_code = td.device_code";
				   sql += " and tcd.company_id in ("+ArrayUtils.array2str(disposalVedioQueryForm.getCompanyIds())+")";
				   sql += " and tcd.device_code = td.device_code";
			   }
			   // 根据工地名称查询
			   if(!StringUtils.isBlank(disposalVedioQueryForm.getDisposalName())){
				   selectSQL += " and tdp.disposal_name = '"+disposalVedioQueryForm.getDisposalName()+"'";
				   selectSQL += " and tdp.id = tdd.disposal_id";
				   selectSQL += " and tdd.device_code = td.device_code";
				   sql += " and tdp.disposal_name = '"+disposalVedioQueryForm.getDisposalName()+"'";
				   sql += " and tdp.id = tdd.disposal_id";
				   sql += " and tdd.device_code = td.device_code";
			   }
			   // 根据负责人联系方式查询
			   if(!StringUtils.isBlank(disposalVedioQueryForm.getManagerName())){
				   selectSQL += " and tdm.disposal_manager like '%"+disposalVedioQueryForm.getManagerName()+"%'";
				   selectSQL += " and tdm.disposal_id = tdd.disposal_id";
				   selectSQL += " and tdd.device_code = td.device_code";
				   sql += " and tdm.disposal_manager like '%"+disposalVedioQueryForm.getManagerName()+"%'";
				   sql += " and tdm.disposal_id = tdd.disposal_id";
				   sql += " and tdd.device_code = td.device_code";
			   }
			   // 根据负责人联系方式查询
			   if(!StringUtils.isBlank(disposalVedioQueryForm.getManagerPhone())){
				   selectSQL += " and tdm.disposal_manager_phone = '"+disposalVedioQueryForm.getManagerPhone()+"'";
				   selectSQL += " and tdm.disposal_id = tdd.disposal_id";
				   selectSQL += " and tdd.device_code = td.device_code";
				   sql += " and tdm.disposal_manager_phone = '"+disposalVedioQueryForm.getManagerPhone()+"'";
				   sql += " and tdm.disposal_id = tdd.disposal_id";
				   sql += " and tdd.device_code = td.device_code";
			   }
			   orderBy = orderBy.deleteCharAt(orderBy.length()-1);
			   selectSQL += " order by "+orderBy;
			   selectSQL += limit;
	    Long totalRecord = disposalVedioMapper.selectTotalRecoreds(sql);
	    PageView<Channel> pageView = new PageView<>(totalRecord, disposalVedioQueryForm.getPageNum(), disposalVedioQueryForm.getPageSize());
	    selectSQL = super.setQueryParams(selectSQL, null, pageView);
	    List<Channel> datas = disposalVedioMapper.selectPageData(selectSQL);
		pageView.setDatas(datas);
		return pageView;
	}
	// -----------------------------------------
	@Override
	protected BaseMapper<Channel> getMapper() {
		return null;
	}
	@Override
	protected String getFields() {
		return "channel_name,channel_code,device_area_name";
	}
}
