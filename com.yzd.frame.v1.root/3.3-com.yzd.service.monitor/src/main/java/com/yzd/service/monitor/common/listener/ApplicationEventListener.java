package com.yzd.service.monitor.common.listener;

import com.yzd.service.monitor.common.monitor.ServiceMonitorUtil;
import com.yzd.service.monitor.config.ScheduleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/***
 *
 *
 * Created by yzd on 2018/8/28 9:06.
 */
@Slf4j
public class ApplicationEventListener implements ApplicationListener {

    /***
     * 监听Spring Boot的启动、停止、重启、关闭
     * http://blog.sina.com.cn/s/blog_70ae1d7b0102wfq2.html
     * // 在这里可以监听到Spring Boot的生命周期
     * if (event instanceof ApplicationEnvironmentPreparedEvent) { // 初始化环境变量 }
     * else if (event instanceof ApplicationPreparedEvent) { // 初始化完成 }
     * else if (event instanceof ContextRefreshedEvent) { // 应用刷新 }
     * else if (event instanceof ApplicationReadyEvent) {// 应用已启动完成 }
     * else if (event instanceof ContextStartedEvent) { // 应用启动，需要在代码动态添加监听器才可捕获 }
     * else if (event instanceof ContextStoppedEvent) { // 应用停止 }
     * else if (event instanceof ContextClosedEvent) { // 应用关闭 }
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        log.info("XXX"+applicationEvent.toString());
        //ServletRequestHandledEvent 请求处理事件
        //XXXServletRequestHandledEvent: url=[/]; client=[0:0:0:0:0:0:0:1]; method=[GET]; servlet=[dispatcherServlet]; session=[null]; user=[null]; time=[1ms]; status=[OK]
        //===================================================
        if(applicationEvent instanceof ServletRequestHandledEvent){
            //一般怦是不会添加此事件监听
            log.info("ServletRequestHandledEvent 请求处理事件");
            return;
        }
        //应用关闭-kill PID 不要使用kill -9 PID
        if (applicationEvent instanceof ContextClosedEvent){
            log.info("程序关闭发送所有监控数据");
            //计划任务的中断等待时间设置在：ScheduleConfig文件中
            ServiceMonitorUtil.shutdownEvent();
            ServiceMonitorUtil.sendAllData();
            //
            return;
        }
    }
}
