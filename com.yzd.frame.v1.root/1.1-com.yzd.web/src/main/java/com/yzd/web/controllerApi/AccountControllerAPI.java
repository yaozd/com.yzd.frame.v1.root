package com.yzd.web.controllerApi;

import com.yzd.web.util.cookieExt.CookieName;
import com.yzd.web.util.cookieExt.CookieUtil;
import com.yzd.web.util.feignExt.client.ITokenClientInf;
import com.yzd.web.util.feignExt.request.DoLoginFeginForm;
import com.yzd.web.util.feignExt.request.GetBaseTokenFeginForm;
import com.yzd.web.util.signExt.SignUtil;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/***
 *
 *
 * @author yzd
 * @date 2018/9/13 16:43.
 */
@RestController
@RequestMapping("/api/account")
public class AccountControllerAPI {
    //web端登录不写任何的接口，而是从前端JS完成所有操作
    @GetMapping("doLogin")
    public String doLogin(HttpServletRequest request, HttpServletResponse response ) {
        CookieUtil cookieUtil=new CookieUtil(request,response);
        String accessToken=cookieUtil.getCookieValue(CookieName.TOKEN.getKey());
        if(StringUtils.isBlank(accessToken)){
            return "accessToken is null";
        }
        DoLoginFeginForm feginForm=new DoLoginFeginForm();
        feginForm.setName("111");
        feginForm.setPassworld("2222");
        ITokenClientInf tokenApiService= Feign.builder()
                .logLevel(Logger.Level.FULL)
                .encoder(new GsonEncoder())
                .target(ITokenClientInf.class,"http://localhost:8890/");
        Map<String, Object> headerMap=new HashMap<>();
        headerMap.put("Authorization",accessToken);
        String loginToken= tokenApiService.doLogin(feginForm,headerMap);
        String timestamp=String.valueOf(System.currentTimeMillis());
        cookieUtil.addCookie(CookieName.TOKEN.getKey(),loginToken,CookieName.TOKEN.getExpiry());
        cookieUtil.addCookie(CookieName.TOKEN_TIME.getKey(),timestamp,CookieName.TOKEN_TIME.getExpiry());
        return "doLogin";
    }
    //参考：HTML页面的加载顺序
    //https://blog.csdn.net/m0_37550086/article/details/77513676
    // 不再使用拦截器的方式：TokenCheckInterceptor,而是用在html的header的最前头增加<script type="text/javascript" src="/api/account/getXX"></script>代替
    //为每个请求增加非登录下访问的Token
    @GetMapping("getXX")
    public void getXX(HttpServletRequest request, HttpServletResponse response ){
        //在html的header的最前头增加<script type="text/javascript" src="/api/account/getXX"></script>
        //这样会在所有JS脚本前执行此方法
        //如果当前调用/api/account/getXX 失败，当前页面可以执行两次的刷新，刷新计数可通过cookie实现
        //或者提示网络超时，让用户人为点击刷新
        CookieUtil cookieUtil=new CookieUtil(request,response);
        Boolean isCreateBaseToken=checkTokenInCookie(cookieUtil);
        //判断是否需要创建新的token
        if(!isCreateBaseToken){
            response.setStatus(304);
            return ;
        }
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
}
