package com.muck.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.muck.domain.BaseEntity;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.page.PageView;
/**
 * @Description: 基类Service
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月12日 下午5:05:27
*/
public interface BaseService<T extends BaseEntity> {

	/**
	 * 	保存实体
	 * 	@param entity 实体对象
	*/
	public void save(T entity);

	/**
	 * 	根据id删除(逻辑删除)
	 * 	@param id 实体对象主键id
	*/
	public void deleteById(String id);
	
	/**
	 * @Description: 根据id删除(真实删除)
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月24日  下午5:25:57
	 * @param:@param id 传入一个id
	 */
	public void deleteByIdReal(String id);

	/**
	 * 	根据id编辑一个实体对象
	 * 	@param entity 实体对象
	*/
	public void editById(T entity);

	/**
	 * 	根据id查询实体对象
	 * 	@param id 实体对象主键id
	*/
	public T queryById(String id);
	
	/**
	 * 	根据id查询实体对象
	 * 		只是查询对象的基本信息,不包括对象之间的关联信息
	*/
	public T queryByIDSimple(String id);
	
	/**
	 * 	分页查询数据(带条件,带排序)
	 * 	@param currentPageNum 当前页
	 * 	@param pageSize 每页显示的记录数
	 * 	@param where where条件
	 * 	@param orderBy 排序条件
	*/
	//public PageView<T> queryPageData(Long currentPageNum,Long pageSize,
	//		Map<String,Object> where,String orderBy);
	
	/**
	 * 	查询分页数据,只有条件,没有排序
	 * 	@param currentPageNum 当前页
	 * 	@param pageSize 每页显示的记录数
	 * 	@param where where条件
	*/
	//public PageView<T> queryPageData(Long currentPageNum,Long pageSize,Map<String,Object> where);
	
	/**
	 * 	查询分页数据,没有条件,只有排序
	 * 	@param currentPageNum 当前页
	 * 	@param pageSize 每页显示的记录数
	 *  @param orderBy 排序条件
	*/
	//public PageView<T> queryPageData(Long currentPageNum,Long pageSize,String orderBy);
	
	/**
	 * 查询分页数据,没有条件,没有排序
	 * 	@param currentPageNum 当前页
	 * 	@param pageSize 每页显示的记录数
	*/
	//public PageView<T> queryPageData(Long currentPageNum,Long pageSize);
	
	
	// ---------------------------
	
	/**
	 * 查询全部数据
	*/
	//public PageView<T> queryPageData();
	
	/**
	 * 	查询全部数据,只带排序
	 * 	@param orderBy 排序条件
	*/
	//public PageView<T> queryPageData(String orderBy);
	
	/**
	 * 	查询全部数据,只带条件
	 * 	@param where 条件
	*/
	//public PageView<T> queryPageData(Map<String,Object> where);
	
	/**
	 * 	查询全部数据,带条件,带排序
	 * 	@param where 条件
	 * 	@param orderby 排序
	*/
	//public PageView<T> queryPageData(Map<String,Object> where,String orderBy);
	
	public PageView<T> queryPageData(Long currentPageNum, Long pageSize, 
			 String whereSQL,List<Object> whereParams,
			 LinkedHashMap<String, String> orderBy);
	
	public PageView<T> queryPageData();
	
	
	/**
	 * @Description: 根据条件查询数据 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月27日 下午4:50:14
	 * @param: wherSQL 传入查询的sql语句
	 * @param: whereParams 传入查询的条件
	 * @return: List<T> 返回数据列表
	 */
	public List<T> queryData(String wherSQL,List<Object> whereParams);
	
	/**
	 * @Description: 查询所有的数据
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月27日 下午5:04:02
	 * @return: List<T> 返回所有的数据列表
	 */
	public List<T> queryData();
	/**
	 * @Description: 生成表格数据
	 * @version: v1.0.0
	 * @author: 朱俊亮
	 * @date: 2018年5月26日  上午11:44:49
	 * @param: excelTemplate Excel模版
	 * @param: list 传入向表格中添加的数据的列表
	 * @return: List<Map<String,String>> 返回excel表格数据
	 */
	public List<Map<String,String>> createExcelData(BaseExcelQueryTemplate excelTemplate,List<T> list);

