package com.muck.service;
import java.util.List;

import com.muck.domain.Manager;

/**
* @Description: 系统用户Service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 上午11:11:10
 */
public interface ManagerService extends BaseService<Manager>{

	/**
	* @Description: 该函数的功能描述
	* @param: managerIds:用户ids
	* 		  privilegeGroupIds:权限组ids
	* @author: 展昭
	* @date: 2018年4月25日 上午11:38:43
	 */
	public void authorizedPrivilegeGroup(String[] managerIds, String[] privilegeGroupIds);

	/**
	* @Description: 用户登录
	* @param: managerPhone: 手机号
	* 		  managerPassword : 密码
	* @author: 展昭
	* @date: 2018年4月26日 下午3:29:35
	*/
	public Manager login(String managerPhone, String managerPassword);

	/**
	* @Description: 根据用户查询用户信息(企业,权限组)
	* @author: 展昭
	* @date: 2018年4月26日 下午5:35:44
	 */
	public Manager queryManagerCenterInfo(Manager manager);

	/**
	 * 
	* @Description: 修改个人中心
	* @author: 展昭
	* @date: 2018年4月27日 上午10:47:21
	*/
	public void editManagerCenterInfo(Manager manager,String[] privilegeGropuIds);
	/**
	 * @Description: 根据负责人姓名查询负责人信息
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月3日 上午9:43:17
	 * @param: managerName 传入一个负责人姓名
	 * @return: List<Manager> 返回含有负责人信息的列表
	 */
	public List<Manager> queryByName(String managerName);
	/**
	 * @Description: 根据负责人联系方式查询负责人信息
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月3日 上午9:43:23
	 * @param: managerPhone 传入一个负责人联系方式
	 * @return: List<Manager> 返回含有负责人信息的列表
	 */
	public List<Manager> queryByPhone(String managerPhone);
}
