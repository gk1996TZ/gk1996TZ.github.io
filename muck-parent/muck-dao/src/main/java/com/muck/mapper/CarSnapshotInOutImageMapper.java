package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.CarSnapshotInOutImage;
import com.muck.response.CarSnapshotResponse;

/***
 * 	车辆抓拍进出工地和处置场Mapper
*/
@Repository
public interface CarSnapshotInOutImageMapper extends BaseMapper<CarSnapshotInOutImage>{

	/**
	 * @Description: 通过车牌号查询指定年月日当天的该车牌号进或者出时的次数
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  上午12:37:02
	 * @param: day 传入指定的年月日
	 * @param: carCode 传入一个车牌号
	 * @param: relevantName 传入抓拍的处置场或工地名称
	 * @return:Integer 返回指定的次数
	 */
	public Integer queryCarSnapshotCountOneDay(@Param("day")String day,@Param("carCode")String carCode,@Param("relevantName")String relevantName);


	/**
	 * @Description: 获取最新的10条抓拍记录信息
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年7月29日 下午5:21:19
	 * @Return: CarSnapshotResponse 返回最新的10条抓拍记录
	 */
	public List<CarSnapshotResponse> getTopTen();


}