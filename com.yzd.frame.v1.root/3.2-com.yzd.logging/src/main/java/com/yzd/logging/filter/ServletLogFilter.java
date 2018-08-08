package com.yzd.logging.filter;

import com.yzd.logging.consts.ParamEnum;
import com.yzd.logging.util.MDCUtil;
import com.yzd.logging.util.StringUtils;
import com.yzd.logging.util.UUIDUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class ServletLogFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(ParamEnum.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUIDUtil.getUUID();;
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