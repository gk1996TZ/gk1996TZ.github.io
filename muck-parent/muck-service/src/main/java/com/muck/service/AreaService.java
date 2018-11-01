package com.muck.service;

import java.util.List;

import com.muck.domain.Area;

/**
* @Description: 区域Service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 上午11:16:07
*/
public interface AreaService extends BaseService<Area>{

	/**
	* @Description: 查询顶级分类
	* @author: 展昭
	* @date: 2018年4月17日 下午3:49:50
	*/
	public List<Area> queryTopAreas();
	
	/**
	* @Description: 根据父区域的id查询此区域下面所有的子区域
	* @author: 展昭 
	* @date: 2018年4月17日 下午4:42:20
	*/
	public List<Area> querySubAreasByParentId(String parentId,Integer areaType);
	/**
	 * @Description: 根据父级区域id查询所有子级区域的id
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月27日 下午2:21:02
	 * @param: parentId 传入父级区域的id
	 * @return: String 返回所有子级区域id拼接而成的字符串
	 */
	public String querySubAreaIdsByParentId(String parentId);
	/**
	 * @Description: 根据父级区域编号查询所有子级区域的
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月16日 下午4:23:10
	 * @param: parentCode
	 * @return: String
	 */
	public String querySubAreaCodesByParentCode(String parentCode);
	/**
	* @Description: 通过递归查询所有的区域(查询出来的是一颗树状结构的数)
	* @author: 展昭
	* @date: 2018年4月17日 下午5:08:36
	 */
	public List<Area> queryAreasByDeep(Integer areaType);
	
	/**
	* @Description: 根据区域id查询此区域下面的子区域的个数
	* @param:区域id
	* @author: 展昭
	* @date: 2018年4月19日 上午10:27:12
	*/
	public Long querySubAreaCount(String areaId);
	/**
	 * @Description:  根据区域编号查询区域信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月19日  下午8:24:51
	 * @param: areaCode 区域编号
	 * @return:Area 返回区域信息
	 */
	public Area  queryByAreaCode(String areaCode);

	public List<Area> queryAllAreas();

}
