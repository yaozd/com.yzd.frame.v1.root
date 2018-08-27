package com.yzd.service.monitor.common.monitor;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/***
 *
 *
 * Created by yzd on 2018/8/27 12:57.
 */
@Slf4j
public class ServiceMonitorUtil {
    private static final String SeparateChar = "#";

    public static void add(String key) {
        try {
            String fullKey = getFullKey(key);
            AtomicLong value = ServiceMonitorCache.getInstance().localCache.get(fullKey);
            value.addAndGet(1L);
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    /***
     * 收集数据
     * 通过删除操作触发cache中的数据移除监听，然后把数据放入到同步阻塞队列之中，通过阻塞队列来进行数据的传递
     */
    public static void collectData() {
        Iterator<String> iterator = ServiceMonitorCache.getInstance().localCache.asMap().keySet().iterator();
        while (iterator.hasNext()) {
            String fullKey = iterator.next();
            String time = getTimeByFullKey(fullKey);
            if (time.equals(getNow())) {
                continue;
            }
            //不要使用keyAccessCount.invalidateAll清除所有，keyAccessCount.invalidateAll在运行20分钟后会自动停止
            ServiceMonitorCache.getInstance().localCache.invalidate(fullKey);
        }
    }

    /***
     * 发送数据
     */
    public static void sendData(){
        AtomicLong value=ServiceMonitorData.getInstance().take();
        log.info("发送数据="+value.toString());
    }

    private static String getFullKey(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(key).append(SeparateChar).append(getNow());
        return sb.toString();
    }

    private static String getNow() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        String time = formatter.format(currentTime);
        ;
        return time;
    }

    private static String getTimeByFullKey(String fullKey) {
        String[] arr = fullKey.split(SeparateChar);
        if (arr.length != 2) {
            throw new IllegalArgumentException("fullKey=" + fullKey + ";数据格式不正确");
        }
        return arr[1];
    }
}
