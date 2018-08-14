package com.wsxd.sync.common.aspect;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * service操作日志
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月19日 上午9:57:07
 */
@Aspect
@Component
public class ServiceLogAspect {

	private static Log logger = LogFactory.getLog(ServiceLogAspect.class);

	/**
	 * 切入点</br>
	 * 第一个*号：表示返回类型，*号表示所有的类型</br>
	 * 包名：表示需要拦截的包名，“.*”表示包下的所有类，而“..*”表示包、子孙包下的所有类</br>
	 * 第二个*号：表示类名，*号表示所有的类</br>
	 * *(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数</br>
	 * 多个execution需要单独扩起来，用||连接</br>
	 * 
	 * @author zhangyi
	 * @time 2018年1月19日 下午2:07:17
	 */
	@Pointcut("(execution(* com.jitai.custom.service.*.login*(..))) "
			+ "|| (execution(* com.jitai.custom.service.*.insert*(..))) "
			+ "|| (execution(* com.jitai.custom.service.*.create*(..))) "
			+ "|| (execution(* com.jitai.custom.service.*.save*(..))) "
			+ "|| (execution(* com.jitai.custom.service.*.add*(..))) "
			+ "|| (execution(* com.jitai.custom.service.*.delete*(..))) "
			+ "|| (execution(* com.jitai.custom.service.*.remove*(..))) "
			+ "|| (execution(* com.jitai.custom.service.*.update*(..))) "
			+ "|| (execution(* com.jitai.custom.service.*.edit*(..)))")
	public void pointcut() {
	}

	@Around(value = "pointcut()")
	public Object showLogAround(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		Signature signature = point.getSignature();
		Object result = null;
		try {
			// 目标方法之前要执行的操作,相当于@before
			logger.debug("[环绕日志 ->" + signature.getName() + " - start]" + signature + "开始了，参数为:" + Arrays.asList(args));
			// 调用目标方法
			result = point.proceed(args);
		} finally {
			// 方法最终结束时执行的操作，相当于@after
			logger.debug("[环绕日志 ->" + signature.getName() + " - end]" + signature + "结束了!");
		}
		// 目标方法正常执行之后的操作，相当于@AfterReturning
		logger.debug("[环绕日志 ->" + signature.getName() + " - return]" + signature + "返回了，返回值为:" + result);

		return result;
	}

}
