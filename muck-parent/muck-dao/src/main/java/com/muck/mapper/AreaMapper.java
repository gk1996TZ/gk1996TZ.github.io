package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Area;

/**
* @Description: 区域Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月11日 下午2:14:21
*/
@Repository
public interface AreaMapper extends BaseMapper<Area>{

	/**
	 * 	查询顶级区域(一级区域)
	*/
	public List<Area> selectTopAreas();
	
	/**
	*  根据父区域id查询此区域下面所有的子区域 
	*/
	public List<Area> selectSubAreasByParentId(@Param("parentId")String parentId,
			@Param("areaType")Integer areaType);
	
	/**
	 * @Description: 查询父级id下所有子级的id 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月27日 下午2:15:47
	 * @param: parentId 传入一个父级id
	 * @return: String 返回所有子级id拼接而成的字符串
	 */
	public String selectSubAreaIdsByParentId(@Param("parentId")String parentId);
	/**
	 * @Description: 查询父级区域下所有的子级区域编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月16日 下午7:20:05
	 * @param: parentCode 传入父级的区域编号
	 * @return: String 返回所有子级的区域编号
	 */
	public String querySubAreaCodesByParentCode(@Param("parentCode")String parentCode);
	/**
	 * 	根据区域id查询此区域下面的子区域的个数
	*/
	public Long selectSubAreaCount(@Param("areaId")String areaId);
	
	/**
	* @Description: 批量区域
	* @author: 展昭
	* @date: 2018年5月10日 下午7:07:33
	 */
	public void insertBatch(List<Area> areas);
	/**
	 * @Description:  根据区域编号查询区域信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月19日  下午8:24:51
	 * @param: areaCode 区域编号
	 * @return:Area 返回区域信息
	 */
	public Area  queryByAreaCode(String areaCode);

	public List<Area> selectAllAreas();

	/**
	 * 	维护区域树结构
	*/
	public void setAreaRelation();
}
