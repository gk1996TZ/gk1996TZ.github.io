package com.litang.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litang.domain.User;
import com.litang.mapper.BaseMapper;
import com.litang.mapper.PostMessageMapper;
import com.litang.mapper.UserMapper;
import com.litang.page.PageView;
import com.litang.service.PropertiesService;
import com.litang.service.UserService;
import com.litang.utils.CollectionUtils;
import com.litang.utils.MD5Utils;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PropertiesService propertiesService;
	
	@Autowired
	private PostMessageMapper postMessageMapper;
	/**
	 * 根据用户名查询用户是否存在
	 */
	public User queryByUserName(String userName) {

//		User user = (User) JedisUtils.getObject(RedisPrefix.USER_PRE + userName);
//		if (user != null) {
//			return user;
//		}
		User user = userMapper.selectByUserName(userName);
		/*if (user != null) {
			JedisUtils.setObject(RedisPrefix.USER_PRE + userName, user);
		}*/

		return user;

	}

	@Override
	public User queryByUserTokenCode(String userTokenCode) {
		return null;
	}


	@Override
	protected BaseMapper<User> getMapper() {
		return userMapper;
	}

	@Override
	protected String getFields() {
		return " * ";
	}

	// 根据状态查询用户
	public List<User> queryUsersByUserState(int userState) {
		// //首先从缓存中获取
		// Object obj = JedisUtils.getObject(RedisPrefix.USER_PRE+userState);
		// if(obj != null){
		// if(obj instanceof ArrayList){
		// List<Object> list = (List<Object>) obj;
		// if(list.size() > 0){
		// Object o = list.get(0);
		// if(o instanceof User){
		// return (List<User>)obj;
		// }
		// }
		// }
		// }
		// //将数据放入缓存
		// if(users!=null){
		// JedisUtils.setObject(RedisPrefix.USER_PRE+userState, users);
		// }
		// 查询数据库
		List<User> users = userMapper.selectUserByUserState(userState);
		return CollectionUtils.toList(users);
	}

	@Override
	public PageView<User> queryPageData() {
		return this.queryPageData(-1L, -1L, null, null, null);
	}

	/**
	 * 查询带分页数据
	 */
	@Override
	public PageView<User> queryPageData(Long currentPageNum, Long pageSize, String whereSQL, List<Object> whereParams,
			LinkedHashMap<String, String> orderBy) {
		String limit = super.buildLimit(currentPageNum, pageSize);
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";

		// 第四步、获取orderby条件
		String orderby = buildOrderby(orderBy);
		// 用来查询数据总数的sql
		String sql = "select count(user.id) from t_user user where 1 = 1 " + whereSQL;

		String selectSQL = "select " + "user.id," + "user.user_name," + "user.user_pwd," + "user.user_phone as "
				+ "userphone," + "user.user_state," + "user.user_token_code," + "user.created_time,"
				+ "user.updated_time," + "userinfo.user_sex," + "userinfo.user_brith," + "userinfo.user_realname,"
				+ "userinfo.user_head," + "userinfo.user_job," + "userinfo.user_describe,"
				+ "userinfo.user_phone as userinfophone " + "from " + "t_user user left join "
				+ "t_userinfo userinfo on user.id = userinfo.user_id where 1 = 1 " + whereSQL + orderby + limit;

		// 查询总数
		sql = super.setQueryParams(sql, whereParams, null);
		Long count = userMapper.selectTotalRecoreds(sql);

		PageView<User> pageView = new PageView<User>(count, currentPageNum, pageSize);

		// 查询记录
		selectSQL = super.setQueryParams(selectSQL, whereParams, pageView);
		List<User> postMessages = userMapper.selectPageData(selectSQL);
		pageView.setDatas(postMessages);

		return pageView;
	}

	@Override
	public boolean resetPwd(String userId) {
		return userMapper.resetPwd(userId,MD5Utils.encode(propertiesService.getDefaultNewPwd())) == 1;
	}

	
	/**
	 * 为用户添加权限
	 * */
	public void setUserRole(String userId, String roleId) {
		
		userMapper.setRoleForUser(userId, roleId);
	}

	
	/**
	 * 为用户修改权限
	 * */
	
	public void updateUserRole(String userId, String roleId) {
		
		userMapper.updateUserRole(userId, roleId);
		
	}
	
	@Override
	public boolean deleteById(String userId) {
		
		//逻辑删除了用户,并且逻辑删除用户发布的信息
		//根据用户id删除用户发布信息
		postMessageMapper.deleteByUserId(userId);
		
		//删除这个用户被赋予的权限
		userMapper.deleteUserRole(userId);
		
		return super.deleteById(userId);
	}

	//删除用户角色
	@Override
	public void deleteUserRoleByUserId(String userId) {
		
		userMapper.deleteUserRole(userId);
		
	}
}
