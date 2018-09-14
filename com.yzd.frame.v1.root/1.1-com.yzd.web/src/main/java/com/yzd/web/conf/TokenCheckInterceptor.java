package com.yzd.web.conf;

import com.yzd.web.util.cookieExt.CookieName;
import com.yzd.web.util.cookieExt.CookieUtil;
import com.yzd.web.util.feignExt.client.ITokenClientInf;
import com.yzd.web.util.feignExt.request.GetBaseTokenFeginForm;
import com.yzd.web.util.signExt.SignUtil;
import feign.Feign;
import feign.gson.GsonEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/***
 * 为每个请求增加非登录下访问的Token
 *
 * @author yzd
 * @date 2018/9/14 10:42.
 */

public class TokenCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        CookieUtil cookieUtil=new CookieUtil(request,response);
        Boolean isCreateBaseToken=checkTokenInCookie(cookieUtil);
        //判断是否需要创建新的token
        if(!isCreateBaseToken){return true;}
        String timestamp=String.valueOf(System.currentTimeMillis());
        GetBaseTokenFeginForm form=new GetBaseTokenFeginForm();
        //AppId最好是唯一的值
        form.setAppId(UUID.randomUUID().toString());
        form.setAppTypeId("1");
        form.setTimestamp(String.valueOf(timestamp));
        String sign=SignUtil.create(form);
        ITokenClientInf tokenApiService= Feign.builder()
                .encoder(new GsonEncoder())
                .target(ITokenClientInf.class,"http://localhost:8890/");
        String token= tokenApiService.getBaseToken(form,sign);
        cookieUtil.addCookie(CookieName.TOKEN.getKey(),token,CookieName.TOKEN.getExpiry());
        cookieUtil.addCookie(CookieName.TOKEN_TIME.getKey(),timestamp,CookieName.TOKEN_TIME.getExpiry());
        return true;
    }
    //cookie中的token在20天后会自动刷新：如果当前是登录后的token的话，就会被刷新为“普通的token”没有登录信息
    static final long day20=20*24*60*60*1000L;
    private Boolean checkTokenInCookie(CookieUtil cookieUtil) {
        String tokenStr= cookieUtil.getCookieValue(CookieName.TOKEN.getKey());
        if(StringUtils.isBlank(tokenStr)){return true;}
        String tokenTimeStr= cookieUtil.getCookieValue(CookieName.TOKEN_TIME.getKey());
        if(StringUtils.isBlank(tokenTimeStr)){return true;}
        if(!NumberUtils.isNumber(tokenTimeStr)){return true;}
        Long tokenTime=NumberUtils.createLong(tokenTimeStr);
        Long nowTime=System.currentTimeMillis();
        Long diffVal=nowTime-tokenTime;
        //20天以内则不需要重新创建，目前cookie的有效期是25天
        if(diffVal<day20){return false;}
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
