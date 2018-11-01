package com.muck.admin.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.ElectricFence;
import com.muck.domain.ElectricFenceCar;
import com.muck.exception.base.BizException;
import com.muck.query.ElectricFenceQueryForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.ElectricFenceCarService;
import com.muck.service.ElectricFenceService;

/**
 * @author 甘坤
 *
 */
@RestController("AdminElectricFenceController")
@RequestMapping("/admin/electricFence/")
public class ElectricFenceController extends BaseController {

	@Autowired
	private ElectricFenceService electricFenceService;
	
	@Autowired 
	private ElectricFenceCarService electricFenceCarService;

	// 保存电子围栏
	@RequestMapping(value = "saveElectricFence.action", method = RequestMethod.POST)
	public ResponseResult saveElectricFence(ElectricFenceQueryForm electricFenceQueryForm) {

		// 1.先获取电子围栏的名称并查询
		String electricFenceName = electricFenceQueryForm.getElectricFenceName();
		if (StringUtils.isNotBlank(electricFenceName)) {
			ElectricFence electricFence = electricFenceService.queryElectricFenceByName(electricFenceName);
			if (electricFence != null) {
				throw new BizException(StatusCode.ELECTRIC_FENCE_EXIST);
			}
		}
		// 验证坐标点
		String[] points = electricFenceQueryForm.getElectricFenceCoordinateArr();
		// 如果传入的坐标点数组长度不是偶数，说明传入的有可能不是一个坐标
		if (points.length % 2 != 0) {
			throw new BizException(StatusCode.POINT_FORMAT_ERROR);
		}
		// 如果长度小于六，则说明小于三个坐标，不能构成一个多边形
		if (points.length < 6) {
			throw new BizException(StatusCode.POINT_SIZE_LESS_THREE);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < points.length; i++) {
			// 判断下标是否是偶数
			if (i % 2 == 0) {
				sb.append("[").append(points[i]).append(",").append(points[i + 1]).append("]").append(",");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		// 2.执行保存
		ElectricFence electricFence = new ElectricFence();
		// 电子围栏名称
		electricFence.setElectricFenceName(electricFenceName);
		// 电子围栏坐标
		electricFence.setElectricFenceCoordinate(sb.toString());
		electricFenceService.save(electricFence);
		return ResponseResult.ok(electricFence.getId());
	}

	/**
	 * 查询所有电子围栏
	 * 
	 */
	@RequestMapping(value = "queryAllElectricFence.action", method = RequestMethod.GET)
	public ResponseResult queryAllElectricFence() {
		List<ElectricFence> electricFences = electricFenceService.queryAllElectricFence();
		return ResponseResult.ok(electricFences);
	}

	/**
	 * 根据id查询电子围栏
	 * 
	 */
	@RequestMapping(value = "queryElectricById.action", method = RequestMethod.GET)
	public ResponseResult queryElectricById(String electricFenceId) {

		ElectricFence electricFence = electricFenceService.queryById(electricFenceId);
		if (electricFence == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(electricFence);
	}

	/**
	 * 根据id修改电子围栏
	 * 
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	public ResponseResult editById(ElectricFenceQueryForm electricFenceQueryForm) {

		// 1.先获取电子围栏的名称并查询
		ElectricFence electricFence = new ElectricFence();
		String electricFenceName = electricFenceQueryForm.getElectricFenceName();
		if (StringUtils.isNotBlank(electricFenceName)) {
			ElectricFence electricFenceInDB = electricFenceService.queryElectricFenceByName(electricFenceName);
			if (electricFenceInDB != null && electricFenceInDB.getId() != electricFenceQueryForm.getElectricFenceId()) {
				throw new BizException(StatusCode.ELECTRIC_FENCE_EXIST);
			}
			// 电子围栏名称
			electricFence.setElectricFenceName(electricFenceName);
		}

		// 主键id
		electricFence.setId(electricFenceQueryForm.getElectricFenceId());

		// 验证坐标点
		String[] points = electricFenceQueryForm.getElectricFenceCoordinateArr();
		if(points!=null&&points.length>0){
			// 如果传入的坐标点数组长度不是偶数，说明传入的有可能不是一个坐标
			if (points.length % 2 != 0) {
				throw new BizException(StatusCode.POINT_FORMAT_ERROR);
			}
			// 如果长度小于六，则说明小于三个坐标，不能构成一个多边形
			if (points.length < 6) {
				throw new BizException(StatusCode.POINT_SIZE_LESS_THREE);
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < points.length; i++) {
				// 判断下标是否是偶数
				if (i % 2 == 0) {
					sb.append("[").append(points[i]).append(",").append(points[i + 1]).append("]").append(",");
				}
			}
			sb.deleteCharAt(sb.length() - 1);
			// 电子围栏坐标
			electricFence.setElectricFenceCoordinate(sb.toString());	
		}
		// 是否禁用
		electricFence.setElectricFenceIsbanning(electricFenceQueryForm.isElectricFenceIsbanning());
		electricFenceService.editById(electricFence);
		return ResponseResult.ok();
	}
	
	/**
	 * 删除电子围栏
	 * 只有没有车辆信息才能删除
	 * 
	 * */
	@RequestMapping(value="deleteElectricById.action",method=RequestMethod.GET)
	public ResponseResult deleteElectricById(String electricFenceId){
		
		//首先查询这个电子围栏下面有没有车辆
		List<ElectricFenceCar> electricFenceCars=electricFenceCarService.queryElectricFenceCarsByElectricFenceId(electricFenceId);
		if(!electricFenceCars.isEmpty()){
			throw new BizException(StatusCode.ELECTRIC_FENCE_CAR_NOT_NULL);
		}
		//真实删除
		electricFenceService.deleteByIdReal(electricFenceId);
		return ResponseResult.ok();
	}

}
