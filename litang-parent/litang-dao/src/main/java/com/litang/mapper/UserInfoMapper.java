package com.litang.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.litang.domain.UserInfo;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

	/**
	 * 通过用户id修改用户详情
	 * @param userId 传入一个用户id
	 * @return 返回是否修改成功
	 */
	public boolean editByUserId(UserInfo userInfo);
	/**
	 * 根据用户id查询用户详情
	 * @param userId 传入一个用户id
	 * @return 返回用户详情
	 */
	public UserInfo queryByUserId(@Param("userId")String userId);
}
