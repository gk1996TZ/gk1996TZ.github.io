package com.muck.service.impl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muck.annotation.ExcelTemplate;
import com.muck.annotation.Table;
import com.muck.domain.BaseEntity;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.page.PageView;
import com.muck.response.StatusCode;
import com.muck.service.BaseService;
import com.muck.utils.GenericsUtils;

/**
 * @Description: 基类的抽象实现
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月12日 下午5:06:44
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

	// 此方法之所以定义为抽象的,是必须要交给子类去重写
	protected abstract BaseMapper<T> getMapper();

	// 定义为抽象的,则必须要子类重写,此方法的功能是定义要查询的字段
	protected abstract String getFields();

	// 获取实体
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 保存实体
	 */
	public void save(T entity) {

		if (entity == null) {
			throw new RuntimeException("要保存的" + getEntityName() + "不能为空");
		}
		// 设置创建时间
		entity.setCreatedTime(new Date());
		// 设置修改时间
		entity.setUpdatedTime(entity.getCreatedTime());
		// 设置默认不删除
		entity.setDeleted(true);

		// 保存
		getMapper().insert(entity);
	}
	@Override
	public void saveAll(List<T> list) {
		if(list == null || list.isEmpty()){
			throw new RuntimeException("要保存的" + getEntityName() + "不能为空");
		}
		//保存
		getMapper().insertAll(list);
	}

	// 根据id删除(逻辑删除)
	public void deleteById(String id) {

		if (StringUtils.isBlank(id)) {
			throw new BizException(StatusCode.ID_BLANK);
		}
		// 删除
		getMapper().deleteById(id);
	}
	
	@Override
	public void deleteByIdReal(String id) {
		if (StringUtils.isBlank(id)) {
			throw new BizException(StatusCode.ID_BLANK);
		}
		// 删除
		getMapper().deleteByIdReal(id);
	}

	// 根据id编辑一个实体对象
	public void editById(T entity) {

		if (entity == null) {
			throw new BizException(StatusCode.ID_BLANK);
		}

		String id = entity.getId();
		if (StringUtils.isBlank(id)) {
			throw new BizException(StatusCode.ID_BLANK);
		}

		// 设置修改时间为当前时间
		entity.setUpdatedTime(new Date());

		// 修改
		getMapper().updateById(entity);
	}

	// 根据id查询实体对象
	public T queryById(String id) {
		if (StringUtils.isBlank(id)) {
			throw new BizException(StatusCode.ID_BLANK);
		}
		T entity = getMapper().selectById(id);

		return entity;
	}
	
	/**
	 * 	根据id查询实体对象
	 * 		只是查询对象的基本信息,不包括对象之间的关联信息
	*/
	public T queryByIDSimple(String id){
		if (StringUtils.isBlank(id)) {
			throw new BizException(StatusCode.ID_BLANK);
		}
		T entity = getMapper().selectByIdSimple(id);

		return entity;
	}

	@Override
	public PageView<T> queryPageData(Long currentPageNum, Long pageSize, String wherSQL, List<Object> whereParams,
			LinkedHashMap<String, String> orderBy){
		// 第一步、获取要查询的字段
		String queryFields = getFields();
		if (StringUtils.isBlank(queryFields)) {
			throw new BizException(StatusCode.QUERY_FIELDS_NOT_FOUND);
		}
	
		// 第二步、获取表名
		String tableName = getTableNameByEntity(this.entityClass);
	
		// 第三步、获取where条件
		String where = StringUtils.isBlank(wherSQL) ? "" : " where " + wherSQL;
	
		// 第四步、获取orderby条件
		String orderby = buildOrderby(orderBy);
	
		// 第五步、生成查询总条数sql语句
		String countSQL = " SELECT COUNT(id) from " + tableName + where;
	
		// 第六步、设置countSQL并返回sql语句
		countSQL = setQueryParams(countSQL , whereParams,null); // select id from t_car where id = 1
		
		// 第七步、获取总页数
		Long totalRecord = getMapper().selectTotalRecoreds(countSQL);
	
		// 第八步、生成limit语句
		String limit = buildLimit(currentPageNum,pageSize);
	
		// 第九步、生成查询记录数sql语句
		String selectSQL = " SELECT " + queryFields + " from " + tableName + where + orderby + limit;
	
		// 第十步、设置pageView,主要是用来计算limit的数据
		PageView<T> pageView = new PageView<>(totalRecord, currentPageNum, pageSize);
		
		// 第十一步、设置参数信息
		selectSQL = setQueryParams(selectSQL , whereParams,pageView);
	
		// 第十一步、查询数据
		List<T> datas = getMapper().selectPageData(selectSQL);
	
		pageView.setDatas(datas);
	
		return pageView;
	}

	 // 查询全部数据
	 public PageView<T> queryPageData() {
		 return this.queryPageData(-1L, -1L, null, null, null);
	 }
	 
	 @Override
	public void deleteData(String wherSQL, List<Object> whereParams) {
			// 第二步、获取表名
			String tableName = getTableNameByEntity(this.entityClass);

			// 第三步、获取where条件
			String where = StringUtils.isBlank(wherSQL) ? "" : " where " + wherSQL;
			// 第四步、拼接sql语句
			String deleteSQL = " delete from " + tableName + where ;
			// 第五步、设置参数
			deleteSQL = setQueryParams(deleteSQL , whereParams,null);
			//执行删除
			getMapper().deleteData(deleteSQL);
	}
	 
	 @Override
	 public List<T> queryData(String wherSQL,List<Object> whereParams) {
		// 第一步、获取要查询的字段
		String queryFields = getFields();
		if (StringUtils.isBlank(queryFields)) {
			throw new BizException(StatusCode.QUERY_FIELDS_NOT_FOUND);
		}

		// 第二步、获取表名
		String tableName = getTableNameByEntity(this.entityClass);

		// 第三步、获取where条件
		String where = StringUtils.isBlank(wherSQL) ? "" : " where " + wherSQL;
		// 第四步、拼接sql语句
		String selectSQL = " SELECT " + queryFields + " from " + tableName + where ;
		// 第五步、设置参数
		selectSQL = setQueryParams(selectSQL , whereParams,null);
		return getMapper().queryData(selectSQL);
	 }
	 @Override
	 public List<T> queryData() {
		 return this.queryData(null,null);
	 }
	 @Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate, List<T> list) {
		//声明一个存放表格数据的集合
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		//定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//存放到表格中的数据列表，包括表头和表身数据
		//声明一个表头键值对容器，这里存放所有的表头信息
		Map<String,String> tableHead = new HashMap<String,String>();
		/*
		  *  声明一个表身键值对容器，
		  *  这里需要注意的是，表头Map中的key和表身的每个Map的key需要保持一致，
		  *  以此来进行向Excel表格中添加数据避免数据顺序的紊乱
		  *  */
		Map<String,String> tableBody = null;
		
		// 获取传入的表格模版的类的对象
		Class<?> cls = excelTemplate.getClass();
		//得到属性集合
		Field [] fs = cls.getDeclaredFields();
		//遍历模版对象属性列表生成表头数据
		try {
			for (Field f : fs) {
				// 设置属性是可以访问的(私有的也可以)
				f.setAccessible(true);
				// 得到该对象中此属性的值
				Object val = f.get(excelTemplate);
				//如果属性值不为空执行的操作
				if(val!=null) {
					if("true".equals(val.toString())){
						//获取属性名
						String fName = f.getName();
						//获取属性的注解
						ExcelTemplate template = f.getAnnotation(ExcelTemplate.class);
						//向表头键值对容器中添加表头数据,key值为属性名，value值为该属性上的注解的name属性值
						tableHead.put(fName,template.name());
					}
				}
			}
			//将表头添加到数据列表中
			data.add(tableHead);
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		//生成表身数据
		//遍历传入的数据列表
		if(list != null){
			try {
				for(T t : list){
					//获取对象的类的对象
					Class<?> tcls = t.getClass();
					//遍历表头map的所有的key
					Set<String> keySet = tableHead.keySet();
					tableBody = new HashMap<String,String>();
					//遍历表头map所有的key
					for(String key: new ArrayList<String>(keySet)){
						//通过key获取列表数据对象中的对应的属性
						//将key中的has_替换成"",保持和数据实体类中的属性名一致
						Field filed = tcls.getDeclaredField(key.replace("has_",""));
						filed.setAccessible(true);
						String type = filed.getGenericType().toString();
						//获取该对象的此属性的值
						Object obj = filed.get(t);
						if(obj != null){
							//如果获取到的是时间类型，则转化一下时间格式
							if("date".equalsIgnoreCase(type.substring(type.lastIndexOf(".")+1, type.length()))){
								Date date = (Date) obj;
								//将该key与属性值添加到当前条数据的Excel表单元格中
								tableBody.put(key,sdf.format(date));
							}else if("boolean".equalsIgnoreCase(type.substring(type.lastIndexOf(".")+1, type.length()))){
								if((boolean)obj){
									tableBody.put(key,"是");
								}else {
									tableBody.put(key,"否");
								}
							}else {
								//将该key与属性值添加到当前条数据的Excel表单元格中
								tableBody.put(key,obj.toString());
							}
						}else {
							tableBody.put(key,"");
						}
					}
					//将该条数据添加到表身中
					data.add(tableBody);
				}
			} catch (NoSuchFieldException e) {
				System.out.println("未识别的属性");
			} catch (SecurityException e) {
				System.out.println("a");
			} catch (IllegalArgumentException e) {
				System.out.println("b");
			} catch (IllegalAccessException e) {
				System.out.println("c");
			}
		}
		return data;
	}
	
	@Override
	public List<T> selectDataFromExcelMapData(BaseExcelQueryTemplate excelTemplate,List<Map<String, String>> excelData) {
		//声明一个存放生成好的实体类对象数据的列表
		List<T> list = new ArrayList<T>();
		//获取本地模版类的对象
		Class<?> cls = excelTemplate.getClass();
		//获取类的对象的属性列表
		Field [] fs = cls.getDeclaredFields();
		//遍历获取到的Excel表格数据
		try {
			for(Map<String,String> map : excelData){
				//获取当前的实体
				//获取当前的实体
				Class<?> cs = Class.forName(this.entityClass.getName());
				//通过字节码获取实例
				Object obj = cs.newInstance();
				//遍历类的对象的属性列表
				for(Field f : fs){
					//获取ExcelTemplate注解
					ExcelTemplate template = f.getAnnotation(ExcelTemplate.class);
					//获取注解的name属性值
					String name = template.name();
					//通过name属性值获取map中的对应的值
					String value = map.get(name);
					if(StringUtils.isNotBlank(value)){
						//获取指定的属性
						Field field = cs.getDeclaredField(f.getName().replace("has_",""));
						//设置属性是可以访问的
						field.setAccessible(true);
						String type = field.getGenericType().toString();
						String typeReal = "";
						if(type.contains("<") && type.contains(">")){
							typeReal = type.substring(type.lastIndexOf(".",type.indexOf("<")) + 1, type.indexOf("<"));
						}else {
							typeReal = type.substring(type.lastIndexOf(".")+1,type.length());
						}
						if("date".equalsIgnoreCase(typeReal)){
							if(!StringUtils.isBlank(value)){
								field.set(obj, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
							}
						}else if("integer".equalsIgnoreCase(typeReal)){
							field.set(obj, Integer.parseInt(value));
						}else {
							field.set(obj, value);
						}
					}
				}
				//将通过反射获取到的数据封装到当前的类型中并添加到指定的列表中
				list.add((T)cs.cast(obj));
			}
		} catch (NoSuchFieldException e) {
			logger.debug("找不到相应的属性",e);
		} catch (SecurityException e) {
			logger.debug("",e);
		} catch (IllegalArgumentException e) {
			logger.debug("",e);
		} catch (IllegalAccessException e) {
			logger.debug("",e);
		} catch (ClassNotFoundException e) {
			logger.debug("未识别到的类型",e);
		} catch (InstantiationException e) {
			logger.debug("",e);
		} catch (ParseException e) {
			logger.debug("",e);
		}
		return list;
	}
	
	@Override
	public Map<String, Object> createExcelData(BaseExcelQueryTemplate excelTemplate, BaseEntity baseEntity) {
		return null;
	}

	@Override
	public List<T> readExcelListData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path) {
		return null;
	}

	@Override
	public List<Map<String, String>> readExcelData(String path) {
		return null;
	}
	
	@Override
	public T readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path) {
		return null;
	}
	
	@Override
	public T readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path,Integer type) {
		return null;
	}
	
	@Override
	public String createExcelPOI(String fileName, Map<String, Object> excelData,String [] water) {
		return null;
	}
	
	@Override
	public String createExcelPOI(BaseExcelQueryTemplate excelTemplate, String fileName, List<T> data, String[] water) {
		return null;
	}

	@Override
	public String createExcelPOI(String filePath, List<Map<String, String>> excelData, String[] water) {
		return null;
	}

	// ------------------------------------------
	// 获取实体的名称
	private String getEntityName() {
		return this.entityClass.getSimpleName();
	}

	// 获取实体所对应的表名
	protected String getTableNameByEntity(Class<T> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if (table == null || "".equals(table.name().trim())) {
			throw new BizException(StatusCode.TABLE_ANNOTATION_NOT_FOUND);
		}
		return table.name();
	}

	// 组装order by语句
	protected String buildOrderby(LinkedHashMap<String, String> orderBy) {
		StringBuffer orderBySql = new StringBuffer("");
		if (orderBy != null && !orderBy.isEmpty()) {
			orderBySql.append(" order by ");
			for (String key : orderBy.keySet()) {
				orderBySql.append(key).append(" ").append(orderBy.get(key)).append(",");
			}
			orderBySql.deleteCharAt(orderBySql.length() - 1);
		}
		return orderBySql.toString();
	}

	// 组装limit语句
	protected String buildLimit(Long currentPageNum, Long pageSize) {
		StringBuilder sb = new StringBuilder("");
		if (currentPageNum != null && pageSize != null && currentPageNum != -1L && pageSize != -1L) {
			sb.append(" ").append("limit %d,%d").append(" ");
		}
		return sb.toString();
	}
	protected boolean isNullRow(org.apache.poi.ss.usermodel.Row row){
		if(row != null){
			Iterator<Cell> cells = row.cellIterator();
			while(cells.hasNext()){
				org.apache.poi.ss.usermodel.Cell cell = cells.next();
				if(cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK){
					return false;
				}
			}
		}
		return true;
	}
	// 设置参数信息并返回此sql
	protected String setQueryParams(String sql,List<Object> queryParams,PageView<T> pageView ){
		
		List<Object> allParams = new ArrayList<Object>();
		if(queryParams != null && queryParams.size() > 0){
			allParams.addAll(queryParams);
		}
		if(sql.contains("limit")){
			if(pageView != null){
				allParams.add(pageView.getStartIndex());
				allParams.add(pageView.getPageSize());
			}
		}
		sql = String.format(sql, allParams.toArray());
		return sql;
	}
}