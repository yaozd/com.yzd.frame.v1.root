package com.yzd.web.api.controllerApi;

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
    @RequestMapping("doLogin")
    public String doLogin() {
        return "test/doLogin";
    }
}

