package com.muck.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Area;
import com.muck.domain.Disposal;
/**
 * @Description:处置场管理mapper
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月4日 下午7:09:00
 */
@Repository
public interface DisposalMapper extends BaseMapper<Disposal>{

	/**
	 * @Description: 获取所有的处置场id
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午11:06:29
	 * @return: String 返回所有的处置场id
	 */
	public List<String> queryDisposalIdsAll();
	
	/**
	 * @Description: 获取处置场树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午5:49:51
	 * @return:List<Area> 返回处置场树
	 */
	public List<Area> queryDisposalTree();

	/**
	* @Description: 批量添加处置场
	*/
	public void insertBatch(@Param("disposals")List<Disposal> disposals);

	/**
	 * 	将处置场中的区域维护(主要是维护区域的id)
	*/
	public void setDisposalAndAreaRelation();

	/**
	 * 	根据通道号查询处置场详情
	**/
	public Disposal selectDisposalInfoByChannelCode(@Param("channelCode")String channelCode);
	
	/**
	 * 批量更新
	 * */
	public void updateBatch(@Param("registerCode")String registerCode,@Param("channelCodes")String channelCodes);
}
