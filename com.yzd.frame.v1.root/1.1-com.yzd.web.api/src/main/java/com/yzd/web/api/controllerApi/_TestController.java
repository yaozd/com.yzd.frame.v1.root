package com.yzd.web.api.controllerApi;

import com.yzd.session.session.CurrentUser;
import com.yzd.web.api.utils.jwtExt.JWTUtil3;
import com.yzd.web.api.utils.jwtExt.RefreshResultJWT;
import com.yzd.web.api.utils.jwtExt.VerifyResultJWT;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/2/24.
 */
@RestController
@RequestMapping("/api/test")
public class _TestController {
    @PostMapping("doLogin")
    public String doLogin() {
        return "doLogin";
    }
    @GetMapping("token/getToken")
    public String getToken(){
        //创建 token,当user为null时是未登录的访问token
        String token=JWTUtil3.createToken(null);
        return token;
    }
    @PostMapping("token/verifyToken")
    public VerifyResultJWT verifyToken(String token){
        VerifyResultJWT result=JWTUtil3.verifyToken(token);
        return result;
    }
    @PostMapping("token/refreshToken")
    public RefreshResultJWT refreshToken(String oldToken){
        RefreshResultJWT refreshResultJWT=JWTUtil3.refreshToken(oldToken,Integer.class);
        return refreshResultJWT;
    }
}

