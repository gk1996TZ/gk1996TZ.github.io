package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.CarDriver;

/**
 *	驾驶员Mapper
*/
@Repository
public interface CarDriverMapper extends BaseMapper<CarDriver>{
	/**
	 * @Description: 根据驾驶员身份证号查询驾驶员信息
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月21日  下午5:10:35
	 * @Param: idNumber 传入一个驾驶员身份证号
	 * @Return:CarDriver 返回驾驶员的信息
	 */
	CarDriver queryByIdNumber(@Param("idNumber")String idNumber);
	
	/**
	 * @Description: 根据车牌号查询驾驶员
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月9日  下午9:01:12
	 * @param: carCode 传入一个车牌号
	 * @return:List<CarDriver> 返回驾驶员
	 */
	List<CarDriver> queryByCarCode(@Param("carCode")String carCode);
	/**
	 * @Description: 通过手机号查询驾驶员 
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月3日 上午10:54:56
	 * @Param: phone 传入一个手机号
	 * @Return: CarDriver 返回一个驾驶员
	 */
	CarDriver queryByPhone(@Param("phone")String phone);
	
	/**
	 * @Description: 维护企业和驾驶员的关系
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月20日 下午12:13:42
	 */
	public void setCompanyAndCarDriverRelation();
}