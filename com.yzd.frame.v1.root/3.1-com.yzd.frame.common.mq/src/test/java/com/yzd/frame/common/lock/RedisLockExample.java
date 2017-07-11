package com.yzd.frame.common.lock;

import com.yzd.frame.common.mq.redis.sharded.ShardedRedisMqUtil;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;

/**
 * Created by zd.yao on 2017/7/11.
 */
public class RedisLockExample {
    /**
     * 解锁redis锁的正确姿势
     * http://www.cnblogs.com/yjf512/p/6597814.html
     */
    @Test
    public void lockExample(){
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String timestamp=String.valueOf(System.currentTimeMillis()) ;
        String key ="my:lock";
        long timeoutSecond=10;
        //超时时间必须大于业务逻辑执行的时间-锁才会有效果
        String isLock= redisUtil.set(key, timestamp, "nx", "ex", timeoutSecond);
        if(ObjectUtils.equals(isLock,"OK")){
            //具体的业务逻辑
            String timestampInRedis=redisUtil.get(key);
            //有些情况下任务是一个循环
            //所以我们可以把锁的粒度定义在单次循环的时间内有锁的有效时间
            //这种情况下锁的有效时间就变为了相对有效时间
            if(ObjectUtils.equals(timestamp,timestampInRedis)){
                redisUtil.expire(key, (int) timeoutSecond);
            }
            //当且仅当这个lock不存在的时候，设置完成之后设置过期时间为10。
            //获取锁的机制是对了，但是删除锁的机制直接使用del是不对的。因为有可能导致误删别人的锁的情况。

            if(ObjectUtils.equals(timestamp,timestampInRedis)){
                redisUtil.del(key);
            }
        }
        System.out.println("redis-lockExample");
    }
}
