package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.CarDriver;
import com.muck.domain.CarDriverFamilyMember;

/**
 * 家庭成员Mapper
*/
@Repository
public interface CarDriverFamilyMemberMapper extends BaseMapper<CarDriverFamilyMember>{

	/**
	 * 	批量添加车辆驾驶员的家庭成员
	*/
	public void insertBatch(@Param("carDriver")CarDriver carDriver,@Param("members")List<CarDriverFamilyMember> members);

	/**
	 *	根据车辆驾驶员id删除家庭成员表中与之车辆驾驶员对应的家庭成员 
	*/
	public void deleteByCarDriverIdReal(@Param("carDriverId")String carDriverId);
	/**
	 * @Description: 维护驾驶员和家庭成员的关系
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月20日 下午12:14:14
	 */
	public void setCarDriverAndCarDriverFamilyMemberRelation();

}