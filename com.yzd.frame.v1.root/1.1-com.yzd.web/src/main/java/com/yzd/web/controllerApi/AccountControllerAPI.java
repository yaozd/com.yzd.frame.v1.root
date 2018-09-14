package com.yzd.web.controllerApi;

import com.yzd.web.util.cookieExt.CookieName;
import com.yzd.web.util.cookieExt.CookieUtil;
import com.yzd.web.util.feignExt.client.ITokenClientInf;
import com.yzd.web.util.feignExt.request.DoLoginFeginForm;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonEncoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
}
