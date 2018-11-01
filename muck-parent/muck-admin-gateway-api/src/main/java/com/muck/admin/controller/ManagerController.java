package com.muck.admin.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.admin.utils.WebUtils;
import com.muck.annotation.Logger;
import com.muck.domain.Company;
import com.muck.domain.Manager;
import com.muck.domain.SystemSettings;
import com.muck.exception.base.BizException;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.ManagerQueryForm;
import com.muck.request.ManagerForm;
import com.muck.request.ManagerLoginForm;
import com.muck.response.DaHuaPlatFormConfig;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CompanyService;
import com.muck.service.DisposalService;
import com.muck.service.ManagerService;
import com.muck.service.PropertiesService;
import com.muck.service.SiteService;
import com.muck.service.SystemSettingsService;
import com.muck.utils.MD5Utils;

/**
 * @Description: 系统用户Controller
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月19日 上午11:38:13
 */
@RestController("AdminManagerController")
@RequestMapping("/admin/manager")
public class ManagerController extends BaseController {

	private static final String INIT_PASSWORD = "123456";
	
	// 系统用户service
	@Autowired
	private ManagerService managerService;
	
	// 企业service
	@Autowired
	private CompanyService companyService;
	
	// 区域service
	@Autowired
	private AreaService areaService;
	
	// 工地service
	@Autowired
	private SiteService siteService;
	
	// 处置场
	@Autowired
	private DisposalService disposalService;

	// 属性配置service
	@Autowired
	private PropertiesService propertiesService;

	// 系统配置信息
	@Autowired
	private SystemSettingsService systemSettingsService;

	/**
	 * @Description: 添加系统用户
	 * @author: 展昭
	 * @date: 2018年4月19日 上午11:40:13
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	@Logger(operatorModel = "系统用户", operatorDesc = "添加系统用户")
	public ResponseResult save(ManagerForm managerForm) {

		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Manager manager = new Manager();
		manager.setManagerName(managerForm.getManagerName());	// 用户姓名
		String password = managerForm.getManagerPassword();
		password = StringUtils.isBlank(password) ? INIT_PASSWORD : password;
		manager.setManagerPassword(MD5Utils.encode(password));// 用户密码
		manager.setManagerPhone(managerForm.getManagerPhone());	// 用户联系方式
		manager.setMemo(managerForm.getMemo());		// 用户备注说明
		String companyId = managerForm.getCompanyId();
		if(StringUtils.isNotBlank(companyId)){
			manager.setCompany(companyService.queryById(companyId));	// 所属企业
		}else if (StringUtils.isNotBlank(managerForm.getCompanyName())){
			Company company = new Company();
			company.setCompanyName(managerForm.getCompanyName());
			companyService.save(company);
			manager.setCompany(company);
		}
		
		String areaId = managerForm.getAreaId();
		if(StringUtils.isNotBlank(areaId)){
			manager.setArea(areaService.queryById(areaId));				// 所属区域
		}
		String siteId = managerForm.getSiteId();
		if(StringUtils.isNotBlank(siteId)){
			manager.setSite(siteService.queryById(siteId)); 		// 所属工地
		}
		String disposalId = managerForm.getDisposalId();
		if(StringUtils.isNotBlank(disposalId)){
			manager.setDisposal(disposalService.queryByIDSimple(disposalId)); // 所属处置场
		}
		
		// 第二步、添加保存
		managerService.save(manager);

		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据系统用户id删除用户
	 * @author: 展昭
	 * @date: 2018年4月19日 下午3:22:14
	 */
	@RequestMapping(value = "deleteById.action", method = RequestMethod.GET)
	@Logger(operatorModel = "系统用户", operatorDesc = "根据系统用户id删除用户")
	public ResponseResult deleteById(String managerId) {

		managerService.deleteById(managerId);

		return ResponseResult.ok();
	}

