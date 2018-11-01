package com.muck.service;

import java.util.List;

import com.muck.domain.CarSnapshotInOutImage;
import com.muck.response.CarSnapshotResponse;

/**
 * 	车辆抓拍进出工地处置场service
*/
public interface CarSnapshotInOutImageService extends BaseService<CarSnapshotInOutImage> {

	//根据条件查询并创建对象
	public CarSnapshotResponse saveCarSnapshotOutInfoByCondition(String carCode,String carCardColor,String registerId,String snapshotTime,String picBigPath,String picSmailPath);

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
	public Integer queryCarSnapshotCountOneDay(String day,String carCode,String relevantName);
	/**
	 * @Description: 获取最新的10条抓拍记录信息
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年7月29日 下午5:21:19
	 * @Return: CarSnapshotResponse 返回最新的10条抓拍记录
	 */
	public List<CarSnapshotResponse> getTopTen();
}
