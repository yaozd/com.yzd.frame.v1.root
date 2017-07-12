package com.yzd.frame.common.lock;

import com.yzd.frame.common.mq.redis.sharded.ShardedRedisMqUtil;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by zd.yao on 2017/7/11.
 */
public class RedisLockExample {
    String KEY_TEST="KEY-TEST_LOCK";
    /**
     * 解锁redis锁的正确姿势
     * http://www.cnblogs.com/yjf512/p/6597814.html
     */
    @Test
    public void lockExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String key = KEY_TEST;
        long timeoutSecond = 10;
        //超时时间必须大于业务逻辑执行的时间-锁才会有效果
        String isLock = redisUtil.set(key, timestamp, "nx", "ex", timeoutSecond);
        if (ObjectUtils.equals(isLock, "OK")) {
            //具体的业务逻辑
            String timestampInRedis = redisUtil.get(key);
            //有些情况下任务是一个循环
            //所以我们可以把锁的粒度定义在单次循环的时间内有锁的有效时间
            //这种情况下锁的有效时间就变为了相对有效时间
            if (ObjectUtils.equals(timestamp, timestampInRedis)) {
                redisUtil.expire(key, (int) timeoutSecond);
            }
            //当且仅当这个lock不存在的时候，设置完成之后设置过期时间为10。
            //获取锁的机制是对了，但是删除锁的机制直接使用del是不对的。因为有可能导致误删别人的锁的情况。

            if (ObjectUtils.equals(timestamp, timestampInRedis)) {
                redisUtil.del(key);
            }
        }
        System.out.println("redis-lockExample");
    }

    /**
     * 有一大的前提：假设程序运行的服务没有发生网络抖动的情况
     * 有一些统计任务或者报表任务不是短时间可以运行完成
     * 就可以通过一个单独的线程来维护过期时间来解决长时间执行的任务
     * 使用情况：后台调度任务或补录程序
     */
    @Test
    public void lockForLongTimeExample() {
        String key = KEY_TEST;
        long timeoutSecond = 10;
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String timestamp = String.valueOf(System.currentTimeMillis());
        CountDownLatch latch = new CountDownLatch(1);
        //超时时间必须大于业务逻辑执行的时间-锁才会有效果
        String isLock = redisUtil.set(key, timestamp, "nx", "ex", timeoutSecond);
        if (ObjectUtils.equals(isLock, "OK")) {
            //有些情况下任务是一个循环
            //所以我们可以把锁的粒度定义在单次循环的时间内有锁的有效时间
            //这种情况下锁的有效时间就变为了相对有效时间
            new ExpireUpdateThread(key, (int) timeoutSecond, timestamp, latch).start();
            //当且仅当这个lock不存在的时候，设置完成之后设置过期时间为10。
            //获取锁的机制是对了，但是删除锁的机制直接使用del是不对的。因为有可能导致误删别人的锁的情况。
            //
            //具休的业务逻辑
            try {
                //模拟业务执行时间
                TimeUnit.SECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //业务完成
                String timestampInRedis = redisUtil.get(key);
                if (ObjectUtils.equals(timestamp, timestampInRedis)) {
                    redisUtil.del(key);
                }
                latch.countDown();
            }

        }
        System.out.println("redis-lockForLongTimeExample");
    }
}
