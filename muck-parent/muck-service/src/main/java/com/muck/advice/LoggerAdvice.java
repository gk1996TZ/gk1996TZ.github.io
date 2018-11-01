package com.muck.advice;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.muck.annotation.Logger;
import com.muck.domain.Log;
import com.muck.domain.Manager;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.service.LogService;
import com.muck.utils.ArrayUtils;
import com.muck.utils.DateUtils;

/**
* @Description: 日志切面
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月28日 下午3:41:03
 */
public class LoggerAdvice {
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private HttpSession session;
	
	/**
	* @Description: 保存记录日志
	* @author: 展昭
	* @date: 2018年4月28日 下午3:41:51
	*/
	public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {
		
		// 首先获取要捕获的方法签名
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取方法
		Method targetMethod = methodSignature.getMethod();
		
		// 判断此方法是否存在Logger注解
		Logger logger = targetMethod.getAnnotation(Logger.class);
		if(logger == null){
			// 为空表示不存在则直接放行
			return pjp.proceed();
		}else{
			// 如果不为空则表示存在,存在则意味着要进行日志处理
			
			// 第一步、创建日志
			Log log = new Log();
			// 设置操作时间
			log.setOperatorTime(new Date());
			// 设置操作模块
			log.setOperatorModel(logger.operatorModel());
			try {
				
				// 2、设置操作人的信息,从session中获取操作人信息
				if(session != null){
					Manager manager = (Manager)session.getAttribute("manager");
					if(manager != null){
						String phone = manager.getManagerPhone();
						log.setOperatorId(IdTypeHandler.decode(manager.getId()));
						log.setOperatorName(manager.getManagerName());
						log.setOperatorPhone(phone);
						log.setOperatorAccount(phone == null ? "" : phone);
					}
				}
				// 3、操作类型
				String type = pjp.getSignature().getName();
				log.setOperatorType(type + "("+logger.operatorDesc()+")");
				
				// 4、操作的参数
				Object[] params = pjp.getArgs();
				if(params!=null && params.length > 0){
					for(int i = 0 ; i < params.length ; i++){
						Object param = params[i];
						if(param instanceof HttpServletRequest || param instanceof HttpServletResponse){
							params[i] = null;
						}
					}
					log.setOperatorParams(ArrayUtils.array2str(params));
				}
				
				// 5、设置描述说明
				log.setMemo(setMemo(log));
				
				// 6、调用目标方法
				Object ret = pjp.proceed();
				
				// 7、设置操作反馈结果
				log.setOperatorResult("success");
				
				// 8、设置操作结果信息
				if(ret != null){
					log.setOperatorResultMsg(ret.toString());
				}
				return ret;
			}catch (Throwable e) {
				// 如果有异常,则设置异常信息,这种异常是我们系统的异常
				log.setOperatorResult("failure");  // 设置操作反馈结果
				if(e instanceof BizException){
					BizException ex = (BizException)e;
					log.setOperatorResultMsg(ex.getStatusCode().getMessage());
				}else{
					log.setOperatorResultMsg(e.getMessage()); // 设置操作结果信息
				}
				throw e;
			}finally{
				// 不管成功与否,都要保存日志
				logService.save(log);
			}
		}
	}
	
	// 设置备注信息
	private String setMemo(Log log){
		// 账号
		String account = log.getOperatorAccount();
		// 格式化日期
		String date = DateUtils.formatDate(log.getOperatorTime(), null);
		// 操作
		String type = log.getOperatorType();
		log.setCreatedTime(log.getOperatorTime());
		log.setUpdatedTime(log.getOperatorTime());
		return account + " 账号在 " + date + " 操作了 " + log.getOperatorModel() + " 模块中的 " + type + " 操作";
	}
	
}
