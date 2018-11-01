package com.muck.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Channel;
import com.muck.domain.Site;

/**
 * @Description: 工地信息mapper
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月2日 下午5:19:30
 */
@Repository
public interface SiteMapper extends BaseMapper<Site>{

	/**
	 * @Description: 通过负责人id获取工地id列表
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月3日 上午11:27:10
	 * @param: managerIds 传入一系列负责人id
	 * @return: Set<String> 返回工地id列表 
	 */
	public Set<String> getSiteIdsByManagerIds(String managerIds);
	/**
	 * @Description: 获取所有的工地id
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午10:14:29
	 * @return: String 返回工地id
	 */
	public List<String> getSiteIdsAll();
	
	/**
	 * 	根据通道号查询工地
	*/
	public Site selectSiteInfoByChannelCode(@Param("channelCode")String channelCode);


	/**
	 * @Description: 根据设备编号查询工地id 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月18日 下午2:58:58
	 * @param: deviceCode 传入一个设备编号
	 * @return: String 返回该设备所属工地的工地id
	 */
	public String querySiteIdByDeviceCode(String deviceCode);
	/**
	 * @Description: 通过工地id查询该工地下所有的设备的所有的通道 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月18日 下午3:04:05
	 * @param: siteId 传入一个工地id
	 * @return: List<Channel> 返回含有当前工地的所有的设备的所有的通道号
	 */
	public List<Channel> queryChannelBySiteId(String siteId);
	
	/**
	 * 	根据区域id查询区域下面所有的工地
	*/
	public List<Site> selectByAreaId(@Param("areaId")String areaId);
	
	/**
	 * 	设置维护工地和区域的关系
	*/
	public void setSiteAndAreaRelation();
	
	/**
	* @Description: 批量添加工地
	*/
	public void insertBatch(@Param("sites")List<Site> sites);
	
	/**
	 * 根据注册号查询工地id
	 * */
	public Integer getIdByRegisterCode(@Param("registerCode")String registerCode);
	
	/**
	 * 批量更新
	 * */
	public void updateBatch(@Param("registerCode")String registerCode,@Param("channelCodes")String channelCodes);
}