	/**
	 * @Description: 生成单个实体对象的表格数据
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月28日  下午12:00:57
	 * @Param: excelTemplate 传入一个模版
	 * @Param: baseEntity 传入一个实体对象
	 * @Return:Map<String,Object> 返回表格数据
	 */
	public Map<String,Object> createExcelData(BaseExcelQueryTemplate excelTemplate,BaseEntity baseEntity);
	/**
	 * @Description:将excel表格数据填充到excel表格文件的工作薄中
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月28日  下午4:29:45
	 * @Param: fileName 传入本地存放该excel模版的文件名
	 * @Param: excelData 传入一个excel表格数据
	 * @Param: water 传入Excel水印数据
	 * @Return:String 返回文件在本地的目录
	 */
	public String createExcelPOI(String fileName,Map<String,Object> excelData,String [] water);
	
	
	/**
	 * @Description: 将excel表格数据填充到excel表格文件的相应工作簿中 
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月14日 下午2:09:36
	 * @Param: filePath 传入一个excel文件路径
	 * @Param: excelData 传入excel表格数据
	 * @Param: water 传入一个水印
	 * @Return: String 返回下载路径或存放数据的excel文件在本地的路径
	 */
	public String createExcelPOI(String filePath,List<Map<String,String>> excelData,String [] water);
	
	/**
	 * @Description: 将多个相同类型的实体对象导入到同一张excel表格中的方法，单个实体占用一个工作簿
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月14日 下午2:52:40
	 * @Param: excelTemplate 传入一个模板实体类
	 * @Param: fileName 传入一个excel文件名
	 * @Param: data 传入一个实体列表
	 * @Param: water 传入一个水印
	 * @Return: String 返回生成的存放数据的excel在本地的目录
	 */
	public String createExcelPOI(BaseExcelQueryTemplate excelTemplate,String fileName,List<T> data,String [] water);
	/**
	 * @Description: 从Map键值对列表中获取实体类数据对象列表
	 * @version: v1.0.0
	 * @author: 朱俊亮
	 * @date: 2018年5月27日  下午2:46:30
	 * @param: excelTemplate 传入本地的Excel模版参数
	 * @param: list 传入一个存放Map键值对的List集合列表
	 * @return: List<T> 返回含有实体类对象数据的列表
	 */
	public List<T> selectDataFromExcelMapData(BaseExcelQueryTemplate excelTemplate,List<Map<String,String>> excelData);
	
	/**
	 * @Description: 读取本地Excel表格内容的操作
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月21日  上午10:28:00
	 * @Param:path 传入一个Excel表格的本地目录
	 * @Return:List<Map<String,String>> 返回表格数据
	 */
	public List<Map<String,String>> readExcelData(String path);
	
	/**
	 * @Description: 读取表格中的数据详情，并封装称一个实体类对象
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月21日  下午5:43:00
	 * @Param: baseExcelQueryTemplate 传入一个Excel模版参数
	 * @Param: path 传入Excel在本地的目录
	 * @Return:T 返回当前的实体类
	 */
	public T readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate,String  path);
	
	/**
	 * @Description: 读取表格文件数据，（这个是用作多个工作簿的导入，每个工作簿的内容是一样的）
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月10日 下午7:07:44
	 * @Param: baseExcelQueryTemplate 传入一个Excel的模板
	 * @Param: path 传入一个文件路径
	 * @Return: List<T> 返回实体列表
	 */
	public List<T> readExcelListData(BaseExcelQueryTemplate baseExcelQueryTemplate,String path);
	
	public T readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate,String  path,Integer type);
	/**
	 * @Description: 向数据库中添加一个列表数据
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月27日  下午5:02:14
	 * @param:@param list 传入一个数据列表
 */
	public void saveAll(List<T> list);
	
	//根据条件删除
	public void deleteData(String wherSQL,List<Object> whereParams);
	
}
