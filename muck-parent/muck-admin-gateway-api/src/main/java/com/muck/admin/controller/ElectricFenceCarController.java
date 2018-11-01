package com.muck.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.annotation.Logger;
import com.muck.domain.Car;
import com.muck.domain.Company;
import com.muck.domain.ElectricFence;
import com.muck.domain.ElectricFenceCar;
import com.muck.domain.Manager;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.query.ElectricFenceCarQueryForm;
import com.muck.request.ElectricFenceCarForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.CarService;
import com.muck.service.CompanyService;
import com.muck.service.ElectricFenceCarService;
import com.muck.service.ElectricFenceService;

/**
 * @Description: 电子围栏车辆Controller
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年7月8日 下午2:14:38
 */
@RestController("AdminElectricFenceCarController")
@RequestMapping("/admin/electricFenceCar")
public class ElectricFenceCarController extends BaseController {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ElectricFenceService electricFenceService;
	@Autowired
	private ElectricFenceCarService electricFenceCarService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CarService carService;

	/**
	 * @Description: 添加电子围栏车辆信息的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日 下午3:31:22
	 * @param: electricFenceCarForm
	 *             传入电子围栏车辆信息
	 * @param: session
	 *             传入一个session
	 * @return:ResponseResult 返回操作的信息
	 */
	@RequestMapping("save.action")
	@Logger(operatorModel = "电子围栏", operatorDesc = "添加电子围栏车辆信息")
	public ResponseResult inset(ElectricFenceCarForm electricFenceCarForm, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			logger.info("添加电子围栏车辆信息时重新登录");
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 封装电子围栏车辆信息
		ElectricFenceCar electricFenceCar = new ElectricFenceCar();

		// 车牌号
		/*String carCode = electricFenceCarForm.getCarCode();
		if (!StringUtils.isBlank(carCode)) {
			Car car = carService.queryByCarCode(carCode);
			// 车辆id
			electricFenceCar.setCarId(car.getCarId());
			// 车牌号
			electricFenceCar.setCarCode(car.getCarCode());
			// 车辆所有人
			electricFenceCar.setCarOwnerOfVehicle(car.getCarOwnerOfVehicle());
		}*/

		// 电子围栏id
		String electricFenceId = electricFenceCarForm.getElectricFenceId();
		if (!StringUtils.isBlank(electricFenceId)) {
			ElectricFence electricFence = electricFenceService.queryById(electricFenceId);
			if (electricFence != null) {
				// 电子围栏id
				electricFenceCar.setElectricFenceId(electricFenceCarForm.getElectricFenceId());
				// 电子围栏名称
				electricFenceCar.setElectricFenceName(electricFenceCarForm.getElectricFenceName());
			}
		}

		// 企业id
		String companyId = electricFenceCarForm.getCompanyId();
		if (!StringUtils.isBlank(companyId)) {
			Company company = companyService.queryById(companyId);
			if (company != null) {
				// 企业id
				electricFenceCar.setCompanyId(company.getId());
				// 企业名称
				electricFenceCar.setCompanyName(company.getCompanyName());
				// 企业联系方式
				electricFenceCar.setCompanyContact(company.getCompanyContact());
			}
		}

		electricFenceCar.setOperatorId(IdTypeHandler.decode(manager.getId()));
		electricFenceCar.setOperatorName(manager.getManagerName());
		electricFenceCar.setOperatorPhone(manager.getManagerPhone());

		electricFenceCarService.save(electricFenceCar);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据id删除一条记录（逻辑删除）
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日 下午3:36:11
	 * @param: id
	 *             传入一个id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteById.action")
	@Logger(operatorModel = "电子围栏", operatorDesc = "根据id逻辑删除电子围栏车辆信息")
	public ResponseResult deleteById(String id) {
		if (!StringUtils.isBlank(id)) {
			ElectricFenceCar electricFenceCar = electricFenceCarService.queryById(id);
			if (electricFenceCar == null) {
				logger.info("id值为:" + id + "的电子围栏车辆信息在数据库中不存在");
				throw new BizException(StatusCode.NOT_FOUND);
			}
		}
		electricFenceCarService.deleteById(id);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据id删除电子围栏车辆数据（真实删除）
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日 下午3:42:42
	 * @param: id
	 *             传入一个id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteByIdReal.action")
	@Logger(operatorModel = "电子围栏", operatorDesc = "根据id真实删除电子围栏车辆信息")
	public ResponseResult deleteByIdReal(String id) {
		if (!StringUtils.isBlank(id)) {
			ElectricFenceCar electricFenceCar = electricFenceCarService.queryById(id);
			if (electricFenceCar == null) {
				logger.info("id值为:" + id + "的电子围栏车辆信息在数据库中不存在");
				throw new BizException(StatusCode.NOT_FOUND);
			}
		}
		electricFenceCarService.deleteByIdReal(id);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据id修改电子围栏车辆信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日 下午3:52:30
	 * @param: electricFenceCarForm
	 *             传入一个电子围栏车辆信息
	 * @param: session
	 *             传入一个HttpSession会话
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("updateById.action")
	@Logger(operatorModel = "电子围栏", operatorDesc = "根据id修改电子围栏车辆信息")
	public ResponseResult updateById(ElectricFenceCarForm electricFenceCarForm, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			logger.info("修改电子围栏车辆信息时重新登录");
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 封装电子围栏车辆信息
		ElectricFenceCar electricFenceCar = new ElectricFenceCar();

		// 车牌号
	/*	String carCode = electricFenceCarForm.getCarCode();
		if (!StringUtils.isBlank(carCode)) {
			Car car = carService.queryByCarCode(carCode);
			// 车辆id
			electricFenceCar.setCarId(car.getCarId());
			// 车牌号
			electricFenceCar.setCarCode(car.getCarCode());
			// 车辆所有人
			electricFenceCar.setCarOwnerOfVehicle(car.getCarOwnerOfVehicle());
		}
*/
		// 电子围栏id
		String electricFenceId = electricFenceCarForm.getElectricFenceId();
		if (!StringUtils.isBlank(electricFenceId)) {
			ElectricFence electricFence = electricFenceService.queryById(electricFenceId);
			if (electricFence != null) {
				// 电子围栏id
				electricFenceCar.setElectricFenceId(electricFenceCarForm.getElectricFenceId());
				// 电子围栏名称
				electricFenceCar.setElectricFenceName(electricFenceCarForm.getElectricFenceName());
			}
		}

		// 企业id
		String companyId = electricFenceCarForm.getCompanyId();
		if (!StringUtils.isBlank(companyId)) {
			Company company = companyService.queryById(companyId);
			if (company != null) {
				// 企业id
				electricFenceCar.setCompanyId(company.getId());
				// 企业名称
				electricFenceCar.setCompanyName(company.getCompanyName());
				// 企业联系方式
				electricFenceCar.setCompanyContact(company.getCompanyContact());
			}
		}

		electricFenceCar.setOperatorId(IdTypeHandler.decode(manager.getId()));
		electricFenceCar.setOperatorName(manager.getManagerName());
		electricFenceCar.setOperatorPhone(manager.getManagerPhone());

		electricFenceCarService.editById(electricFenceCar);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据电子围栏车辆id查询
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日 下午4:02:06
	 * @param: id
	 *             传入一个id
	 * @return:ResponseResult 返回电子围栏车辆信息
	 */
	@RequestMapping("queryById.action")
	public ResponseResult queryById(String id) {
		ElectricFenceCar electricFenceCar = electricFenceCarService.queryById(id);
		if (electricFenceCar == null) {
			throw new BizException(StatusCode.NOT_FOUND);
		}
		return ResponseResult.ok(electricFenceCar);
	}

	/**
	 * @Description: 根据电子围栏id查询电子围栏车辆信息列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日 下午4:04:27
	 * @param: electricFenceId
	 *             传入一个电子围栏id
	 * @return:ResponseResult 返回电子围栏车辆信息列表
	 */
	@RequestMapping("queryByElectricFenceId.action")
	public ResponseResult queryByElectricFenceId(String electricFenceId) {
		List<ElectricFenceCar> list = electricFenceCarService.queryElectricFenceCarsByElectricFenceId(electricFenceId);
		if (list == null | list.size() == 0) {
			logger.info("根据电子围栏id为：" + electricFenceId + "没有查询到电子围栏车辆信息");
			throw new BizException(StatusCode.NOT_FOUND);
		}
		return ResponseResult.ok(list);
	}

	/**
	 * @Description: 查询电子围栏车辆信息列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日 下午5:01:47
	 * @param: electricFenceCarForm
	 *             传入查询的列表
	 * @return:ResponseResult 返回含分页列表数据
	 */
	@RequestMapping("queryElectricFenceCars.action")
	public ResponseResult queryElectricFenceCars(ElectricFenceCarQueryForm electricFenceCarQueryForm) {

		QueryHelper queryHelper = new QueryHelper();
		// 电子围栏id
		String electricFenceId = electricFenceCarQueryForm.getElectricFenceId();
		queryHelper.addCondition(electricFenceId, "electric_fence_id = %d", true);

		return ResponseResult
				.ok(electricFenceCarService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams()));
	}

	/**
	 * @Description: 根据车辆组id获取车辆列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月30日 上午11:45:06
	 * @param:@param carGroupId
	 *                   传入一个车辆组id
	 * @return:ResponseResult 返回车辆组里的车辆列表
	 */
	@RequestMapping("queryCars.action")
	public ResponseResult queryCarByCarGroupId(String companyId, String carGroupId, String carCode) {
		// 创建一个QueryHelper帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(carGroupId, "car.car_group_id = %d", true).addCondition(companyId,
				"car.company_id = %d", true);
		if (!StringUtils.isBlank(carCode)) {
			queryHelper.addCondition("%" + carCode + "%", "car.car_code like '%s'", false);
		}
		return ResponseResult
				.ok(carService.queryDataForElectricFence(queryHelper.getWhereSQL(), queryHelper.getWhereParams()));
	}

	/**
	 * @Description: 将企业下车辆组里的车辆添加到电子围栏中的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日 下午5:33:22
	 * @param: electricFenceId
	 *             传入一个电子围栏id
	 * @param: cars
	 *             传入一个存放车辆信息的数组数据格式
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("insertElectricFenceCarsToElectricFence.action")
	public ResponseResult insertElectricFenceCarsToElectricFence(String electricFenceId, String carCodes,
			String companyId, HttpSession session) {
		// electricFenceId电子围栏id必传
		// cars数组中携带的数据
		// 第一种参考：车辆id，车牌号，车辆所有人，企业id，企业名称，企业联系方式
		// 第二种参考：车牌号（或车辆id），企业id，其他数据从数据库获取
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		List<ElectricFenceCar> electricFenceCars = null;

		ElectricFence electricFence = electricFenceService.queryById(electricFenceId);

		// 判断传进来的车牌数组不为空
		if (carCodes != null && carCodes.length() >= 0) {
			String [] codes = carCodes.split(",");
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < codes.length; i++) {
				sb.append("'").append(codes[i]).append("'").append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			// 通过拼接车牌号查询
			List<Car> cars = carService.queryCarByCarCodes(sb.toString());
			if (!cars.isEmpty()) {
				electricFenceCars = new ArrayList<ElectricFenceCar>();
				for (Car car : cars) {
					// 封装电子围栏车辆信息
					ElectricFenceCar electricFenceCar = new ElectricFenceCar();
					electricFenceCar.setCarCode(car.getCarCode());
					electricFenceCar.setCarId(car.getId());
					electricFenceCar.setCarOwnerOfVehicle(car.getCarOwnerOfVehicle());
					if (electricFence != null) {
						electricFenceCar.setElectricFenceCoordinate(electricFence.getElectricFenceCoordinate());
						electricFenceCar.setElectricFenceId(electricFence.getId());
						electricFenceCar.setElectricFenceIsbanning(electricFence.isElectricFenceIsbanning());
						electricFenceCar.setElectricFenceName(electricFence.getElectricFenceName());
					}
					electricFenceCar.setCompanyId(car.getCompanyId());
					electricFenceCar.setCompanyName(car.getCompanyName());
					electricFenceCar.setCarOwnerOfVehicle(car.getCompanyName());
					electricFenceCar.setOperatorId(IdTypeHandler.decode(manager.getId()));
					electricFenceCar.setOperatorName(manager.getManagerName());
					electricFenceCar.setOperatorPhone(manager.getManagerPhone());
					electricFenceCar.setCreatedTime(new Date());
					electricFenceCar.setUpdatedTime(new Date());
					electricFenceCars.add(electricFenceCar);
				}
			}
			// 批量添加电子围栏车辆信息
			electricFenceCarService.saveAll(electricFenceCars);
			return ResponseResult.ok();
		}
		throw new BizException(StatusCode.CAR_CODE_BLANK);

	}

	/**
	 * 根据电子围栏id，和传入的车牌号删除电子围栏车辆信息
	 * 
	 */
	@RequestMapping(value="deleteByCondition.action",method=RequestMethod.POST)
	public ResponseResult deleteByCondition(String electricFenceId, String carCodes) {

		// 判断传进来的车牌数组不为空
		if (carCodes != null && carCodes.length() >= 0) {
			String[] codes = carCodes.split(",");
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < codes.length; i++) {
				sb.append("'").append(codes[i]).append("'").append(",");
			}
			sb.deleteCharAt(sb.length() - 1);

			// 拼接where条件
			QueryHelper queryHelper = new QueryHelper();
			queryHelper.addCondition(electricFenceId, "electric_fence_id=%d", true).addCondition(sb.toString(),
					"car_code in (%s)", false);

			electricFenceCarService.deleteData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
			return ResponseResult.ok();
		}
		throw new BizException(StatusCode.CAR_CODE_BLANK);
	}
}
