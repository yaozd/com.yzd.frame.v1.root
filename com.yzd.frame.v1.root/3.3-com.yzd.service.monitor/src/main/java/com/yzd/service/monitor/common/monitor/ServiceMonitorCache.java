package com.yzd.service.monitor.common.monitor;

import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/***
 *
 *
 * Created by yzd on 2018/8/27 13:01.
 */
@Slf4j
public class ServiceMonitorCache {

    private static class SingletonHolder {
        private static final ServiceMonitorCache INSTANCE = new ServiceMonitorCache();
    }
    public static final ServiceMonitorCache getInstance() {
        return SingletonHolder.INSTANCE;
    }
    public LoadingCache<String, AtomicLong> localCache;
    //
    private ServiceMonitorCache (){
        LoadingCache<String, AtomicLong> cache;
        cache = CacheBuilder.newBuilder()
                //设置cache的初始大小为10，要合理设置该值
                .initialCapacity(10)
                //设置cache的最大容量为100，要合理设置该值
                .maximumSize(100)
                //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
                .concurrencyLevel(5)
                //设置cache中的数据在写入之后的存活时间为10秒-此处不设置过期时间，
                // 通过ServiceMonitorUtil.collectData()方法来进行无效数据的收集与剔除操作
                //.expireAfterWrite(10, TimeUnit.SECONDS)
                //设置cache中的数据移除监听
                .removalListener(new RemovalListener<String, AtomicLong>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, AtomicLong> removal) {
                        String key = removal.getKey();
                        AtomicLong value=removal.getValue();
                        Long count = value.get();
                        log.info("removalListener:removal.getKey()="+key+";count="+count);
                        //数据收到阻塞队列中
                        ServiceMonitorData.getInstance().add(value);
                    }
                })
                .build(new CacheLoader<String, AtomicLong>() {
                    @Override
                    public AtomicLong load(String key) {
                        return new AtomicLong(1);
                    }
                });
        //创建本地缓存
        localCache=cache;
    }
}
