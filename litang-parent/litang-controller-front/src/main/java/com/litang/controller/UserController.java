package com.litang.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.litang.domain.Role;
import com.litang.domain.User;
import com.litang.domain.UserInfo;
import com.litang.exception.base.BizException;
import com.litang.facade.UserState;
import com.litang.helper.QueryHelper;
import com.litang.page.PageView;
import com.litang.query.UserQueryForm;
import com.litang.request.UserForm;
import com.litang.request.UserInfoForm;
import com.litang.request.UserPosition;
import com.litang.response.ResponseResult;
import com.litang.response.StatusCode;
import com.litang.service.PropertiesService;
import com.litang.service.RoleService;
import com.litang.service.UserInfoService;
import com.litang.service.UserService;
import com.litang.service.impl.UserPositionPool;
import com.litang.utils.MD5Utils;
import com.litang.utils.NarrowImage;
import com.litang.utils.UploadUtil;

/**
 * 用户控制层
 */
@RestController("FrontUserController")
@RequestMapping("/front/user/")
public class UserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private PropertiesService propertiesService;
	
	@Autowired
	private RoleService roleService;

	/**
	 * @Param: userQueryForm 传入一个用户请求实体
	 * @Param: session 传入一个HttpSession
	 * @Return: 返回登录操作的信息
	 */
	@RequestMapping("login.action")
	public ResponseResult login(UserQueryForm userQueryForm, HttpSession session) {

		// 获取缓存中的token，如果token存在，则直接获取user，并返回user
		/*
		 * String token = JedisUtils.getStr(RedisPrefix.TOKEN_CODE_PRE +
		 * userQueryForm.getUserTokenCode()); if (token != null) { User user =
		 * (User) JedisUtils.getObject(RedisPrefix.USER_PRE +
		 * userQueryForm.getUserName()); return ResponseResult.jumpLogin(user);
		 * }
		 */
		String token = userQueryForm.getUserTokenCode();
		if (!StringUtils.isBlank(token)) {
			User user = userService.queryByUserTokenCode(token);
			if (user != null) {
				return ResponseResult.jumpLogin(user);
			}
		}
		String userName = userQueryForm.getUserName();
		String userPwd = userQueryForm.getUserPwd();

		// 判断用户名并且密码不为空
		if (StringUtils.isBlank(userName) | StringUtils.isBlank(userPwd)) {
			StatusCode statusCode = StatusCode.NAME_OR_PWD_IS_NULL;
			return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
		}
		// 根据用户名获取用户
		User user = userService.queryByUserName(userName);
		// 如果用户为空，则返回用户不存在的信息
		if (user == null) {
			StatusCode statusCode = StatusCode.USER_NOT_EXISTS;
			return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
		}
		// 用户禁用状态
		if (user.getUserState() == UserState.USER_REVIEW_DISABLE) {
			StatusCode statusCode = StatusCode.USER_DISABLE;
			return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
		}
		// 如果用户存在，则判断密码是否正确
		if (!MD5Utils.encode(userPwd).equals(user.getUserPwd())) {
			StatusCode statusCode = StatusCode.PASSWORD_IS_NOT_TRUE;
			return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
		}
		// 如果密码正确，则生成用户唯一的token

		if (StringUtils.isBlank(token)) {
			String userTokenCode = MD5Utils.encode(userName);
			user.setUserTokenCode(userTokenCode);
			// 修改成功后，将用户更新后的用户信息放到redis中，并将用户token返回给前台
			if (userService.editById(user)) {
				/*
				 * JedisUtils.setObject(RedisPrefix.USER_PRE + userName, user);
				 * JedisUtils.setStr(RedisPrefix.TOKEN_CODE_PRE + userName, 60 *
				 * 60 * 24 * 7, userTokenCode);
				 */
				return ResponseResult.ok(user);
			}
		} else {
			return ResponseResult.ok(user);
		}
		StatusCode statusCode = StatusCode.ERROR;
		return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
	}

	/**
	 * 根据用户id查询用户详情
	 * 
	 * @param userId
	 *            传入一个用户id
	 * @return 返回用户信息
	 */
	@RequestMapping("queryById.action")
	public ResponseResult queryById(String userId) {
		if (StringUtils.isBlank(userId)) {
			StatusCode statusCode = StatusCode.USER_ID_NULL;
			return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
		}

		User user = userService.queryById(userId);
		if (user == null) {
			StatusCode statusCode = StatusCode.NOT_FOUND;
			return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
		}
		return ResponseResult.ok(user);
	}

	/**
	 * 根据id修改用户的操作
	 * 
	 * @param userForm
	 *            传入用户信息
	 * @return 返回修改获取用户的信息
	 */
	@RequestMapping("editByUserId.action")
	public ResponseResult editById(UserInfoForm userInfoForm) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userInfoForm.getUserId());
		String userHead = userInfoForm.getUserHead();
		if (!StringUtils.isBlank(userHead)) {
			userInfo.setUserHead(userHead);
		}
		String userRealName = userInfoForm.getUserRealName();
		if (!StringUtils.isBlank(userRealName)) {
			userInfo.setUserRealName(userRealName);
		}
		String userJob = userInfoForm.getUserJob();
		if (!StringUtils.isBlank(userJob)) {
			userInfo.setUserJob(userJob);
		}
		String userDescribe = userInfoForm.getUserDescribe();
		if (!StringUtils.isBlank(userDescribe)) {
			userInfo.setUserDescribe(userDescribe);
		}
		String userPhone = userInfoForm.getUserPhone();
		if (!StringUtils.isBlank(userPhone)) {
			userInfo.setUserPhone(userPhone);
		}
		if (userInfoService.editByUserId(userInfo)) {
			/*
			 * User user = userService.queryById(userInfoForm.getUserId()); try
			 * { String userName = user.getUserName(); user = (User)
			 * JedisUtils.getObject(RedisPrefix.USER_PRE + userName);
			 * user.setUserInfo(userInfoService.queryByUserId(userInfoForm.
			 * getUserId())); JedisUtils.setObject(RedisPrefix.USER_PRE +
			 * userName, user); JedisUtils.del(RedisPrefix.USER_PRE + userName);
			 * JedisUtils.setObject(RedisPrefix.USER_PRE + userName, user); }
			 * catch (NullPointerException e) { logger.info("用户id为：" +
			 * userInfoForm.getUserId() + "在修改用户详情时出现修改缓存内用户详情的异常"); StatusCode
			 * statusCode = StatusCode.ERROR; return new
			 * ResponseResult(statusCode.getCode(), statusCode.getMessage()); }
			 */
			return ResponseResult.ok(userInfo);
		}
		StatusCode statusCode = StatusCode.ERROR;
		return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
	}

	/**
	 * 修改用户头像
	 * 
	 * @param request
	 *            传入一个HttpServletRequest 
	 * @return 返回用户头像路径
	 */
	@RequestMapping("editUserHead.action")
	public ResponseResult editUserHead(HttpServletRequest request) {
		List<NarrowImage> paths = UploadUtil.uploadImages(propertiesService.getWindowsRootPath(), "user",
				propertiesService.getCurrentServerForImage(), request);
		return ResponseResult.ok(paths);
	}

	/**
	 * 用户注册 注册同时维护用户信息
	 */
	@RequestMapping(value = "register.action", method = RequestMethod.POST)
	public ResponseResult register(UserQueryForm userQueryForm) {
		// 首先判断用户唯一性
		String userName = userQueryForm.getUserName();
		User userDB = userService.queryByUserName(userName);
		// 用户不为空说明用户已存在无法注册
		if (userDB != null) {
			// 用户禁用状态
			if (userDB.getUserState() == UserState.USER_REVIEW_DISABLE) {
				StatusCode statusCode = StatusCode.USER_DISABLE;
				return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
			}
		}
		// 说明用户名还不存在
		String password = MD5Utils.encode(userQueryForm.getUserPwd());
		String phoneNum = userQueryForm.getUserPhone();

		// 1.创建user对象封装信息
		User user = new User();
		user.setUserName(userName);
		user.setUserPhone(phoneNum);
		user.setUserPwd(password);

		// 设置用户状态为禁用
		user.setUserState(UserState.USER_REVIEW_DISABLE);

		userService.insert(user);

		// 2.维护userInfo
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user.getId());
		userInfo.setUserPhone(phoneNum);
		userInfo.setUserHead(propertiesService.getDefaultHeadPath());
		userInfoService.insert(userInfo);

		// 维护缓存中注册用户总数
		/*
		 * Object registerCount =
		 * JedisUtils.getObject(RedisPrefix.USER_REGISTER); Integer oR = 0; if
		 * (registerCount != null && registerCount instanceof Integer) { oR =
		 * (Integer) registerCount; }
		 * JedisUtils.setObject(RedisPrefix.USER_REGISTER, oR++); //
		 * 维护缓存中审核未通过总数 Object auditNotTransit =
		 * JedisUtils.getObject(RedisPrefix.USER_AUDIT_NOT_TRANSIT); Integer oA
		 * = 0; if (auditNotTransit != null && auditNotTransit instanceof
		 * Integer) { oA = (Integer) auditNotTransit; }
		 * JedisUtils.setObject(RedisPrefix.USER_AUDIT_NOT_TRANSIT, oA++);
		 */
		return ResponseResult.ok(user);

	}
	
	
	/**
	 * 删除用户
	 * */
	@RequestMapping(value="deleteById.action",method=RequestMethod.GET)
	public ResponseResult deleteById(String userId){
		if(StringUtils.isNotBlank(userId)){
			userService.deleteById(userId);
			return ResponseResult.ok();
		}
		throw new BizException(StatusCode.USER_ID_NULL);
		
	}

	/**
	 * 根据用户名查询用户是否已经存在
	 */
	@RequestMapping(value = "validateExist.action", method = RequestMethod.GET)
	public ResponseResult validateExist(String userName) {
		if (StringUtils.isNotBlank(userName)) {
			User user = userService.queryByUserName(userName);
			if (user != null) {
				// 用户审核通过状态
				if (user.getUserState() == UserState.USER_REVIEW_DISABLE) {
					StatusCode statusCode = StatusCode.USER_DISABLE;
					return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
					// 用户待审核状态
				} else if (user.getUserState() == UserState.USER_REVIEW_ENABLE) {
					StatusCode statusCode = StatusCode.USER_ENABLE;
					return new ResponseResult(statusCode.getCode(), statusCode.getMessage());
					// 用户审核不通过状态
				}
			}
			return ResponseResult.ok();
		}
		throw new BizException(StatusCode.NAME_OR_PWD_IS_NULL);

	}

	/**
	 * 查询待审核用户 带分页数据
	 */
	@RequestMapping(value = "queryUsersByState.action", method = RequestMethod.POST)
	public ResponseResult queryUsersByState(Integer userState, Long pageNum, Long pageSize,String userName) {

		QueryHelper queryHelper = new QueryHelper();
		// 排序规则
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("user.created_time", "desc");

		if (userState != null) {
			// 查询实体
			queryHelper.addCondition(userState, "user.user_state=%d", false);

		}
		if(StringUtils.isNotBlank(userName)){
			queryHelper.addCondition(userName, "user.user_name !='%s'", false);
		}
		queryHelper.addCondition(1, "user.deleted=%d", false);

		// 查询分页数据
		PageView<User> pageView = userService.queryPageData(pageNum, pageSize, queryHelper.getWhereSQL(),
				queryHelper.getWhereParams(), orderby);
		if (pageView == null) {
			ResponseResult.notFound();
		}
		return ResponseResult.ok(pageView);

	}

	/**
	 * 禁用用户接口
	 */
	@RequestMapping(value = "userDisable.action", method = RequestMethod.GET)
	public ResponseResult userPass(String userId) {

		// 设置用户审核通过
		User user = new User();
		user.setId(userId);
		user.setUserState(UserState.USER_REVIEW_DISABLE);
		userService.editById(user);

		/*
		 * // 维护缓存中审核未通过总数 Object auditNotTransit =
		 * JedisUtils.getObject(RedisPrefix.USER_AUDIT_NOT_TRANSIT);
		 * 
		 * Integer oA = 0; if (auditNotTransit != null && auditNotTransit
		 * instanceof Integer) { oA = (Integer) auditNotTransit; } if (oA > 0) {
		 * JedisUtils.setObject(RedisPrefix.USER_AUDIT_NOT_TRANSIT, oA--); }
		 * else { JedisUtils.setObject(RedisPrefix.USER_AUDIT_NOT_TRANSIT, 0); }
		 * 
		 * // 维护缓存通过总数 Object auditTransit =
		 * JedisUtils.getObject(RedisPrefix.USER_AUDIT_TRANSIT);
		 * 
		 * Integer aT = 0; if (auditTransit != null && auditTransit instanceof
		 * Integer) { aT = (Integer) auditTransit; }
		 * JedisUtils.setObject(RedisPrefix.USER_AUDIT_TRANSIT, aT++);
		 */

		return ResponseResult.ok();

	}

	/**
	 * 启用用户接口
	 */
	@RequestMapping(value = "userEnable.action", method = RequestMethod.GET)
	public ResponseResult userNotPass(String userId) {
		// 设置用户审核不通过
		User user = new User();
		user.setId(userId);
		user.setUserState(UserState.USER_REVIEW_ENABLE);
		userService.editById(user);

		return ResponseResult.ok();
	}

	/**
	 * 用户退出登录
	 */

	@RequestMapping(value = "existLogin.action", method = RequestMethod.GET)
	public ResponseResult existLogin(String userName) {

		/**
		 * String userId = null; User user = null; try{ user = (User)
		 * JedisUtils.getObject(RedisPrefix.USER_PRE + userName); if(user !=
		 * null){ userId = user.getId(); } }catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * if(userId == null){ user = userService.queryByUserName(userName);
		 * if(user != null){ userId = user.getId(); } }
		 ***/

		User user = userService.queryByUserName(userName);
		if (user != null) {
			UserPositionPool.remove(new UserPosition(user.getId()));
		}

		/*
		 * try { // 删除缓存的token //JedisUtils.del(RedisPrefix.TOKEN_CODE_PRE +
		 * userName); } catch (Exception e) { e.printStackTrace(); } //
		 * 删除缓存的token /*JedisUtils.del(RedisPrefix.TOKEN_CODE_PRE + userName);
		 */
		return ResponseResult.ok();
	}

	/**
	 * 用户修改密码
	 */

	@RequestMapping(value = "userChangePwd.action", method = RequestMethod.POST)
	public ResponseResult userChangePwd(UserForm userForm) {
		String userName = userForm.getUserName();
		User user = userService.queryByUserName(userName);
		if (user == null) {
			throw new RuntimeException("找不到用户名为:" + userName + "的用户");
		}
		String pwd = user.getUserPwd();
		// 判断用户输入的密码是否正确
		if (MD5Utils.encode(userForm.getOldPwd()).equals(pwd)) {
			// 说明输入的密码真确,继续判断旧密码和新密码是否一致
			if (!userForm.getNewPwd().equals(userForm.getOldPwd())) {
				// 加密并设置新密码
				user.setUserPwd(MD5Utils.encode(userForm.getNewPwd()));

				// 修改数据库数据
				userService.editById(user);

				// 维护缓存数据
//				JedisUtils.setObject(RedisPrefix.USER_PRE + userName, user);

				return ResponseResult.ok();

			} else {
				// 说明新旧密码一致
				throw new BizException(StatusCode.USER_PWD_SAME);
			}
		}

		throw new BizException(StatusCode.PASSWORD_IS_NOT_TRUE);

	}
	/**
	 * 重置密码
	 * @param userId 传入一个用户id
	 * @return 返回操作信息
	 */
	@RequestMapping("resetPwd.action")
	public ResponseResult resetPwd(String userId){
		userService.resetPwd(userId);
		return ResponseResult.ok("新密码为:"+propertiesService.getDefaultNewPwd());
	}
	
	
	/**
	 * 为用户分配角色
	 * 
	 * */
	@RequestMapping(value="setRoleForUser.action",method=RequestMethod.POST)
	public ResponseResult setRoleForUser(String userId,String roleId){
		
		//用户id和角色id都不能为空
		if(StringUtils.isNotBlank(userId)){
			
			if(StringUtils.isBlank(roleId)){
				//没传角色id,默认把该用户的角色删除
				userService.deleteUserRoleByUserId(userId);
				return ResponseResult.ok();
			}
			Role role=roleService.queryRoleBySimple(roleId);
			if(role==null){
				throw new BizException(StatusCode.ROLE_NOT_EXIST);
			}
			User user=userService.queryById(userId);
			
			if(user==null){
				throw new BizException(StatusCode.USER_NOT_EXISTS);
			}
			//说明是有该角色并且也存在该用户,为用户赋予该角色
			if(user.getListRole().size()<=0){
				//说明用户还没有角色,为用户分配
				userService.setUserRole(userId, roleId);
			}else{
				//说明用户是有角色的,更新用户角色
				userService.updateUserRole(userId, roleId);
			}
			return ResponseResult.ok();
			
		}
		throw new BizException(StatusCode.USER_ID_NULL);
		
	}

	
}
