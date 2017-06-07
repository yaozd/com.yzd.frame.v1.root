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
        boolean t = redisUtil.srem(orderMQ_set01, "db363586-6890-487d-9874-55dcdfa61cfb");
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

    //region 订单类型消息队列-示例
    //------------------
    private String orderMQ_set01 = "orderMQ_set01";
    private String orderMQ_list01 = "orderMQ_list01";
    private String orderMQ_MutexKey01 = "orderMQ_MutexKey01-";

    /**
     * 1 orderMQ-set-排重消息队列
     * 2 orderMQ-list-任务消息队列
     * 3 orderMQ-MutesKey-互斥锁-记录正在运行的任务-有效时间为5分钟-任务完成后主动删除MutesKey
     */

    /**
     * 消息队列-插入消息
     */
    @Test
    public void orderMQ_add_Example() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        for (int i = 0; i < 100; i++) {
            String uuid = UUID.randomUUID().toString();
            //todo 先插入到排重set消息队列中
            long countOfsadd = redisUtil.sadd(orderMQ_set01, uuid);
            //todo 判断当前消息是否存在-如果当前消息不存在，则插入到list消息队列中
            if (countOfsadd == 1) {
                //todo 常规操作-从尾部插入
                long countOflpush = redisUtil.rpush(orderMQ_list01, uuid);
                System.out.println(String.format("countOfsadd:%s,countOflpush:%s", countOfsadd, countOflpush));
            }
        }
    }

    /**
     * 消息队列-头部读取
     * 阻塞列表
     * 移出并获取列表的【第一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * todo 需要注意解决的两个异常：1-reids本身的异常 2-任务的操作异常 3-共同的网络中断产生的网络异常
     */
    @Test
    public void orderMQ_blpop_Example() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        for (int i = 0; i < 10000; i++) {
            ////todo Redis消息队列网络异常
            //当超期时间到达时，keys列表仍然没有内容，则返回Null
            //todo 常规操作-从头部读取
            String taskId = redisUtil.blpop(5, orderMQ_list01);
            System.out.println(taskId);
            if (taskId == null) {
                continue;
            }
            //taskId="dc29e81a-f7d5-4bad-b2d2-3a0c6da729ca";
            //todo 设置任务正在运行-有效时间为5分钟
            String mutexKey = orderMQ_MutexKey01 + taskId;
            String isOk = redisUtil.set(mutexKey, "1", "NX", "EX", 60 * 5);
            if (!"OK".equals(isOk)) {
                continue;
            }
            //todo 具体任务操作
            //todo 任务操作异常或数据库异常
            //第一步先验证数据库-当前的任务是否已经处理
            //-----------------------------------------
            //todo 先删除set排除消息对列再删除mutexKey互斥锁
            boolean valOfSrem = redisUtil.srem(orderMQ_set01, taskId);
            System.out.println(valOfSrem);
            redisUtil.del(mutexKey);
        }
    }

    /**
     * 5分钟循环定时任务--示例
     */
    @Test
    public void OrderMQ_Example() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        String key = orderMQ_set01;
        long total = redisUtil.scard(key);
        //todo 每5分钟获取当前消息的10%最多大值为200，进行消息删除重复消息；
        //200>countOfSrandMember>20
        int countOfSrandMember = (int) ((total + 10) * 0.1) + 20;
        countOfSrandMember = countOfSrandMember > 200 ? 200 : countOfSrandMember;
        List<String> setList = redisUtil.srandMember(key, countOfSrandMember);
        //
        for (String e : setList) {
            //todo 判断当前消息队列中是否存在此消息
            //todo 当count为负数时，移除方向是从尾到头-删除
            long valOfLrem = redisUtil.lrem(orderMQ_list01, -1, e);
            if (valOfLrem == 1) {
                //todo 常规操作-从头部插入
                redisUtil.lpush(orderMQ_list01, e);
                continue;
            }
            //todo 验证是否当前消息正在运行
            boolean isExists = redisUtil.exists(orderMQ_MutexKey01 + e);
            if (isExists) {
                continue;
            }
            //todo 当前消息中不存在此消息同时当前正在运行消息中也不存在，则进行删除set中的消息
            boolean valOfSrem = redisUtil.srem(orderMQ_set01, e);
            System.out.println(valOfSrem);
        }
    }
    //endregion
}
