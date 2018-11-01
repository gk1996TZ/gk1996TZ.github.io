package com.muck.service;

import java.util.List;

import com.muck.domain.Area;
import com.muck.domain.SnapshotImage;
import com.muck.response.SnapshotImageRecord;

/**
* @Description: 抓拍图片service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月12日 下午5:01:32
 */
public interface SnapshotImageService extends BaseService<SnapshotImage>{
	
	/**
	 * @Description: 抓拍记录树（
	 * 										   工地区域>工地
	 * 										   处置场区域>处置场
	 * 										   停车场区域>停车场
	 * 										 ）
	 * @version: v1.0.0
	 * @author: 朱俊亮
	 * @date: 2018年6月1日  下午1:16:38
	 * @return: SnapshotImageRecord 返回抓拍记录树
	 */
	public SnapshotImageRecord querySnapshotImageTree();
	
	/**
	 * @Description: 获取工地的抓拍图片树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日  下午1:23:25
	 * @return:List<Area> 返回工地的抓拍图片树
	 */
	public List<Area> querySnapshotImageSiteTree();
	
	/**
	 * @Description: 获取处置场的抓拍图片树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日  下午1:23:33
	 * @return:List<Area> 返回处置场的抓拍图片树
	 */
	public List<Area> querySnapshotImageDisposalTree();
	
	/**
	 * @Description: 获取停车场的抓拍图片树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日  下午1:23:39
	 * @return:List<Area> 返回停车场的抓拍图片树
	 */
	public List<Area> querySnapshotImageCarPark();
}
