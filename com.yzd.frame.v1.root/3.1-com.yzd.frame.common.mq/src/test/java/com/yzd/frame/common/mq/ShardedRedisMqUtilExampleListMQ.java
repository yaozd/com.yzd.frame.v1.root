package com.yzd.frame.common.mq;

import com.yzd.frame.common.mq.redis.sharded.ShardedRedisMqUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Collection;

/**
 * Created by Administrator on 2017/3/7.
 */
public class ShardedRedisMqUtilExampleListMQ {
    String key="ShardedRedisMqUtilExampleListMQ";

    /**
     * 列表头部-插入
     * 将一个值插入到列表头部，value可以重复，返回列表的长度
     */
    @Test
    public void lpushExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        for (int i=0;i<100;i++){
            Long result = redisUtil.lpush(key, "2017-03-07:"+i);
            System.out.println(result);
        }
    }
    /**
     * 列表头部-读取
     * 移出并获取列表的第一个元素，当列表不存在或者为空时，返回Null
     */
    @Test
    public void lpopExample1() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        //String result= redisUtil.lpop(key);
        //System.out.println(result);
        for (int i=0;i<10000;i++){
            String result= redisUtil.lpop(key);
            System.out.println(result);
        }
    }
    /**
     * 列表头部-读取
     * 阻塞列表
     * 移出并获取列表的【第一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     */
    @Test
    public void blpopExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        //String result= redisUtil.lpop(key);
        //System.out.println(result);
        for (int i=0;i<10000;i++){
            String result= redisUtil.blpop(5,key);
            System.out.println(result);
        }
    }

    /**
     * 列表尾部-读取
     * 移除并获取列表最后一个元素，当列表不存在或者为空时，返回Null
     */
    @Test
    public void rpopExample1() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        //String result= redisUtil.lpop(key);
        //System.out.println(result);
        for (int i=0;i<10000;i++){
            String result= redisUtil.rpop(key);
            System.out.println(result);
        }
    }

    /**
     * 列表尾部-读取
     * 阻塞列表
     * 移出并获取列表的【最后一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     */
    @Test
    public void brpopExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        //String result= redisUtil.lpop(key);
        //System.out.println(result);
        for (int i=0;i<10000; i++) {
            String result= redisUtil.brpop(5,key);
            System.out.println(result);
        }
    }
    /**************************** redis 列表List扩展 start***************************/
    /**
     *  这是一种特殊情况-以Redis作为消息队列-并且队列内容特别的大
     *  这里会以List中的value的值做为分片的信息
     *  这样就可以实现水平扩展
     *  主要解决redis作为消息队列时出现数据倾斜的问题
     */
    @Test
    public void lpushExtExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        for (int i=0;i<100;i++){
            Long result = redisUtil.lpushExt(key, "2017-03-07:" + i);
            System.out.println(result);
        }
    }
    @Test
    public void getAllShardsExample() {
        ShardedRedisMqUtil redisUtil = ShardedRedisMqUtil.getInstance();
        Collection<Jedis> jedisCollection= redisUtil.getAllShards();
    }
    /**************************** redis 列表List扩展 end***************************/
}
