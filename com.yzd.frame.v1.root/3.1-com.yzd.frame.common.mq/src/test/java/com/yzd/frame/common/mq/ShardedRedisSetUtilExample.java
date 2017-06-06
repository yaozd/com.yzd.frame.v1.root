package com.yzd.frame.common.mq;

import com.yzd.frame.common.mq.redis.sharded.ShardedRedisMqUtil;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by zd.yao on 2017/6/5.
 */
public class ShardedRedisSetUtilExample {

    @Test
    public void addExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        long val = redisUtil.sadd("set-default", "1");
        System.out.println(val);
    }

    @Test
    public void rpushExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String key = "rpush-default";
        long lremVal = redisUtil.lrem(key, 1, "1");
        long val = redisUtil.rpush("rpush-default", new String[]{"2", "22222"});
        System.out.println(val);
    }

    @Test
    public void lremExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String key = "rpush-default";
        long lremVal = redisUtil.lrem(key, 1, "1");
        System.out.println(lremVal);
    }

    private String srandMember_set01 = "srandMember_set01";

    @Test
    public void srandMemberExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String key = srandMember_set01;
        long count = redisUtil.scard(key);
        List<String> setList = redisUtil.srandMember(key, 10);
        System.out.println(String.format("key:%s,count:%s,size:%s", key, count, setList.size()));
    }

    @Test
    public void orderMQ_add_Example() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        for (int i = 0; i < 100; i++) {
            String uuid=UUID.randomUUID().toString();
            long countOfsadd = redisUtil.sadd(orderMQ_set01, uuid);
            long countOflpush = redisUtil.lpush(orderMQ_list01, uuid);
            System.out.println(String.format("countOfsadd:%s,countOflpush:%s", countOfsadd,countOflpush));
        }
    }
    //------------------
    private String orderMQ_set01 = "orderMQ_set01";
    private String orderMQ_list01 = "orderMQ_list01";
    private String orderMQ_MutexKey01 = "orderMQ_MutexKey01:";

    /**
     * 5分钟循环定时任务--示例
     */
    @Test
    public void OrderMQ_Example(){
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String key = orderMQ_set01;
        long total = redisUtil.scard(key);
        //todo 每5分钟获取当前消息的10%最多大值为200，进行消息删除重复消息；
        //200>countOfSrandMember>20
        int countOfSrandMember=(int)((total+10)*0.1)+20;
         countOfSrandMember= countOfSrandMember>200?200: countOfSrandMember;
        List<String> setList = redisUtil.srandMember(key, countOfSrandMember);
        //
        for (String e : setList){
            //todo 判断当前消息队列中是否存在此消息
            long valOfLrem= redisUtil.lrem(orderMQ_list01,1,e);
            if(valOfLrem==1){
                redisUtil.lpush(orderMQ_list01,e);
                continue;
            }
            //todo 验证是否当前消息正在运行
            boolean isExists=redisUtil.exists(orderMQ_MutexKey01+e);
            if(isExists){
                continue;
            }
            //todo 当前消息中不存在此消息同时当前正在运行消息中也不存在，则进行删除set中的消息
            boolean valOfSrem=redisUtil.srem(orderMQ_set01,e);
            System.out.println(valOfSrem);
        }
    }
}
