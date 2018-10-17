package com.xumengba.admin.controller;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.admin.utils.WebUtils;
import com.xumengba.domain.User;
import com.xumengba.helper.QueryHelper;
import com.xumengba.page.PageView;
import com.xumengba.query.UserQueryForm;
import com.xumengba.request.UserForm;
import com.xumengba.request.UserPwdForm;
import com.xumengba.response.ResponseResult;
import com.xumengba.response.StatusCode;
import com.xumengba.service.UserService;
import com.xumengba.utils.MD5Utils;
@RestController("AdminUserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController{

	@Autowired
	UserService userService;
	/**
	 * 根据id查询用户信息
	 * @param id 返回用户对象
	 * @return 
	 */
	@RequestMapping("selectById.action")
	public ResponseResult getById(String id){
		return ResponseResult.ok(userService.queryById(id));
	}
	/**
	 * 根据id真实删除
	 * @param id 传入一个id
	 * @return 返回操作信息
	 */
	@RequestMapping(value="deleteByIdReal.action",method=RequestMethod.GET)
	public ResponseResult deleteByIdReal(String id){
		userService.deleteByIdReal(id);
		return ResponseResult.ok();
	}
	/**
	 * 根据id修改
	 * @param user 传入一个需要被修改的用户对象
	 * @return 返回操作信息
	 */
	@RequestMapping("editById.action")
	public ResponseResult editById(User user){
		userService.editById(user);
		return ResponseResult.ok();
	}
	/**
	 * 获取用户分页数据
	 * @return 返回用户分页数据
	 */
	@RequestMapping("getUsers.action")
	public ResponseResult getUsers(UserQueryForm userForm){
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<User> pageView = null;

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "deleted = %d", false);
		
		pageView = userService.queryPageData(userForm.getPageNum(), userForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}
	
	//用户修改密码

	/**
	 * @Description:
	 * @author:甘坤
	 * @date: 2018年10月10日 上午11:25:32
	 * @param: userForm
	 * @return: ResponseResult
	 */
	@RequestMapping(value="changePassword.action",method=RequestMethod.POST)
	public  ResponseResult changePassword(UserPwdForm userForm,HttpServletRequest request){
		//修改密码
		userService.changePwd(userForm.getUserName(), userForm.getOldPwd(), userForm.getNewPwd());
		//将session中的user移除
		WebUtils.removeManagerFromSession(request);
		return ResponseResult.ok();
	}
	
	/**
	 * @Description:
	 * @author:甘坤
	 * @date: 2018年10月10日 下午12:00:58
	 * @param: @param request
	 * @param: @param userName
	 * @param: @param password
	 * @param: @return
	 * @return: ResponseResult
	 */
	@RequestMapping(value = "login.action", method = RequestMethod.POST)
	public ResponseResult login(HttpServletRequest request, String userName, String password) {

		// 1.根据用户名查询用户
		User user = userService.login(userName,MD5Utils.encode(password));

		if (user != null) {
			// 第二步：如果不为空则放到session，并给前端用户一个反馈
			WebUtils.saveManagerToSession(user, request);
			
			return ResponseResult.ok(user.getId());
		}
		return ResponseResult.build(StatusCode.USER_LOGIN_ERROR.getCode(),
				StatusCode.USER_LOGIN_ERROR.getMessage());
	}
	
	/**
	 * @Description:用户退出登录
	 * @author:甘坤
	 * @date: 2018年10月10日 上午11:31:25
	 * @param: @param request
	 * @param: @return
	 * @return: ResponseResult
	 */
	@RequestMapping(value="exit.action")
	public ResponseResult exit(HttpServletRequest request){
		
		WebUtils.removeManagerFromSession(request);
		return ResponseResult.ok();
	}
	
	
	/**
	 * @Description:添加用户
	 * @author:甘坤
	 * @date: 2018年10月10日 下午12:03:50
	 * @param: @param userForm
	 * @param: @return
	 * @return: ResponseResult
	 */
	@RequestMapping(value="addUser.action")
	public ResponseResult addUser(UserForm userForm){
		User user=new User();
		BeanUtils.copyProperties(userForm, user);
		userService.save(user);
		return ResponseResult.ok();
	}

	@RequestMapping(value="updateUser.action",method=RequestMethod.POST)
	public ResponseResult updateUser(User user){
		
		userService.editById(user);
		return ResponseResult.ok();
		
	}
}
