package com.yzd.frame.common.mqExt2;

import com.yzd.frame.common.mq.redis.sharded.ShardedRedisMqUtil;
import org.junit.Test;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * 通过同步队列SynchronousQueue来合并多个redis数据源的读取结果
 * Created by zd.yao on 2017/7/11.
 */
public class RedisMqExtExample {
    String key = "RedisMq:SynchronousQueue";
    /**************************** redis 列表List扩展 start***************************/
    /**
     * 这是一种特殊情况-以Redis作为消息队列-并且队列内容特别的大
     * 这里会以List中的value的值做为分片的信息
     * 这样就可以实现水平扩展
     * 主要解决redis作为消息队列时出现数据倾斜的问题
     */
    @Test
    public void lpushExtExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        for (int i = 0; i < 100; i++) {
            Long result = redisUtil.lpushExt(key, "2017-07-11:" + i);
            System.out.println(result);
        }
    }

    /**
     * 通过同步队列SynchronousQueue来合并多个redis数据源的读取结果
     * @throws InterruptedException
     */
    @Test
    public void brpopExtByShardedJedisPoolExample() throws InterruptedException {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Collection<JedisShardInfo> jedisCollection = redisUtil.getAllJedisShardInfo();
        ExecutorService executorService = Executors.newFixedThreadPool(jedisCollection.size());
        SynchronousQueue<String> data = new SynchronousQueue<String>();
        for (JedisShardInfo j : jedisCollection) {
            ShardedJedisPool shardedJedisPool =redisUtil.getOneShardedJedisPool(j);
            JedisExecutorTask jedisExecutor = new JedisExecutorTask(shardedJedisPool, key,data);
            executorService.execute(jedisExecutor);
        }
        while (true){
            String value=data.take();
            System.out.println("brpopExtByShardedJedisPoolExample:value="+value);
        }
        //new CountDownLatch(1).await();
    }
}
