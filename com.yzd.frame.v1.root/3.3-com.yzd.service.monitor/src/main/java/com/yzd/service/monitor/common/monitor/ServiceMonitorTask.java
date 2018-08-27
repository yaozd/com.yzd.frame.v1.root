package com.yzd.service.monitor.common.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/***
 * 调试任务
 * 用于数据的收集与发送
 *
 * Created by yzd on 2018/8/27 14:56.
 */
@Slf4j
@Service
public class ServiceMonitorTask {
    /***
     * 收集数据
     * 通过删除操作触发cache中的数据移除监听，然后把数据放入到同步阻塞队列之中，通过阻塞队列来进行数据的传递
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 1000 * 15)
    public void collectData() {
        log.info("收集数据");
        ServiceMonitorUtil.collectData();
    }
    /***
     * 发送数据
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 1000 * 15)
    public static void sendData(){
        ServiceMonitorUtil.sendData();
        log.info("发送数据完成");
    }
}
