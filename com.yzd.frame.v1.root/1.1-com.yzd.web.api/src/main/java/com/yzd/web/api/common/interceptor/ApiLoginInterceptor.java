package com.yzd.web.api.common.interceptor;

import com.yzd.session.session.CurrentUser;
import com.yzd.session.session.CurrentUserContextHolder;
import com.yzd.web.api.utils.fastjsonExt.FastJsonUtil;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 *
 * 拦截未登录的用户信息
 * @author yzd
 * @date 2018/9/13 14:13.
 */

public class ApiLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        //验证用户是否登陆
        CurrentUser currentUser = CurrentUserContextHolder.get();
        boolean isLogin = currentUser != null && currentUser.getId() > 0;
        if (BooleanUtils.isNotTrue(isLogin)) {
            response.setStatus(403);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResult = FastJsonUtil.serialize("用户没有登录，无访问权限");
            response.getWriter().write(jsonResult);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}