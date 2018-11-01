package com.muck.admin.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Car;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.request.CarForm;
import com.muck.response.ResponseResult;
import com.muck.service.AreaService;
import com.muck.service.CarService;

/**
 * @Description: 渣土管理controller
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月8日 上午9:42:13
 */
@RestController("AdminMuckController")
@RequestMapping("/admin/muck")
public class MuckController extends BaseController{

	@Autowired
	CarService carService;
	@Autowired
	AreaService areaService;
	/**
	 * @Description: 渣土管理系统-高级筛选
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月8日 上午9:47:49
	 * @param: carForm 传入查询车辆信息的参数
	 * @return: ResponseResult 返回含有车辆列表的结果集
	 */
	@RequestMapping("muck.action")
	public ResponseResult muck(CarForm carForm){
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Car> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("created_time", "desc");
		// 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据
		if("true".equals(carForm.getQuery())){
			
			// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
			QueryHelper queryHelper = new QueryHelper();
			
			// 根据地域查询工地
			String areaId = carForm.getAreaId();
			
			// 根据企业id查询工地
			String companyId = carForm.getCompanyId();
			
			queryHelper.addCondition(areaService.querySubAreaIdsByParentId(areaId), "area_id in (%s)",false)
					   .addCondition(companyId, "company_id = %d", false);
			pageView = carService.queryPageData(carForm.getPageNum(),carForm.getPageSize(),queryHelper.getWhereSQL(),queryHelper.getWhereParams(),orderby);
		}else{
			pageView = carService.queryPageData();
		}
		return ResponseResult.ok(pageView);
	}
}
