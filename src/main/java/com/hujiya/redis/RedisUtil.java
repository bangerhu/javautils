package com.hujiya.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陌上丶天琊
 * 描述： redis操作类
 */
@Component
public class RedisUtil {
    private Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private RedisTemplate redisTemplate;
    private ValueOperations string;
    private ListOperations list;
    private HashOperations hash;
    private SetOperations set;
    private ZSetOperations zSet;

    @Autowired
    private void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        string = this.redisTemplate.opsForValue();
        list = this.redisTemplate.opsForList();
        hash = this.redisTemplate.opsForHash();
        set = this.redisTemplate.opsForSet();
        zSet = this.redisTemplate.opsForZSet();
    }

    // region -- 读操作
    // 字符串
    public Object get(String key) {
        Object obj = null;
        try {
            obj = string.get(key);
        } catch (Exception e) {
            logger.error("get: {}", e);
        }
        return obj;
    }

    // Hash值
    public Object hashGet(String key, Object hashKey) {
        Object obj = null;
        try {
            obj = hash.get(key, hashKey);
        } catch (Exception e) {
            logger.error("hashGet: {}", e);
        }
        return obj;
    }

    public List multiGet(String key, Collection fields) {
        List obj = null;
        try {
            obj = hash.multiGet(key, fields);
        } catch (Exception e) {
            logger.error("multiGet: {}", e);
        }
        return obj;
    }

    // 列表
    public Object leftPop(String k) {
        Object obj = null;
        try {
            obj = list.leftPop(k);
        } catch (Exception e) {
            logger.error("leftPop: {}", e);
        }
        return obj;
    }

    // 列表
    public List<Object> listRange(String k, long l, long l1) {
        List<Object> obj = null;
        try {
            obj = list.range(k, l, l1);
        } catch (Exception e) {
            logger.error("listRange: {}", e);
        }
        return obj;
    }

    // 集合
    public Set<Object> setMembers(String key) {
        Set<Object> obj = null;
        try {
            obj = set.members(key);
        } catch (Exception e) {
            logger.error("setMembers: {}", e);
        }
        return obj;
    }

    // 有序集合
    public Set<Object> setRangeByScore(String key, double source, double source1) {
        Set<Object> obj = null;
        try {
            obj = zSet.rangeByScore(key, source, source1);
        } catch (Exception e) {
            logger.error("setRangeByScore: {}", e);
        }
        return obj;
    }
    // endregion

    // region -- 写操作
    // 字符串
    public boolean set(String key, Object value) {
        boolean result = false;
        try {
            string.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error("set: {}", e);
        }
        return result;
    }

    // 字符串
    @SuppressWarnings("unchecked")
    /**
     *  expireTime 秒
     */
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            string.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("set: {}", e);
        }
        return result;
    }

    // Hash
    public void hashPut(String key, Object hashKey, Object value) {
        try {
            hash.put(key, hashKey, value);
        } catch (Exception e) {
            logger.error("hashPut: {}", e);
        }
    }

    // 列表
    public void listLeftPush(String k, Object v) {
        try {
            list.leftPush(k, v);
        } catch (Exception e) {
            logger.error("listLeftPush: {}", e);
        }
    }

    public Long rightPushAll(String key, Collection values) {
        try {
            return list.rightPushAll(key, values);
        } catch (Exception e) {
            logger.error("listLeftPush: {}", e);
            return 0L;
        }
    }

    // 集合
    public void setAdd(String key, Object value) {
        try {
            set.add(key, value);
        } catch (Exception e) {
            logger.error("setAdd: {}", e);
        }
    }

    // 有序集合
    public void zSetAdd(String key, Object value, double source) {
        try {
            zSet.add(key, value, source);
        } catch (Exception e) {
            logger.error("zSetAdd: {}", e);
        }
    }
    // endregion

    // region -- 删操作
    // 删 单个key
    @SuppressWarnings("unchecked")
    public void delete(String key) {
        Boolean flag = hasKey(key);
        if (flag != null && flag) {
            try {
                redisTemplate.delete(key);
            } catch (Exception e) {
                logger.error("delete: {}", key, e);
            }
        }
    }

    // region -- 删操作
    // 删 单个key
    @SuppressWarnings("unchecked")
    public void deleteDirect(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("delete: {}", key, e);
        }
    }

    // 删 多个key
    public void delete(String... keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    // 删 正则匹配
    @SuppressWarnings("unchecked")
    public void deleteByPattern(String pattern) {
        try {
            Set<Serializable> keys = redisTemplate.keys(pattern);
            if (keys != null && keys.size() > 0) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            logger.error("deleteByPattern: {}", e);
        }
    }

    // 删 Hash键
    public void hashDelete(String masterKey, Object... hashKey) {
        try {
            hash.delete(masterKey, hashKey);
        } catch (Exception e) {
            logger.error("hashDelete: {}", e);
        }
    }
    // endregion

    // region -- 其他操作

    /**
     * 判断key是否存在
     *
     * @param key
     * @return redis报错返回null
     */
    @SuppressWarnings("unchecked")
    public Boolean hasKey(String key) {
        Boolean flag = null;
        try {
            flag = redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("hasKey: {}", key, e);
        }
        return flag;
    }

    // 读 keys
    @SuppressWarnings("unchecked")
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 获取Hash键集合
     *
     * @param key hash键
     * @return redis异常返回null，如果key不存在返回空集合
     */
    public Set<Object> hashKeys(String key) {
        Set<Object> obj = null;
        try {
            obj = hash.keys(key);
        } catch (Exception e) {
            logger.error("hashKeys: {}", key, e);
        }
        return obj;
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @return redis异常返回null
     */
    @SuppressWarnings("unchecked")
    public Boolean expire(String key, long timeout) {
        Boolean flag = null;
        try {
            flag = redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("expire: {}", key, e);
        }
        return flag;
    }

    /**
     * 获取剩余过期时间
     *
     * @param key
     * @return redis异常或没有过期时间返回-1
     */
    public long getExpire(String key) {
        long flag = -1;
        try {
            flag = redisTemplate.getExpire(key).longValue();
        } catch (Exception e) {
            logger.error("getExpire: {}", key, e);
        }
        return flag;
    }

    public <T> T execute(RedisCallback<T> callback) {
        return (T) redisTemplate.execute(callback);
    }
    // endregion

    /**
     * 加锁,true 重复 ， false 未重复
     */
    public boolean isRepeat(String key, long ttl) {
        try {
            long count = redisTemplate.opsForValue().increment(key, 1);
            if (count == 1) {
                //设置有效期
                redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
                return false;
            } else {
                long time = redisTemplate.getExpire(key);
                if (time == -1) {
                    //设置失败重新设置过期时间
                    redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
                    return false;
                }
            }
            //如果存在表示重复
            return true;
        } catch (Exception e) {
            logger.error("redis加锁异常", key, e);
            redisTemplate.delete(key);        //出现异常删除锁
            return false;
        }
    }

    /**
     * 加锁
     */
    public long increment(String key, long ttl) {
        long count = 0;
        try {
            count = redisTemplate.opsForValue().increment(key, 1);
            if (count == 1) {
                redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
            } else if (count >= Integer.MAX_VALUE - 1000) {
                redisTemplate.delete(key);
            } else {
                long time = redisTemplate.getExpire(key);
                if (time == -1) {
                    //设置失败重新设置过期时间
                    redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
                }
            }

        } catch (Exception e) {
            logger.error("{}, redis increment error", key, e);
            redisTemplate.delete(key);        //出现异常删除锁
        }

        return count;
    }

    ReentrantLock lock = new ReentrantLock(true);

    /**
     * 将redis读取和删除操作作为原子性
     *
     * @param key
     * @return
     */
    public Object getAndDelete(String key) {
        Object object = null;
        try {
            lock.lock();
            object = redisTemplate.opsForValue().get(key);
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("{} ,getAndDelete has error", key, e);
        } finally {
            lock.unlock();
        }
        return object;
    }

    /**
     * 将redis读取和删除操作作为原子性
     *
     * @param key
     * @return
     */
    public Object getAndDelete2(String key) {
        Object object = null;
        try {
            synchronized (this) {
                object = redisTemplate.opsForValue().get(key);
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            logger.error("{} ,getAndDelete2 has error", key, e);
        }
        return object;
    }

    /**
     * 获取有过期时间的自增长ID
     *
     * @param key
     * @return
     */
    public long increaseByDate(String key) {
        LocalDateTime now = LocalDateTime.now();
        //生成DDyyyyMMdd key值
        String dateKey = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 设置24小时后过期
        return increment(key + ":" + dateKey, 24 * 60 * 60);
    }

    /**
     * 按天累加
     *
     * @param key
     * @return
     */
    public void addByDate(String key, Long num) {
        LocalDateTime now = LocalDateTime.now();
        //生成DDyyyyMMdd key值
        String dateKey = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 设置24小时后过期
        key = key + ":" + dateKey;

        long ttl = 24 * 60 * 60;
        try {
            Integer count = (Integer) redisTemplate.opsForValue().get(key);
            if (Objects.isNull(count)) {
                redisTemplate.opsForValue().set(key, num, ttl, TimeUnit.SECONDS);
            } else {

                // 防止溢出
                if (count >= Integer.MAX_VALUE - 1000) {
                    redisTemplate.delete(key);
                } else {
                    // 防止redis TTL异常
                    long time = redisTemplate.getExpire(key);
                    if (time == -1) {
                        //设置失败重新设置过期时间
                        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
                    }
                }
                redisTemplate.opsForValue().set(key, count + num, ttl, TimeUnit.SECONDS);
            }

        } catch (Exception e) {
            logger.error("{}, redis increment error", key, e);
            redisTemplate.delete(key);        //出现异常删除锁
        }
    }

    public long getValueOfIncreaseByDate(String key) {
        long count = 0;
        LocalDateTime now = LocalDateTime.now();
        //生成DDyyyyMMdd key值
        String dateKey = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Integer l = (Integer) redisTemplate.opsForValue().get(key + ":" + dateKey);
        if (Objects.nonNull(l)) {
            count = l.longValue();
        }
        return count;
    }

    /**
     * @param key
     * @param value
     * @param hours
     * @param createTime 毫秒
     */
    public void putZSet(String key, String value, int hours, long createTime) {
        logger.debug("RedisDao| putZSet({},{})", key, value);
        try {
            redisTemplate.opsForZSet().add(key, value, createTime);
            redisTemplate.expire(key, hours, TimeUnit.HOURS);
        } catch (Exception e) {
            logger.error("RedisDao| putZSet()  has exception, the key is {}", key, e);
        }
    }


    public Set<String> getZSetByRange(String key, long delStartTime, long delEndTime) {
        logger.debug("RedisDao| getZSet({},{})", key);
        Set<String> valueList = new HashSet<>();
        try {
            BoundZSetOperations<String, String> opts = redisTemplate.boundZSetOps(key);
            //查询几分钟之内的
            Set<ZSetOperations.TypedTuple<String>> set = opts.rangeByScoreWithScores(delStartTime, delEndTime);
            if (CollectionUtils.isEmpty(set)) {
                logger.debug("getZset| no data find in key={}", key);
                return null;
            }
            for (ZSetOperations.TypedTuple<String> item : set) {
                valueList.add(item.getValue());
            }
        } catch (Exception e) {
            logger.error("RedisDao| getZSet()  has exception, the key is {}", key, e);
        }
        return valueList;
    }

    public Set<String> getAllZSet(String key) {
        long limitEndTime = System.currentTimeMillis();
        return getZSetByRange(key, 0, limitEndTime);
    }

    public boolean removeZSetByRange(String key, long delEndTime) {
        boolean flag = false;
        try {
            BoundZSetOperations<String, String> opts = redisTemplate.boundZSetOps(key);
            //删除过期tokenId
            opts.removeRangeByScore(0, delEndTime);
            logger.info("removeZsetByRange successful key={}, delEndTime={}", key, delEndTime);
            flag = true;
        } catch (Exception e) {
            logger.error("removeZsetByRange exception={}", e);
        }
        return flag;
    }

    public boolean removeValueFromZSet(String key, String value) {
        boolean flag = false;
        try {
            BoundZSetOperations<String, String> opts = redisTemplate.boundZSetOps(key);
            opts.remove(value);
            flag = true;
        } catch (Exception e) {
            logger.error("removeValueFromZset exception={}", e);
        }
        return flag;
    }

    public void removeAllZSet(String key) {
        removeZSetByRange(key, -1);
    }
}
