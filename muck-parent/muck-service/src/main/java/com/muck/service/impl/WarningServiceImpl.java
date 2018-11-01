package com.muck.service.impl;

import java.util.LinkedHashMap; 
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Warning;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.WarningMapper;
import com.muck.page.PageView;
import com.muck.service.WarningService;

/**
* @Description: 告警Service实现
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月4日 上午10:14:57 
 */
@Service
public class WarningServiceImpl extends BaseServiceImpl<Warning> implements WarningService {

	@Autowired
	private WarningMapper warningMapper; 
	
	@Override
	public PageView<Warning> queryPageData(Long currentPageNum, Long pageSize, String whereSQL, List<Object> whereParams,
			LinkedHashMap<String, String> orderBy,Integer warningType) {
		// 第一步：生成order的sql
		String orderby = buildOrderby(orderBy);
		// 第二步：生成分页的sql
		String limit = super.buildLimit(currentPageNum, pageSize);
		// 第三步：生成条件sql
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";
		// 第四步：用来查询数据总数的sql
		String countSQL = "select count(w.id) from t_warnings w where 1 = 1 " + whereSQL;
		// 第五步：生成查询数据的sql
		String selectSQL = "";
		if(warningType != null){
			//如果传入的告警类别不为空
			if (warningType == 1){
				//如果是工地告警情况
				selectSQL += " select "
						+ " w.warning_content,w.device_name,w.car_code,w.warning_address,w.handle_time,w.is_handle, "
						+ " s.site_name,s.area_name as site_area_name,s.company_name, "
						+ " (select spi.construction_unit_manager from t_biz_site_project_info spi where spi.site_id = s.id) as construction_unit_manager, "
						+ " (select spi.construction_unit_manager_phone from t_biz_site_project_info spi where spi.site_id = s.id) as construction_unit_manager_phone "
						+ " from "
						+ " t_warnings w left join t_site s on w.site_id = s.id "
						+ " where 1 = 1 "
						+ whereSQL + orderby + limit;
			} else if (warningType == 2){
				//如果是车辆高警情况
				selectSQL += " select "
						+ " w.warning_content,w.device_name,w.car_code,w.warning_address,w.handle_time,w.is_handle, "
						+ " car.car_code,car.car_owner_of_vehicle,car.area_name car_area_name,car.company_name, "
						+ " (select c.company_legal_representative from t_company c where c.id = car.company_id) as company_legal_representative, "
						+ " (select c.company_legal_representative_phone from t_company c where c.id = car.company_id) as company_legal_representative_phone "
						+ " from "
						+ " t_warnings w left join t_car car on w.car_code = car.car_code "
						+ " where 1 = 1"
						+ whereSQL + orderby + limit;
			} else if (warningType == 3){
				//如果是处置场告警情况
				selectSQL += " select "
						+ " w.warning_content,w.device_name,w.car_code,w.warning_address,w.handle_time,w.is_handle, "
						+ " d.disposal_name,d.area_name as disposal_area_name,d.company_name, "
						+ " (select c.company_legal_representative from t_company c where c.id = d.company_id) as company_legal_representative, "
						+ " (select c.company_legal_representative_phone from t_company c where c.id = d.company_id) as company_legal_representative_phone "
						+ " from "
						+ " t_warnings w left join t_disposal d on w.disposal_id = d.id"
						+ " where 1 = 1"
						+ whereSQL + orderby + limit;
			}
		}else {
			selectSQL += " select "
					+ "device_code,device_name,site_id,disposal_id,car_park_id,car_code,warning_name,warning_type,warning_time,warning_content,warning_address, "
					+ "warning_status,handle_time,is_handle,memo, deleted, created_time"
					+ " from "
					+ " t_warnings "
					+ " where 1 = 1"
					+ whereSQL + orderby + limit;
		}
	
		// 第六步、获取数据总数
		countSQL = setQueryParams(countSQL , whereParams,null);
		Long totalRecord = warningMapper.selectTotalRecoreds(countSQL);
		// 第七步、设置pageView,主要是用来计算limit的数据
		PageView<Warning> pageView = new PageView<Warning>(totalRecord, currentPageNum, pageSize);
		// 第八步、设置参数信息
		selectSQL = setQueryParams(selectSQL , whereParams,pageView);
		// 第九步、查询数据
		List<Warning> datas = warningMapper.selectPageData(selectSQL);
		pageView.setDatas(datas);
		return pageView;
	}
	@Override
	public Long queryWarningCount() {
		return warningMapper.queryWarningCount();
	}
	
	@Override
	public Warning queryByWarningName(String warningName) {
		return warningMapper.queryByWarningName(warningName);
	}
	
	
	//------------------------------------------------
	@Override
	protected BaseMapper<Warning> getMapper() {
		return warningMapper;
	}

	@Override
	protected String getFields() {
		return "id,device_code,device_name,site_id,disposal_id,car_park_id,car_code, warning_name, warning_type, warning_time, "
				+ "warning_content,warning_address,warning_time,"
				+ "warning_status, handle_time,is_handle,memo, deleted, created_time";
	}
}
