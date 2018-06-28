package com.yzd.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/2/24.
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping(value = {"", "/index"})
    @ResponseBody
    public String index() {
        logger.info("IndexController->step-01");
        logger.info("IndexController->step-02");
        logger.info("IndexController->step-03");
        logger.info("IndexController->step-04");
        return "index/index";
    }
}
