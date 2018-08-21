package com.yzd.web.logging;

import com.yzd.web.util.fastjsonExt.FastJsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


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
    //将@Around调整为@Before ,不同之处在于：操作异常时，不再打印LogControllerAPIAspect的堆栈异常信息
    @Before("controllerMethodPointcut()")
    public void doBefore(JoinPoint pjp){
        //获得请求参数，目前缓存方法只接受一个请求参数
        Object[] param = pjp.getArgs();
        //用户请求的URI参数
        logParam(param);
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
    /**
     * 打印请求参数，但ServletRequest与ServletResponse对象无法进行json格式化
     * @param param
     * @return
     */
    private void logParam(Object[] param){
        if(param==null||param.length==0){
            return;
        }
        int len=param.length;
        Object[] paramTmp=new Object[param.length];
        for (int i = 0; i <len ; i++) {
            Object obj=param[i];
            if(obj instanceof ServletRequest){
                paramTmp[i]="ServletRequest";
                continue;
            }
            if(obj instanceof ServletResponse){
                paramTmp[i]="ServletResponse";
                continue;
            }
            //过滤文件类型参数
            if(obj instanceof MultipartFile){
                paramTmp[i]="MultipartFile";
                continue;
            }
            if(obj instanceof MultipartFile[]){
                paramTmp[i]="MultipartFile[]";
                continue;
            }
            paramTmp[i]=obj;
        }
        //fastjson 过滤不需要的字段或者只要某些字段
        //https://blog.csdn.net/stubbornness1219/article/details/52947013
        //对于敏感信息可以通过 transient或者@JSONField(serialize=false) 过滤掉
        //目前推荐使用transient关键词
        if(paramTmp.length>0){
            logger.info("Request URI Parameters:{}",FastJsonUtil.serialize(paramTmp));
        }
    }
}
