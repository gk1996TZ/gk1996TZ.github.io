package com.muck.admin.controller.advice;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.muck.domain.Log;
import com.muck.domain.Manager;
import com.muck.service.LogService;
import com.muck.utils.ArrayUtils;

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
		
		// 第一步、创建日志
		Log log = new Log();
		// 设置操作时间
		log.setOperatorTime(new Date());
		try {
			
			// 2、设置操作人的信息,从session中获取操作人信息
			if(session != null){
				Manager manager = (Manager)session.getAttribute("manager");
				if(manager != null){
					log.setOperatorAccount(manager.getManagerPhone());
				}
			}
			// 3、操作类型
			String type = pjp.getSignature().getName();
			log.setOperatorType(type);
			
			// 4、操作的参数
			Object[] params = pjp.getArgs();
			log.setOperatorParams(ArrayUtils.array2str(params));
			
			// 调用目标方法
			Object ret = pjp.proceed();
			
			// 设置操作反馈结果
			log.setOperatorResult("success");
			// 设置操作结果信息
			if(ret != null){
				log.setOperatorResultMsg(ret.toString());
			}
			return ret;
		} catch (Throwable e) {
			// 如果有异常,则设置异常信息,这种异常是我们系统的异常
			log.setOperatorResult("failure");  // 设置操作反馈结果
			log.setOperatorResultMsg(e.getMessage()); // 设置操作结果信息
		}finally{
			// 不管成功与否,都要保存日志
			logService.save(log);
		}
		return null;
	}
}
