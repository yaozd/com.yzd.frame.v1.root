package com.yzd.logging.filter;

import com.yzd.logging.consts.ParamEnum;
import com.yzd.logging.util.MDCUtil;
import com.yzd.logging.util.StringUtils;
import com.yzd.logging.util.UUIDUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/***
 *
 * 参考同：Java过滤器与SpringMVC拦截器之间的关系与区别
 * https://blog.csdn.net/chenleixing/article/details/44573495
 * 所以在spring框架中优先使用拦截器，但需要使用spring mvc,目前暂时使用过滤器ServletLogFilter
 *
 */
public class ServletLogFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(ParamEnum.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUIDUtil.getUUID();
        }
        MDCUtil.put(MDCUtil.Type.TRACE_ID, traceId);
        filterChain.doFilter(servletRequest,servletResponse);
        MDCUtil.clear();
    }
    @Override
    public void init(FilterConfig filterConfig){ }
    @Override
    public void destroy() { }
}