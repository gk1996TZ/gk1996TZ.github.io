package com.muck.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Manager;
import com.muck.exception.base.BizException;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AccreditService;
import com.muck.service.ManagerService;

/**
 * @Description: 授权controller
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年7月16日 下午4:31:26
 */
@RestController("AdminAccreditController")
@RequestMapping("/admin/accredit")
public class AccreditController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ManagerService managerService;
	@Autowired
	private AccreditService accreditService;
	
	/**
	 * @Description: 授权的操作
	 * @Version: v1.0.0
	 * @Author 朱俊亮
	 * @Date: 2018年7月16日 下午4:32:04
	 * @Param: managerId 传入一个用户id
	 * @Param: privilegeGroupIds 传入一个权限组数组
	 * @Param: session 传入一个HttpSession
	 * @Return: ResponseResult 返回授权的操作
	 */
	@RequestMapping("accredit.action")
	public ResponseResult accredit (String managerId,String [] privilegeGroupIds,HttpSession session){
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		Manager manage = managerService.queryById(managerId); 
		if(manage == null){
			logger.info("用户:"+manager.getManagerName()+" 联系方式为:"+manager.getManagerPhone() +"的用户，在给用户id为:"+managerId+"的用户添加权限时，在数据库中未找到该用户，时间是："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			throw new BizException(StatusCode.NOT_FOUND);
		}
		if(privilegeGroupIds == null | privilegeGroupIds.length ==0){
			throw new BizException(StatusCode.PRIVILEGE_GROUP_ID_BLANK);
		}
		accreditService.insertBatch(managerId, Arrays.asList(privilegeGroupIds));
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 撤销授权的操作
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年7月16日 下午6:04:34
	 * @Param: managerId 传入一个用户id
	 * @Param: privilegeGroupIds 传入一个权限组列表
	 * @Param: session 传入一个HttpSession
	 * @Return: ResponseResult 返回操作的信息
	 */
	@RequestMapping("repealAccredit.action")
	public ResponseResult repealAccredit (String managerId,String [] privilegeGroupIds,HttpSession session){
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		Manager manage = managerService.queryById(managerId);
		if(manage == null){
			logger.info("用户:"+manager.getManagerName()+" 联系方式为:"+manager.getManagerPhone() +"的用户，在给用户id为:"+managerId+"的用户删除权限时，在数据库中未找到该用户，时间是："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			throw new BizException(StatusCode.NOT_FOUND);
		}
		if(privilegeGroupIds == null | privilegeGroupIds.length ==0){
			throw new BizException(StatusCode.PRIVILEGE_GROUP_ID_BLANK);
		}
		accreditService.deleteBatch(managerId, Arrays.asList(privilegeGroupIds));
		return ResponseResult.ok();
	}
}
