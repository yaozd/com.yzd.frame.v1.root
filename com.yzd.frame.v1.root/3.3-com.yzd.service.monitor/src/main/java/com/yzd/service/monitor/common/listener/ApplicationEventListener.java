package com.yzd.service.monitor.common.listener;

import com.yzd.service.monitor.common.monitor.ServiceMonitorUtil;
import com.yzd.service.monitor.config.ScheduleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/***
 *
 *
 * Created by yzd on 2018/8/28 9:06.
 */
@Slf4j
public class ApplicationEventListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        log.info("XXX"+applicationEvent.toString());
        //应用关闭-kill PID 不要使用kill -9 PID
        if (applicationEvent instanceof ContextClosedEvent){
            log.info("程序关闭发送所有监控数据");
            //计划任务的中断等待时间设置在：ScheduleConfig文件中
            ServiceMonitorUtil.shutdownEvent();
            ServiceMonitorUtil.sendAllData();
            //

        }
    }
}
