package com.yzd.web.logging;

import com.yzd.logging.consts.ParamEnum;
import com.yzd.logging.util.MDCUtil;
import com.yzd.logging.util.StringUtils;
import com.yzd.logging.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 分布式日志跟踪拦截器LogTraceInterceptor
 * LogTraceInterceptor等于LogURLInterceptor+ServletLogFilter的合并
 * 优点 出现异常时不再打印LogTraceInterceptor的堆栈信息，但如里使用ServletLogFilter则打印ServletLogFilter的堆栈信息
 */
public class LogTraceInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogTraceInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String traceId = httpServletRequest.getHeader(ParamEnum.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUIDUtil.getUUID();
        }
        MDCUtil.put(MDCUtil.Type.TRACE_ID, traceId);
        //记录每次请求的URL-打印用户请求的URI
        logger.info("Request URI: {};Method={};IP :{}", httpServletRequest.getRequestURI(),httpServletRequest.getMethod(),getIpAddr(httpServletRequest));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //请求处理完以后，需要清空当前的MDC的内容
        MDCUtil.clear();
    }
    /***
     * 客户端的真实IP地址
     * squid.conf 的配制文件　forwarded_for 项默认是为on，如果 forwarded_for 设成了 off 　则：
     * X-Forwarded-For: unknown
     * 参考：request.getRemoteAddr()怎么获取用户真实的IP地址
     * https://blog.csdn.net/lijunlinlijunlin/article/details/43714443
     * @param request
     * @return
     */
    String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0) { ip = request.getRemoteAddr(); }
        return ip;
    }
}
