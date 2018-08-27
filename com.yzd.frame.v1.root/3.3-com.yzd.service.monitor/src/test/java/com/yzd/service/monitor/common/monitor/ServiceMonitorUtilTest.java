package com.yzd.service.monitor.common.monitor;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

/***
 *
 *
 * Created by yzd on 2018/8/27 13:27.
 */

public class ServiceMonitorUtilTest {

    @Test
    public void ServiceMonitorData_add_off() throws InterruptedException, ExecutionException {
        //offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.
        //插入监控数据
        for (int i = 0; i <12 ; i++) {
            ServiceMonitorData.getInstance().add(new AtomicLong(0));
        }
    }
    @Test
    public void addAndCollectData() throws InterruptedException, ExecutionException {
        //插入监控数据
        ServiceMonitorUtil.add("test");
        TimeUnit.SECONDS.sleep(10);
        //收集监控数据
        ServiceMonitorUtil.collectData();
        System.out.println(ServiceMonitorCache.getInstance().localCache.get("test"));
    }
}