package com.yzd.frame.common.mq;

import com.yzd.frame.common.mq.redis.sharded.ShardedRedisMqUtil;
import org.junit.Test;
import redis.clients.jedis.BinaryClient;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public class ShardedRedisMqUtilExample {
    String key="ShardedRedisMqUtilExample";
    @Test
    public void lpushExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Long result = redisUtil.lpush(key, "2017-03-06");
        System.out.println(result);
    }
    @Test
    public void lpushValuesExample() {
        String[] values={"1","2","3"};
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Long result = redisUtil.lpush(key, values);
        System.out.println(result);
    }
    @Test
    public void lrangeExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        List<String> result = redisUtil.lrange(key, 1,5);
        System.out.println(result);
    }
    @Test
    public void lindexExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String result = redisUtil.lindex(key, 0L);
        System.out.println(result);
    }
    @Test
    public void llenExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Long result = redisUtil.llen(key);
        System.out.println(result);
    }
    @Test
    public void linsertExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Long result = redisUtil.linsert(key, BinaryClient.LIST_POSITION.BEFORE,"3","4");
        System.out.println(result);
    }
    @Test
    public void lpushxExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Long result = redisUtil.lpushx(key, "5");
        System.out.println(result);
    }
    @Test
    public void lremExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Long result = redisUtil.lrem(key, 1, "5");
        System.out.println(result);
    }
    @Test
    public void lsetExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        boolean result = redisUtil.lset(key, 9,"5");
        System.out.println(result);
    }
    @Test
    public void ltrimExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        boolean result = redisUtil.ltrim(key, 0, 9);
        System.out.println(result);
    }
    @Test
    public void lpopExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String result = redisUtil.lpop(key);
        System.out.println(result);
    }
    @Test
    public void rpopExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String result = redisUtil.rpop(key);
        System.out.println(result);
    }
    @Test
    public void rpushExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Long result = redisUtil.rpush(key, "2017-03-07-1213-01");
        System.out.println(result);
    }
    @Test
    public void rpushValuesExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String[] values={"7-1","7-2","7-3"};
        long result = redisUtil.rpush(key,values);
        System.out.println(result);
    }
    @Test
    public void rpushxExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Long result = redisUtil.rpushx(key,"rpushx-01");
        System.out.println(result);
    }
    @Test
    public void blpopExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String result = redisUtil.blpop(5, key);
        System.out.println(result);
    }
    @Test
    public void brpopExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String result = redisUtil.brpop(3,key);
        System.out.println(result);
    }
}
