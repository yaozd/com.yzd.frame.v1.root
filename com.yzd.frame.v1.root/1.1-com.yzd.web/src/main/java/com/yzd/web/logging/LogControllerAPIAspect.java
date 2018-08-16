package com.yzd.web.logging;

import com.yzd.web.util.fastjsonExt.FastJsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogControllerAPIAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogControllerAPIAspect.class);
    /**
     * 记录restfull api 的请求参数
     * 定义拦截规则：拦截com.yzd.web.controllerApi包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* com.yzd.web.controllerApi..*(..))) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}
    /**
     * 指定拦截器规则
     * 拦截器具体实现
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        //获得请求参数，目前缓存方法只接受一个请求参数
        Object[] param = pjp.getArgs();
        if(hasParam(param)){logger.info("用户请求的URI参数:{}",FastJsonUtil.serialize(param));}
        return pjp.proceed();
    }
    /**
     * 当前方法是否有参数
     * @param param
     * @return
     */
    private boolean hasParam(Object[] param){
        if(param==null||param.length==0){
            return false;
        }
        return true;
    }
}
