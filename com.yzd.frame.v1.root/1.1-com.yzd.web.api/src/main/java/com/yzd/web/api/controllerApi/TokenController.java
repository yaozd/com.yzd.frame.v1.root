package com.yzd.web.api.controllerApi;

import com.yzd.session.session.CurrentUser;
import com.yzd.web.api.common.exceptionExt.DataValidException;
import com.yzd.web.api.common.exceptionExt.TokenValidException;
import com.yzd.web.api.model.request.token.BaseTokenForm;
import com.yzd.web.api.model.request.token.GetBaseTokenForm;
import com.yzd.web.api.model.request.token.RefreshFrom;
import com.yzd.web.api.utils.jwtExt.JWTUtil3;
import com.yzd.web.api.utils.jwtExt.RefreshResultJWT;
import com.yzd.web.api.utils.signExt.SignUtil;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zd.yao on 2018/9/12.
 */
@RestController
@RequestMapping("/api/token")
public class TokenController {
    private static final Long min5=5*60*1000L;
    @PostMapping("getBaseToken")
    public String getBaseToken(@RequestBody GetBaseTokenForm form, String sign){
        filterForGetBaseToken(form, sign);
        //创建 token,当user为null时是未登录的访问token
        String token= JWTUtil3.createToken(CurrentUser.createEmptyUser());
        return token;
    }
    @PostMapping(value = "refresh")
    public RefreshResultJWT refresh(RefreshFrom form,String sign){
        filterForGetBaseToken(form, sign);
        RefreshResultJWT refreshResultJWT=JWTUtil3.refreshToken(form.getRefreshToken(),CurrentUser.class);
        return refreshResultJWT;
    }

    /***
     * getBaseToken方法的请求数据有效性进行过滤验证
     * @param form
     * @param sign
     */
    private void filterForGetBaseToken(BaseTokenForm form, String sign) {
        if(StringUtils.isBlank(sign)){
            throw new TokenValidException("//签名不能为空");
        }
        Long tokenTimestamp= NumberUtils.createLong(form.getTimestamp());
        Long nowTimestamp=System.currentTimeMillis();
        Long diffVal=Math.abs(nowTimestamp-tokenTimestamp);
        if(diffVal>min5){
            throw new TokenValidException("//只接受5分钟内的时间差");
        }
        if(BooleanUtils.isNotTrue(SignUtil.check(form, sign))){
            throw new TokenValidException("//签名不正确");
        }
    }
}
