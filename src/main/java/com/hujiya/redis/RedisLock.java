//package com.hujiya.redis;
//
//import com.ivay.ivay_common.constants.RedisConstant;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.RedisStringCommands;
//import org.springframework.data.redis.connection.ReturnType;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.types.Expiration;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.nio.charset.Charset;
//import java.time.LocalDate;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author 陌上丶天琊
// * 描述： redis分布式锁工具类
// */
//@Component
//@Slf4j
//public class RedisLock {
//    private static final String UNLOCK_LUA;
//    // 默认超时时间 60s
//    private static long EXPIRE_TIME = 60;
//    // baokim还款回调接口限制 90s
//    private static long COLLECTION_EXPIRE_TIME = 90;
//
//    /**
//     * 释放锁脚本，原子操作
//     */
//    static {
//        StringBuilder sb = new StringBuilder();
//        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
//        sb.append("then ");
//        sb.append("    return redis.call(\"del\",KEYS[1]) ");
//        sb.append("else ");
//        sb.append("    return 0 ");
//        sb.append("end ");
//        UNLOCK_LUA = sb.toString();
//    }
//
//    @Resource
//    private RedisTemplate redisTemplate;
//
//    /**
//     * 获取分布式锁，原子操作
//     *
//     * @param lockKey
//     * @param requestId 唯一ID, 可以使用UUID.randomUUID().toString();
//     * @param expire
//     * @param timeUnit
//     * @return
//     */
//    public boolean tryLock(String lockKey, String requestId, long expire, TimeUnit timeUnit) {
//        try {
//            RedisCallback<Boolean> callback = connection -> connection.set(
//                    lockKey.getBytes(Charset.forName("UTF-8")),
//                    requestId.getBytes(Charset.forName("UTF-8")),
//                    Expiration.seconds(timeUnit.toSeconds(expire)),
//                    RedisStringCommands.SetOption.SET_IF_ABSENT
//            );
//            return (Boolean) redisTemplate.execute(callback);
//        } catch (Exception e) {
//            log.error("redis lock error.", e);
//        }
//        return false;
//    }
//
//    /**
//     * 释放锁
//     *
//     * @param lockKey
//     * @param requestId 唯一ID
//     * @return
//     */
//    public boolean releaseLock(String lockKey, String requestId) {
//        RedisCallback<Boolean> callback = connection -> connection.eval(
//                UNLOCK_LUA.getBytes(),
//                ReturnType.BOOLEAN,
//                1,
//                lockKey.getBytes(Charset.forName("UTF-8")),
//                requestId.getBytes(Charset.forName("UTF-8"))
//        );
//        return (Boolean) redisTemplate.execute(callback);
//    }
//
//    /**
//     * 获取Redis锁的value值
//     *
//     * @param lockKey
//     * @return
//     */
//    public String get(String lockKey) {
//        try {
//            RedisCallback<String> callback =
//                    connection -> new String(connection.get(lockKey.getBytes()), Charset.forName("UTF-8"));
//            return (String) redisTemplate.execute(callback);
//        } catch (Exception e) {
//            log.error("get redis occurred an exception", e);
//        }
//        return null;
//    }
//
//    // region -- 借款锁
//    public boolean tryBorrowLock(String userGid) {
//        String lockKey = RedisConstant.BORROW_MONEY_PREFIX + userGid;
//        return tryLock(lockKey, userGid, EXPIRE_TIME, TimeUnit.SECONDS);
//    }
//
//    public boolean releaseBorrowLock(String userGid) {
//        String lockKey = RedisConstant.BORROW_MONEY_PREFIX + userGid;
//        return releaseLock(lockKey, userGid);
//    }
//    // endregion
//
//    //region --老户拉新提现锁
//    public boolean tryPullNewCashOutLock(String userGid) {
//        String lockKey = RedisConstant.PULL_NEW_CASH_OUT_PREFIX + userGid;
//        return tryLock(lockKey, userGid, EXPIRE_TIME, TimeUnit.SECONDS);
//    }
//
//    public boolean releasePullNewCashOutLock(String userGid) {
//        String lockKey = RedisConstant.PULL_NEW_CASH_OUT_PREFIX + userGid;
//        return releaseLock(lockKey, userGid);
//    }
//    //endregion
//
//    //region --授信资料锁
//    public boolean tryUpdateExtInfoLock(String userGid) {
//        String lockKey = RedisConstant.UPDATE_EXT_INFO_PREFIX + userGid;
//        return tryLock(lockKey, userGid, 5, TimeUnit.SECONDS);
//    }
//
//    public boolean releaseUpdateExtInfoLock(String userGid) {
//        String lockKey = RedisConstant.UPDATE_EXT_INFO_PREFIX + userGid;
//        return releaseLock(lockKey, userGid);
//    }
//    //endregion
//
//    // region --获取猫池锁
//    public boolean tryCardPoolLock() {
//        String lockKey = RedisConstant.CARD_POOL_PREFIX;
//        return tryLock(lockKey, "something", EXPIRE_TIME, TimeUnit.SECONDS);
//    }
//
//    public boolean releaseCardPoolLock() {
//        String lockKey = RedisConstant.CARD_POOL_PREFIX;
//        return releaseLock(lockKey, "something");
//    }
//    // endregion
//
//    // region -- 提现锁
//    public boolean tryWithdrawLock(int wallet) {
//        String lockKey = RedisConstant.WITHDRAW_PREFIX + wallet;
//        return tryLock(lockKey, "" + wallet, EXPIRE_TIME, TimeUnit.SECONDS);
//    }
//
//    public boolean releaseWithdrawLock(int wallet) {
//        String lockKey = RedisConstant.WITHDRAW_PREFIX + wallet;
//        return releaseLock(lockKey, "" + wallet);
//    }
//    // endregion
//
//    // region -- 计算逾期费用
//    public boolean tryOverdueFeeLock(LocalDate date) {
//        String dateStr = DateUtils.format_YYYY_MM_DD(date);
//        String lockKey = RedisConstant.TIMER_OVERDUE_FEE_PREFIX + dateStr;
//        return tryLock(lockKey, dateStr, EXPIRE_TIME, TimeUnit.SECONDS);
//    }
//
//
//    // region -- 风控信息
//    public boolean tryAppNumLock(LocalDate updateDate, String userGid) {
//        String requestId = updateDate.toString() + ":" + userGid;
//        String lockKey = RedisConstant.RISK_APPNUM_PREFIX + requestId;
//        return tryLock(lockKey, requestId, EXPIRE_TIME, TimeUnit.SECONDS);
//    }
//
//    public void releaseAppNumLock(LocalDate updateDate, String userGid) {
//        String requestId = updateDate.toString() + ":" + userGid;
//        String lockKey = RedisConstant.RISK_APPNUM_PREFIX + requestId;
//        releaseLock(lockKey, requestId);
//    }
//
//    public boolean tryUserContactLock(LocalDate updateDate, String userGid) {
//        String requestId = updateDate.toString() + ":" + userGid;
//        String lockKey = RedisConstant.RISK_CONTACTS_PREFIX + requestId;
//        return tryLock(lockKey, requestId, EXPIRE_TIME, TimeUnit.SECONDS);
//    }
//
//    public void releaseUserContactLock(LocalDate updateDate, String userGid) {
//        String requestId = updateDate.toString() + ":" + userGid;
//        String lockKey = RedisConstant.RISK_CONTACTS_PREFIX + requestId;
//        releaseLock(lockKey, requestId);
//    }
//
//    // region -- 风控跑批分群
//    public boolean tryRiskSegmentLock(String date) {
//        String lockKey = RedisConstant.TIMER_RISK_SEGMENT_PREFIX + date;
//        return tryLock(lockKey, date, EXPIRE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 风控上报
//     * @param dataId
//     * @param step
//     * @param userGid
//     * @param ttl
//     * @return
//     */
//    public boolean tryRiskDataUploadLock(String dataId, String step, String userGid, long ttl) {
//        String lockKey = RedisConstant.RISK_UPLOAD_DATA_LOCK_PREFIX + dataId + ":" + step + ":" + userGid;
//        String requestId = System.currentTimeMillis() + lockKey;
//        return tryLock(lockKey, requestId, ttl, TimeUnit.SECONDS);
//
//    }
//}
