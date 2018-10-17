package com.xumengba.mapper.provider;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;

import com.xumengba.annotation.Table;
import com.xumengba.handler.IdTypeHandler;

public class InsertMapperProvider {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 添加对象的通用sql
	 * 
	 * @param obj
	 *            传入一个需要被添加的对象
	 * @return 返回sql语句
	 */
	public String insert(final Object obj) {
		// 得到类对象
		final Class<Object> stuCla = (Class<Object>) obj.getClass();
		// 得到属性集合
		Field [] fields = stuCla.getDeclaredFields();
		/*// 获取父类属性
		Class<Object> stuSuper = stuCla.getSuperclass();
		Field [] fSupers = null;
		if(stuSuper != null){
			fSupers = stuSuper.getDeclaredFields();
		}else {
			fSupers = new Field[0];
		}
		// 将父类属性和子类属性放到一个数组中
		fields = Arrays.copyOf(fields, fields.length+fSupers.length);//数组扩容
		System.arraycopy(fSupers, 0, fields, fields.length, fSupers.length);*/
		final Field[] fs = fields;
		return new SQL() {
			{
				//获取表名
				Table table = stuCla.getAnnotation(Table.class);
				if(table == null){
					throw new RuntimeException("实体类"+stuCla.getSimpleName()+"上缺少table注解");
				}
				INSERT_INTO(table.name());
				try {
					//存放字段名
					List<String> params = new ArrayList<String>();
					//存放value值
					List<String> values = new ArrayList<String>();
					// 遍历属性
					for (Field f : fs) {
						// 设置属性是可以访问的(私有的也可以)
						f.setAccessible(true);
						// 得到此属性的值
						Object val = f.get(obj);
						// 如果属性值不为空执行的操作
						if (val != null && !"".equals(val.toString()) && !"serialVersionUID".equals(f.getName())) {
							// 将字段名和该字段的值放到数组中
							com.xumengba.annotation.Field fieldName = f.getAnnotation(com.xumengba.annotation.Field.class);
							if(fieldName == null){
								throw new RuntimeException("添加"+stuCla.getSimpleName()+"时");
							}
							params.add(fieldName.name());
							String type = f.getGenericType().toString();
							// 如果添加的时间类型，则转化一下时间格式
							if ("date".equalsIgnoreCase(type.substring(type.lastIndexOf(".") + 1, type.length()))) {
								Date date = (Date) val;
								values.add("'" + sdf.format(date) + "'");
							} else if("boolean".equalsIgnoreCase(type.substring(type.lastIndexOf(".") + 1, type.length()))){
								if((boolean)val){
									values.add(1+"");
								}else {
									values.add(0+"");
								}
							} else if(f.getName().toLowerCase().contains("id")){
				        		values.add("'" + IdTypeHandler.decode(val.toString()) + "'");
				        	}else {
								try {
									values.add("'" + val.toString() + "'");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					//拼接字段名sql
					INTO_COLUMNS(params.toArray(new String[] {}));
					//拼接value值sql
					INTO_VALUES(values.toArray(new String[] {}));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}.toString();
	}

	/**
	 * 执行添加一个列表数据的操作
	 * 
	 * @param objects
	 *            传入一个数据列表
	 * @return 返回sql语句
	 */
	public String insertAll(List<Object> objects) {
		if (objects != null && objects.size() > 0) {
			// 得到列表中第一个对象
			final Object obj = objects.get(0);
			// 得到列表中第一个对象的类对象
			final Class<Object> stuCla = (Class<Object>) obj.getClass();
			// 得到属性集合
			Field [] fields = stuCla.getDeclaredFields();
			/*// 获取父类属性
			Class<Object> stuSuper = stuCla.getSuperclass();
			Field [] fSupers = null;
			if(stuSuper != null){
				fSupers = stuSuper.getDeclaredFields();
			}else {
				fSupers = new Field[0];
			}
			fields = Arrays.copyOf(fields, fields.length+fSupers.length);//数组扩容
			System.arraycopy(fSupers, 0, fields, fields.length, fSupers.length);*/
			final Field[] fs = fields;
			StringBuilder sb = new StringBuilder("");
			sb.append(new SQL() {
				// 将父类属性和子类属性放到一个数组中
				{
					Table table = stuCla.getAnnotation(Table.class);
					if(table==null){
						throw new RuntimeException("实体类"+stuCla.getSimpleName()+"上缺少table注解");
					}
					INSERT_INTO(table.name());
					try {
						List<String> params = new ArrayList<String>();
						// 遍历属性
						for (Field f : fs) {
							// 设置属性是可以访问的(私有的也可以)
							f.setAccessible(true);
							// 得到此属性的值
							Object val = f.get(obj);
							// 如果属性值不为空执行的操作
							if (val != null && !"".equals(val.toString()) && !"serialVersionUID".equals(f.getName())) {
								// 将字段名和该字段的值放到数组中
								com.xumengba.annotation.Field fieldName = f.getAnnotation(com.xumengba.annotation.Field.class);
								if(fieldName == null){
									throw new RuntimeException("添加"+stuCla.getSimpleName()+"时");
								}
								params.add(fieldName.name());
							}
						}
						INTO_COLUMNS(params.toArray(new String[] {}));
					}catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}.toString());
			sb.append("values");
			// 组装Values
			for (Object o : objects) {
				// 获取当前对象的类的对象
				final Class<Object> stuClaO = (Class<Object>) o.getClass();
				// 得到属性集合
				Field [] fieldsO = stuClaO.getDeclaredFields();
				/*// 获取父类属性
				Class<Object> stuSuperO = stuClaO.getSuperclass();
				Field [] fSupersO = null;
				if(stuSuperO != null){
					fSupersO = stuSuperO.getDeclaredFields();
				}else {
					fSupersO = new Field[0];
				}
				fieldsO = Arrays.copyOf(fieldsO, fieldsO.length+fSupersO.length);//数组扩容
				System.arraycopy(fSupersO, 0, fieldsO, fieldsO.length, fSupersO.length);*/
				final Field[] fsO = fieldsO;
				List<String> values = new ArrayList<String>();
				try {
					// 遍历属性
					for (Field fO : fsO) {
						// 设置属性是可以访问的(私有的也可以)
						fO.setAccessible(true);
						// 得到此属性的值
						Object valO = fO.get(o);
						// 如果属性值不为空执行的操作
						if (valO != null && !"serialVersionUID".equals(fO.getName())) {
							String typeO = fO.getGenericType().toString();
							// 如果添加的时间类型，则转化一下时间格式
							if ("date".equalsIgnoreCase(
									typeO.substring(typeO.lastIndexOf(".") + 1, typeO.length()))) {
								Date date = (Date) valO;
								values.add("'" + sdf.format(date) + "'");
							} else if ("boolean".equalsIgnoreCase(typeO.substring(typeO.lastIndexOf(".") + 1, typeO.length()))){
								if((boolean)valO){
									values.add(1+"");
								}else {
									values.add(0+"");
								}
							} else if(fO.getName().toLowerCase().contains("id")){
				        		values.add("'" + IdTypeHandler.decode(valO.toString()) + "'");
				        	}else {
								values.add("'" + valO.toString() + "'");
							}
						}
					}
					if(values.size()>0){
						sb.append("(");
						sb.append(values.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
						sb.append(")");
						sb.append(",");
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			return sb.length()>0?sb.deleteCharAt(sb.length()-1).append(";").toString():"select -1";
		}
		return "select 0";
	}
}
