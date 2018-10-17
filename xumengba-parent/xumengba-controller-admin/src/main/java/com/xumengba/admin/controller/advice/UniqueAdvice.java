package com.xumengba.admin.controller.advice;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.xumengba.annotation.Unique;
import com.xumengba.exception.UniqueException;
import com.xumengba.request.BaseForm;
import com.xumengba.response.StatusCode;
import com.xumengba.service.UniqueService;
import com.xumengba.utils.ArrayUtils;

/**
* @Description: 校验唯一性通知
* 					这个通知是一个前置通知
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月3日 下午4:10:33
 */
public class UniqueAdvice {
	
	@Autowired
	private UniqueService uniqueService;
	
	
	/**
	* @Description: 字段唯一性校验
	* @author: 展昭
	* @date: 2018年5月3日 下午4:15:09
	*/
	public void validateUnique(JoinPoint joinpoint) throws Throwable {
		
		// 获取目标方法要执行的参数
		Object[] args = joinpoint.getArgs();
		
		if(args != null && args.length > 0){
			// 表示目标方法存在参数
			for(Object arg : args){
				if(arg == null || !(arg instanceof BaseForm)){
					continue;
				}
				Unique unique = arg.getClass().getAnnotation(Unique.class);
				if(unique != null){
					// 不为空表示此实体上有此注解,则意味着需要校验唯一性认证
					
					// 获取需要校验的字段
					String[] fields = unique.uniqueFields();
					
					// 获取表名
					String tableName = unique.tableName();
					
					if(ArrayUtils.isNotEmpty(fields) && StringUtils.isNotBlank(tableName)){
						
						Method method = arg.getClass().getMethod("validateUnique", new Class[]{});
						
						Object ret = method.invoke(arg, new Object[]{});
						
						if(ret != null && ret instanceof Object[]){
							// 去查询
							Object[] values = (Object[])ret;
							StatusCode result = uniqueService.validateUnique(fields,values,tableName);
							if(result != null){
								throw new UniqueException(result);
							}
						}
					}
				}
			}
		}
	}
}
