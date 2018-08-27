package com.yzd.service.monitor.controller;

import com.yzd.service.monitor.common.monitor.ServiceMonitorUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/2/24.
 */
@Slf4j
@Controller
public class IndexController {

    //
    @RequestMapping(value = {"", "/index"})
    @ResponseBody
    public String index() {
        log.info("插入监控数据");
        ServiceMonitorUtil.add("test");
        return "index/index=插入监控数据";
    }
}
