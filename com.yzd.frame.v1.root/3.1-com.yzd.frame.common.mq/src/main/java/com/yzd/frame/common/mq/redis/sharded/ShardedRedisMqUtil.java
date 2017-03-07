package com.yzd.frame.common.mq.redis.sharded;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public class ShardedRedisMqUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShardedRedisMqUtil.class);

    private static final String DEFAULT_REDIS_SEPARATOR = ";";

    private static final String HOST_PORT_SEPARATOR = ":";
    private static final String WEIGHT_SEPARATOR = "\\*";
    private ShardedJedisPool shardedJedisPool = null;

    private static final ShardedRedisMqUtil INSTANCE = new ShardedRedisMqUtil();

    private ShardedRedisMqUtil() {
        initialShardedPool();
    }

    private void initialShardedPool() {
        // 操作超时时间,默认2秒
        int timeout = NumberUtils.toInt(SharedRedisConfig.getConfigProperty("redis.timeout"), 2000);
        // jedis池最大连接数总数，默认8
        int maxTotal = NumberUtils.toInt(SharedRedisConfig.getConfigProperty("redis.jedisPoolConfig.maxTotal"), 8);
        // jedis池最大空闲连接数，默认8
        int maxIdle = NumberUtils.toInt(SharedRedisConfig.getConfigProperty("redis.jedisPoolConfig.maxIdle"), 8);
        // jedis池最少空闲连接数
        int minIdle = NumberUtils.toInt(SharedRedisConfig.getConfigProperty("redis.jedisPoolConfig.minIdle"), 0);
        // jedis池没有对象返回时，最大等待时间单位为毫秒
        long maxWaitMillis = NumberUtils.toLong(SharedRedisConfig.getConfigProperty("redis.jedisPoolConfig.maxWaitTime"), -1);
        // 在borrow一个jedis实例时，是否提前进行validate操作
        boolean testOnBorrow = Boolean.parseBoolean(SharedRedisConfig.getConfigProperty("redis.jedisPoolConfig.testOnBorrow"));

        // 设置jedis连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);

        // 取得redis的url
        String redisUrls = SharedRedisConfig.getConfigProperty("redis.jedisPoolConfig.urls");
        if (redisUrls == null || redisUrls.trim().isEmpty()) {
            throw new IllegalStateException("the urls of redis is not configured");
        }
        LOGGER.info("the urls of redis is {}", redisUrls);
        // 生成连接池
        List<JedisShardInfo> shardedPoolList = new ArrayList<JedisShardInfo>();
        for (String redisUrl : redisUrls.split(DEFAULT_REDIS_SEPARATOR)) {
            JedisShardInfo Jedisinfo = new JedisShardInfo(redisUrl);
            Jedisinfo.setConnectionTimeout(timeout);
            Jedisinfo.setSoTimeout(timeout);
            shardedPoolList.add(Jedisinfo);
        }

        // 构造池
        this.shardedJedisPool = new ShardedJedisPool(poolConfig, shardedPoolList, Hashing.MURMUR_HASH);
    }

    public static ShardedRedisMqUtil getInstance() {
        return INSTANCE;
    }
    /**
     * 实现jedis连接的获取和释放，具体的redis业务逻辑由executor实现
     *
     * @param executor RedisExecutor接口的实现类
     * @return
     */
    public <T> T execute(String key, ShardedRedisExecutor<T> executor) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        T result = null;
        try {
            result = executor.execute(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**************************** redis 列表List start***************************/
    /**
     * 将一个值插入到列表头部，value可以重复，返回列表的长度
     * @param key
     * @param value String
     * @return 返回List的长度
     */
    public Long lpush(final String key, final String value){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long length = jedis.lpush(key, value);
                return length;
            }
        });
    }
    /**
     * 将多个值插入到列表头部，value可以重复
     * @param key
     * @param values String[]
     * @return 返回List的数量size
     */
    public  Long lpush(String key, String[] values){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long size = jedis.lpush(key, values);
                return size;
            }
        });
    }
    /**
     * 获取List列表
     * @param key
     * @param start long，开始索引
     * @param end long， 结束索引
     * @return List<String>
     */
    public  List<String> lrange(String key, long start, long end){
        return execute(key, new ShardedRedisExecutor<List<String>>() {
            @Override
            public List<String> execute(ShardedJedis jedis) {
                List<String> list = jedis.lrange(key, start, end);
                return list;
            }
        });
    }
    /**
     * 通过索引获取列表中的元素
     * @param key
     * @param index，索引，0表示最新的一个元素
     * @return String
     */
    public  String lindex(String key, long index){
        return execute(key, new ShardedRedisExecutor<String>() {
            @Override
            public String execute(ShardedJedis jedis) {
                String str = jedis.lindex(key, index);
                return str;
            }
        });
    }
    /**
     * 获取列表长度，key为空时返回0
     * @param key
     * @return Long
     */
    public  Long llen(String key){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long length = jedis.llen(key);
                return length;
            }
        });
    }
    /**
     * 在列表的元素前或者后插入元素，返回List的长度
     * @param key
     * @param where LIST_POSITION
     * @param pivot 以该元素作为参照物，是在它之前，还是之后（pivot：枢轴;中心点，中枢;[物]支点，支枢;[体]回转运动。）
     * @param value
     * @return Long
     */
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long length = jedis.linsert(key, where, pivot, value);
                return length;
            }
        });
    }
    /**
     * 将一个或多个值插入到已存在的列表头部，当成功时，返回List的长度；当不成功（即key不存在时，返回0）
     * @param key
     * @param value String
     * @return Long
     */
    public  Long lpushx(String key, String value){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long length = jedis.lpushx(key, value);
                return length;
            }
        });
    }
    /**
     * 移除列表元素，返回移除的元素数量
     * @param key
     * @param count，标识，表示动作或者查找方向
     * <li>当count=0时，移除所有匹配的元素；</li>
     * <li>当count为负数时，移除方向是从尾到头；</li>
     * <li>当count为正数时，移除方向是从头到尾；</li>
     * @param value 匹配的元素
     * @return Long
     */
    public  Long lrem(String key, long count, String value){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long length = jedis.lrem(key, count, value);
                return length;
            }
        });
    }
    /**
     * 通过索引设置列表元素的值，当超出索引时会抛错。成功设置返回true
     * @param key
     * @param index 索引
     * @param value
     * @return boolean
     */
    public  boolean lset(String key, long index, String value){
        return execute(key, jedis -> {
            String statusCode = jedis.lset(key, index, value);
            if("ok".equalsIgnoreCase(statusCode)){
                return true;
            }
            return false;
        });
    }
    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * @param key
     * @param start
     * <li>可以为负数（-1是列表的最后一个元素，-2是列表倒数第二的元素。）</li>
     * <li>如果start大于end，则返回一个空的列表，即列表被清空</li>
     * @param end
     * <li>可以为负数（-1是列表的最后一个元素，-2是列表倒数第二的元素。）</li>
     * <li>可以超出索引，不影响结果</li>
     * @return boolean
     */
    public  boolean ltrim(String key, long start, long end){
        return execute(key, jedis -> {
            String statusCode = jedis.ltrim(key, start, end);
            if("ok".equalsIgnoreCase(statusCode)){
                return true;
            }
            return false;
        });
    }
    /**
     * 移出并获取列表的第一个元素，当列表不存在或者为空时，返回Null
     * @param key
     * @return String
     */
    public  String lpop(String key){
        return execute(key, new ShardedRedisExecutor<String>() {
            @Override
            public String execute(ShardedJedis jedis) {
                String value = jedis.lpop(key);
                return value;
            }
        });
    }
    /**
     * 移除并获取列表最后一个元素，当列表不存在或者为空时，返回Null
     * @param key
     * @return String
     */
    public  String rpop(String key){
        return execute(key, new ShardedRedisExecutor<String>() {
            @Override
            public String execute(ShardedJedis jedis) {
                String value = jedis.rpop(key);
                return value;
            }
        });
    }
    /**
     * 在列表中的尾部添加一个个值，返回列表的长度
     * @param key
     * @param value
     * @return Long
     */
    public  Long rpush(String key, String value){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long length = jedis.rpush(key, value);
                return length;
            }
        });
    }
    /**
     * 在列表中的尾部添加多个值，返回列表的长度
     * @param key
     * @param values
     * @return Long
     */
    public  Long rpush(String key, String[] values){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long length = jedis.rpush(key, values);
                return length;
            }
        });
    }

    /**
     * 仅当列表存在时，才会向列表中的尾部添加一个值，返回列表的长度
     * @param key
     * @param value
     * @return Long
     */
    public  Long rpushx(String key, String value){
        return execute(key, new ShardedRedisExecutor<Long>() {
            @Override
            public Long execute(ShardedJedis jedis) {
                Long length = jedis.rpushx(key, value);
                return length;
            }
        });
    }
    /**
     * 移出并获取列表的【第一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param timeout 单位为秒
     * @param key
     * <li>当有多个key时，只要某个key值的列表有内容，即马上返回，不再阻塞。</li>
     * <li>当所有key都没有内容或不存在时，则会阻塞，直到有值返回或者超时。</li>
     * <li>当超期时间到达时，keys列表仍然没有内容，则返回Null</li>
     * @return List<String>
     */
    public  String blpop(int timeout, String key){
        return execute(key, new ShardedRedisExecutor<String>() {
            @Override
            public String execute(ShardedJedis jedis) {
                List<String> value = jedis.blpop(timeout, key);
                if(value==null||value.isEmpty()||value.size()<2)return null;
                return value.get(1);
            }
        });
    }
    /**
     * 移出并获取列表的【最后一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param timeout 单位为秒
     * @param key
     * <li>当有多个key时，只要某个key值的列表有内容，即马上返回，不再阻塞。</li>
     * <li>当所有key都没有内容或不存在时，则会阻塞，直到有值返回或者超时。</li>
     * <li>当超期时间到达时，keys列表仍然没有内容，则返回Null</li>
     * @return List<String>
     */
    public  String brpop(int timeout, String key){
        return execute(key, new ShardedRedisExecutor<String>() {
            @Override
            public String execute(ShardedJedis jedis) {
                List<String> value = jedis.brpop(timeout, key);
                if(value==null||value.isEmpty()||value.size()<2)return null;
                return value.get(1);
            }
        });
    }
    /**************************** redis 列表List end***************************/
}