	/**
	 * @Description: 修改系统用户
	 * @author: 展昭
	 * @date: 2018年4月19日 下午3:27:59
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	@Logger(operatorModel = "系统用户", operatorDesc = "根据系统用户id修改用户")
	public ResponseResult editById(ManagerForm managerForm) {

		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Manager manager = managerService.queryById(managerForm.getManagerId());
		if(manager == null){
			return ResponseResult.notFound();
		}
		manager.setManagerName(managerForm.getManagerName());	// 系统用户名
		manager.setManagerPhone(managerForm.getManagerPhone());	// 系统手机号
		manager.setMemo(managerForm.getMemo());		// 备注说明
		String companyId = managerForm.getCompanyId();
		if(StringUtils.isNotBlank(companyId)){
			manager.setCompany(companyService.queryById(companyId));	// 所属企业
		}else if (StringUtils.isNotBlank(managerForm.getCompanyName())){
			Company company = new Company();
			company.setCompanyName(managerForm.getCompanyName());
			companyService.save(company);
			manager.setCompany(company);
		}
		String areaId = managerForm.getAreaId();
		if(StringUtils.isNotBlank(areaId)){
			manager.setArea(areaService.queryById(areaId));// 所属区域
		}
		String siteId = managerForm.getSiteId();
		if(StringUtils.isNotBlank(siteId)){
			manager.setSite(siteService.queryById(siteId));// 所属工地
		}
		String disposalId = managerForm.getDisposalId();
		if(StringUtils.isNotBlank(disposalId)){
			manager.setDisposal(disposalService.queryByIDSimple(disposalId)); // 所属处置场
		}

		// 第二步、修改保存
		managerService.editById(manager);

		return ResponseResult.ok();

	}

	/**
	 * @Description: 根据系统用户id查询用户
	 * @author: 展昭
	 * @date: 2018年4月19日 下午3:27:59
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String managerId) {

		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Manager manager = managerService.queryById(managerId);
		if (manager == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(manager);
	}

	/**
	 * @Description: 用户登录
	 * @param: phone:手机号
	 *             password:密码
	 * @author: 展昭
	 * @date: 2018年4月26日 下午3:09:13
	 */
	@RequestMapping(value = "login.action", method = RequestMethod.POST)
	@Logger(operatorModel = "系统用户", operatorDesc = "用户登录")
	public ResponseResult login(HttpServletRequest request, ManagerLoginForm loginForm) {

		// 第一步：校验用户名是密码是否正确
		Manager manager = managerService.login(loginForm.getManagerPhone(), loginForm.getManagerPassword());
		if (manager != null) {
			// 第二步：如果不为空则放到session，并给前端用户一个反馈
			WebUtils.saveManagerToSession(manager, request);
			// 第三步：登录大华平台的配置信息
			DaHuaPlatFormConfig config = propertiesService.getDaHuaPlatFormConfig();
			// 第四步：常量信息(视频轮播时间、设备异常检测时间)
			SystemSettings systemSettings = systemSettingsService.query();

			// 第五步、组装信息返回前台
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("manager", manager);
			map.put("daHuaConfig", config);
			map.put("systemSettings", systemSettings);

			return ResponseResult.ok(map);
		}
		return ResponseResult.build(StatusCode.MANAGER_LOGIN_ERROR.getCode(),
				StatusCode.MANAGER_LOGIN_ERROR.getMessage());
	}

	/**
	 * @Description: 用户退出
	 * @author: 展昭
	 * @date: 2018年4月26日 下午3:58:46
	 */
	@RequestMapping(value = "logout.action", method = RequestMethod.GET)
	@Logger(operatorModel = "系统用户", operatorDesc = "用户退出")
	public ResponseResult logout(HttpServletRequest request) {
		WebUtils.removeManagerFromSession(request);
		return ResponseResult.ok();
	}
	
	
	/***
	 * 查询所有的用户 
	 * 		根据用户姓名和联系方式查询
	*/
	@RequestMapping(value = "queryManagers.action", method = RequestMethod.POST)
	public ResponseResult queryManagers(ManagerQueryForm managerQueryForm) {

		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Manager> pageView = null;

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");

		// 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据

		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "deleted = %d", false);

		// 用户手机号
		String managerPhone = managerQueryForm.getManagerPhone();
		if(StringUtils.isNotBlank(managerPhone)){
			queryHelper.addCondition(managerPhone + "%", "manager_phone like '%s'", false);
		}
		// 用户姓名
		String managerName = managerQueryForm.getManagerName();
		if(StringUtils.isNotBlank(managerName)){
			queryHelper.addCondition(managerName + "%", "manager_name like '%s'", false);
		}
		
		pageView = managerService.queryPageData(managerQueryForm.getPageNum(), managerQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);

