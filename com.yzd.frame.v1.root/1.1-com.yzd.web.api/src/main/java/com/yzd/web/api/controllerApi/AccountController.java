package com.yzd.web.api.controllerApi;

import com.yzd.session.session.CurrentUser;
import com.yzd.web.api.model.request.account.DoLoginForm;
import com.yzd.web.api.utils.jwtExt.JWTUtil3;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *
 *
 * @author yzd
 * @date 2018/9/13 14:19.
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {
    @PostMapping("doLogin")
    public String doLogin(DoLoginForm form) {
        String name=form.getName().trim();
        String passworld=form.getPassworld().trim();
        CurrentUser currentUser= CurrentUser.createLoginUser(1,name,1);
        String token=JWTUtil3.createToken(currentUser);
        return token;
    }
    @PostMapping("doLogout")
    public String doLogout(){
        return "doLogout";
    }
}
