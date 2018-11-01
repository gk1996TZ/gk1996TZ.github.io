package com.muck.admin.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.muck.domain.Manager;
import com.muck.exception.base.BizException;
import com.muck.response.StatusCode;

/**
* @Description: 前端工具类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月26日 下午3:31:49
 */
public final class WebUtils {

	private static final String MANAGER_KEY = "manager";
	
	private WebUtils(){}
	
	/**
	* @Description: 将用户保存到session中
	* @author: 展昭
	* @date: 2018年4月26日 下午3:33:40
	*/
	public static void saveManagerToSession(Manager manager,HttpServletRequest request){
		request.getSession().setMaxInactiveInterval(60*60*24*30);
		request.getSession().setAttribute(MANAGER_KEY, manager);
	}
	
	/**
	* @Description: 从session中获取用户
	* @author: 展昭
	* @date: 2018年4月26日 下午3:35:20
	*/
	public static Manager getManagerFromSession(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		if(session == null){
			throw new BizException(StatusCode.SESSION_VALIDATE);
		}
		Manager manager = (Manager)session.getAttribute(MANAGER_KEY);
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_TOKEN_VALIDATE);
		}
		return manager;
	}
	
	/**
	* @Description: 从session中移除用户
	* @author: 展昭
	* @date: 2018年4月26日 下午3:35:20
	*/
	public static void removeManagerFromSession(HttpServletRequest request){
		HttpSession session =  request.getSession(false);
		if(session != null){
			session.removeAttribute(MANAGER_KEY);
		}
	}
}
