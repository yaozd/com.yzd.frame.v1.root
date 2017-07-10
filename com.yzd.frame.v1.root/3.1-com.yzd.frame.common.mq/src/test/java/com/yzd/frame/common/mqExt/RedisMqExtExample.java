package com.yzd.frame.common.mqExt;

import com.yzd.frame.common.mq.redis.sharded.ShardedRedisMqUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zd.yao on 2017/7/7.
 */
public class RedisMqExtExample {
    String key = "RedisMqExtExampleListMQKey";
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
            Long result = redisUtil.lpushExt(key, "2017-07-07:" + i);
            System.out.println(result);
        }
    }

    @Test
    public void getAllShardsExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Collection<Jedis> jedisCollection = redisUtil.getAllShards();
    }

    @Test
    public void getgetAllShardInfoExample() throws InterruptedException {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Collection<JedisShardInfo> jedisCollection = redisUtil.getAllShardInfo();
        // 设置jedis连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(3);
        poolConfig.setMaxWaitMillis(60000);
        poolConfig.setTestOnBorrow(true);
        //生成jedis的单一redis的线程池
        for (JedisShardInfo j : jedisCollection) {
            ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, Arrays.asList(j), Hashing.MURMUR_HASH);
        }
    }

    @Test
    public void brpopExtExample() throws InterruptedException {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Collection<JedisShardInfo> jedisCollection = redisUtil.getAllShardInfo();
        // 设置jedis连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(3);
        poolConfig.setMaxWaitMillis(60000);
        poolConfig.setTestOnBorrow(true);
        ExecutorService executorService = Executors.newFixedThreadPool(jedisCollection.size());
        for (JedisShardInfo j : jedisCollection) {
            ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, Arrays.asList(j), Hashing.MURMUR_HASH);
            JedisExecutorTask jedisExecutor = new JedisExecutorTask(shardedJedisPool, key);
            executorService.execute(jedisExecutor);
        }
        new CountDownLatch(1).await();
    }
    /**************************** redis 列表List扩展 end***************************/
}
