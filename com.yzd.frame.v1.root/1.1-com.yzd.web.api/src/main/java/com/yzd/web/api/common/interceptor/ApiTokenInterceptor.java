package com.yzd.web.api.common.interceptor;

import com.yzd.session.session.CurrentUser;
import com.yzd.session.session.CurrentUserContextHolder;
import com.yzd.web.api.utils.fastjsonExt.FastJsonUtil;
import com.yzd.web.api.utils.jwtExt.JWTUtil3;
import com.yzd.web.api.utils.jwtExt.VerifyResultJWT;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 验证api签名，拦截错误签名的请求
 *
 * @author yzd
 * @date 2018/9/12 16:42.
 */

public class ApiTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())){
            return true;
        }
        String accessToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.isBlank(accessToken)){
            httpServletResponse.setStatus(401);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            String jsonResult = FastJsonUtil.serialize("Not Found Token In Header");
            httpServletResponse.getWriter().write(jsonResult);
            return false;
        }
        //验证前必须要先移除ThreadLocal中的用户信息，防止用户串联在一起
        CurrentUserContextHolder.remove();
        //验证是否通过
        VerifyResultJWT verifyResultJWT = JWTUtil3.verifyToken(accessToken);
        if(BooleanUtils.isNotTrue(verifyResultJWT.getIsOk())){
            httpServletResponse.setStatus(401);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            String jsonResult = FastJsonUtil.serialize(verifyResultJWT);
            httpServletResponse.getWriter().write(jsonResult);
            return false;
        }
        //验证通过后把用户信息放入ThreadLocal中
        CurrentUser currentUser= FastJsonUtil.deserialize(verifyResultJWT.getUserJson(),CurrentUser.class);
        CurrentUserContextHolder.set(currentUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
