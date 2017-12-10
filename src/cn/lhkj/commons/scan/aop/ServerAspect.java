package cn.lhkj.commons.scan.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import cn.lhkj.commons.defined.Transactional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.defined.OptLog;

/**
 * 1、使用dbcp连接池：service中调用方法开始时候建立连接，使用完毕或发生异常时关闭连接
 * 2、方法带事物时候提交或回滚事物
 * 3、方法带日志注解时候记录日志
 * @author zzy
 */
@Service("serverAspect")
@Aspect
public class ServerAspect {
	
	public static final String EDP = "execution(* cn.lhkj.project..*Service*.*(..))";
	
	/**
	 * 在调用Service中的方法之前 从连接池中取一个连接
	 * 注： public的方法才会生效 
	 * 带注解@Transactional的方法会开启事务
	 * @param jp
	 * @throws Exception
	 */
	@Before(EDP)
	public void beforeConnect(JoinPoint jp) throws Exception {
		MethodSignature signature = (MethodSignature)jp.getSignature();
		Method exMethod = signature.getMethod();//接口中的方法
		if(!Modifier.isPublic(exMethod.getModifiers())) return;
		
		Object obj = jp.getTarget();
		obj.getClass().getMethod("openDao").invoke(obj);
		
		Method implMethod = obj.getClass().getMethod(exMethod.getName(), exMethod.getParameterTypes());//实现类中的方法
		Annotation transactional = implMethod.getAnnotation(Transactional.class);
		if(transactional != null){//带事务的方法
			obj.getClass().getMethod("beginTranscation").invoke(obj);
		}
	}
	
	/**
	 * 在调用Service中的方法后关闭数据库连接 
	 * 注：public的方法才会生效
	 * @param jp
	 * @throws Exception
	 */
	@AfterReturning(EDP)
	public void afterReturning(JoinPoint jp) throws Exception {
		MethodSignature signature = (MethodSignature)jp.getSignature();
		Method exMethod = signature.getMethod();
		if(!Modifier.isPublic(exMethod.getModifiers())) return;
		
		Object obj = jp.getTarget();
		Method implMethod = obj.getClass().getMethod(exMethod.getName(), exMethod.getParameterTypes());//实现类中的方法
		Annotation transactional = implMethod.getAnnotation(Transactional.class);
		if(transactional != null){//带事务的方法
			obj.getClass().getMethod("commit").invoke(obj);
		}
		
		obj.getClass().getMethod("closeDao").invoke(obj);
		
		Annotation optLog = implMethod.getAnnotation(OptLog.class);
		if(optLog != null){//调用完成记录日志
//			OptLog lntLog = (OptLog)optLog;
//			OptLogClient.addLog(lntLog.info());
		}
	}
	
	/**
	 * 在目标方法抛出异常后调用
	 * @param jp
	 * @throws Exception
	 */
	@AfterThrowing (EDP)
	public void afterThrowing(JoinPoint jp) throws Exception {
		MethodSignature signature = (MethodSignature)jp.getSignature();
		Method exMethod = signature.getMethod();
		if(!Modifier.isPublic(exMethod.getModifiers())) return;
		
		Object obj = jp.getTarget();
		Method implMethod = obj.getClass().getMethod(exMethod.getName(), exMethod.getParameterTypes());//实现类中的方法
		Annotation myTransactional = implMethod.getAnnotation(Transactional.class);
		if(myTransactional != null){//带事务的方法
			obj.getClass().getMethod("rollback").invoke(obj);
		}
		obj.getClass().getMethod("closeDao").invoke(obj);
	}	
	
}