		return ResponseResult.ok(pageView);
	}

	
	/**
	 * 	重置密码
	*/
	@RequestMapping(value = "resetPassword.action", method = RequestMethod.GET)
	public ResponseResult resetPassword(String managerId){
		
		Manager manager = managerService.queryById(managerId);
		if(manager == null){
			return ResponseResult.notFound();
		}
		manager.setManagerPassword(MD5Utils.encode(INIT_PASSWORD));
	
		// 更新
		managerService.editById(manager);
		
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 权限组设置,将某个用户授权给某个权限组
	 * @param:
	 * @author: 展昭
	 * @date: 2018年4月24日 下午3:12:40
	 */
	@RequestMapping(value = "userAuthorizedPrivilegeGroup.action", method = RequestMethod.POST)
	@Logger(operatorModel = "系统用户", operatorDesc = "权限组设置,将某个用户授权给某个权限组")
	public ResponseResult userAuthorizedPrivilegeGroup(ManagerForm managerForm) {

		// 1、校验是否选择用户
		String[] managerIds = managerForm.getManagerIds();
		if (managerIds == null || managerIds.length <= 0) {
			throw new BizException(StatusCode.MANAGER_MANAGERIDS_BLANK);
		}

		// 2、校验是否选择权限组
		String[] privilegeGroupIds = managerForm.getPrivilegeGroupIds();
		if (privilegeGroupIds == null || privilegeGroupIds.length <= 0) {
			throw new BizException(StatusCode.MANAGER_PRIVILEGEGROUPIDS_BLANK);
		}

		// 3、授权
		managerService.authorizedPrivilegeGroup(managerIds, privilegeGroupIds);

		return ResponseResult.ok();
	}

	/**
	 * 个人中心
	 * 		查看
	 */
	@RequestMapping(value = "queryManagerCenterInfo.action", method = RequestMethod.GET)
	public ResponseResult queryManagerCenterInfo(HttpServletRequest request) {

		// 第一步、从session中获取当前登录的用户
		Manager manager = WebUtils.getManagerFromSession(request);
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}

		// 第二步：根据当前用户获取用户的信息，包括(企业、权限组)
		manager = managerService.queryManagerCenterInfo(manager);

		return ResponseResult.ok(manager);
	}
	
	/**
	 * 个人中心的修改
	 */
	@RequestMapping(value = "editManagerCenterInfo.action", method = RequestMethod.POST)
	@Logger(operatorModel = "系统用户", operatorDesc = "个人中心的修改")
	public ResponseResult editManagerCenterInfo(HttpServletRequest request, ManagerForm managerForm) {

		// 第一步、从session中获取当前登录的用户
		Manager manager = WebUtils.getManagerFromSession(request);
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 第二步、设置基本信息
		manager.setManagerName(managerForm.getManagerName());	// 系统用户名
		manager.setManagerPhone(managerForm.getManagerPhone());	// 系统手机号
		manager.setMemo(managerForm.getMemo());		// 备注说明
		String companyId = managerForm.getCompanyId();
		if(StringUtils.isNotBlank(companyId)){
			manager.setCompany(companyService.queryById(companyId));	// 所属企业
		}
		String areaId = managerForm.getAreaId();
		if(StringUtils.isNotBlank(areaId)){
			manager.setArea(areaService.queryById(areaId));				// 所属区域
		}
		String siteId = managerForm.getSiteId();
		if(StringUtils.isNotBlank(siteId)){
			manager.setSite(siteService.queryByIDSimple(siteId)); 		// 所属工地
		}
		String disposalId = managerForm.getDisposalId();
		if(StringUtils.isNotBlank(disposalId)){
			manager.setDisposal(disposalService.queryByIDSimple(disposalId)); // 所属处置场
		}

		// 第三步、修改用户个人中心
		managerService.editManagerCenterInfo(manager, managerForm.getPrivilegeGroupIds());

		return ResponseResult.ok();
	}

	/**
	 * @Description: 修改密码的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月21日  下午1:54:14
	 * @param: managerForm 传入一个新密码
	 * @return:ResponseResult 返回是否修改成功
	 */
	@RequestMapping(value = "editPassword.action" , method = RequestMethod.POST)
	public ResponseResult editPassword(String password,HttpServletRequest request){
		
		Manager manager = WebUtils.getManagerFromSession(request);
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		manager.setManagerPassword(MD5Utils.encode(password));
		managerService.editById(manager);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 校验密码确定性
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月21日  下午2:02:42
	 * @param: password 传入一个密码
	 * @return:ResponseResult 返回校验的结果
	 */
	@RequestMapping(value = "verifyPassword.action" , method = RequestMethod.POST)
	public ResponseResult verifyPassword(String password,HttpServletRequest request){
		if(StringUtils.isBlank(password)){
			throw new BizException(StatusCode.MANAGER_PASSWORD_BLANK);
		}
		
		Manager manager = WebUtils.getManagerFromSession(request);
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		String phone = manager.getManagerPhone();
		Manager mana = managerService.login(phone,password);
		if(mana != null){
			return ResponseResult.build(StatusCode.MANAGER_PASSWRD_VERIFY_SUCCESS.getCode(),StatusCode.MANAGER_PASSWRD_VERIFY_SUCCESS.getMessage());
		}else {
			return ResponseResult.build(StatusCode.MANAGER_PASSWRD_VERIFY_FAIL.getCode(),StatusCode.MANAGER_PASSWRD_VERIFY_FAIL.getMessage());
		}
	}
}
