package com.naiye.drm.service.impl;

import com.naiye.drm.common.RedisConst;
import com.naiye.drm.component.CrawProxyComponent;
import com.naiye.drm.component.redis.RedisComponent;
import com.naiye.drm.mapper.IpProxyMapper;
import com.naiye.drm.model.IpProxy;
import com.naiye.drm.service.IpProxyService;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: csf
 * @Descriptions:
 * @Date: create in  16:30 2018/6/25
 */
@Service
public class IpProxyServiceImpl implements IpProxyService {
    private static final Logger logger = LoggerFactory.getLogger(IpProxyServiceImpl.class);
    @Resource
    private IpProxyMapper ipProxyMapper;
    @Resource
    private CrawProxyComponent crawIpProxy;
    @Resource
    private RedisComponent redisComponent;

    /**
     * 获取可用IP代理
     *
     * @param used IP可使用次数
     * @return
     */
    @Override
    public IpProxy getAvailableIpProxy(int used, String field) throws Exception {
        IpProxy ipProxy = getIpProxy(used, field);
        //如果IP池为空了，去获取代理IP
        if (ipProxy == null) {
            try {
                //保证1秒钟内不会发出多条请求
                if (redisComponent.acquire(RedisConst.CRAW_IP_LOCK.toString(), 1)) {
                    ipProxy = getIpProxy(used, field);
                    if (ipProxy != null) {
                        return ipProxy;
                    }
                    List<IpProxy> ipProxies = crawIpProxy.craw();
                    ipProxyMapper.insertList(ipProxies);
                    return getAvailableIpProxy(used, field);
                } else {
                    Thread.sleep(1000L);
                    return getIpProxy(used, field);
                }
            } catch (Exception e) {
                logger.info("获取代理失败", e);
            }
        }
        return ipProxy;
    }

    /**
     * 获取可用IP代理
     *
     * @param used
     * @param field
     * @return
     * @throws Exception
     */
    @Override
    public IpProxy getAvailableIpProxy2Step(int used, String field, Integer ipNo) throws Exception {
        String fieldNo = field + ":" + ipNo;

        //这里把count原来的Integer类型转换成了Long类型为了和下面ip使用次数num对应
        Long count = redisComponent.hget(RedisConst.PROXY_NO + fieldNo, "count", Long.class);

        //以前代码
/*        if (count != null && count < 5) {
            IpProxy ipProxy = redisComponent.hget(RedisConst.PROXY_NO + fieldNo, "proxy", IpProxy.class);
            if (ipProxy != null) {
                //返回可用代理
                return ipProxy;
            }
        }*/


        //更新代码
        if (count != null && count < used) {
            IpProxy ipProxy = redisComponent.hget(RedisConst.PROXY_NO + fieldNo, "proxy", IpProxy.class);
            if (ipProxy != null) {

                //ip使用次数加1
                //hincrby指令增加的值只能是long类型
                //num为当前ip使用次数
                Long num = redisComponent.hincrby(RedisConst.PROXY_NO + fieldNo, "count", 1L);
                logger.info("ip使用次数:" + num);

                //更新数据库
                Long id = ipProxy.getId();
                ipProxyMapper.UpdateIpNum(num, id);

                //返回可用代理
                return ipProxy;
            }
        }


        //随机取的IP已超过使用次数，从数据库中取IP并插入缓存
        Long maxIndex = redisComponent.get(RedisConst.APP_MAX_PROXY_ID + field, Long.class);
        logger.info("field = {}, maxId={}", field, maxIndex);
        //找不到对应计费点的IP使用信息,则从0开始
        if (maxIndex == null) {
            maxIndex = 0L;
        }
        IpProxy ipProxy = ipProxyMapper.getIpProxyByMaxId(maxIndex, new DateTime().minusMinutes(1).toDate());
        if (ipProxy != null) {
            //覆盖代理操作不能同时进行
            if (!redisComponent.acquire("proxy_set_lock:" + field, 30L)) {
                Thread.sleep(1001L);
                return getAvailableIpProxy2Step(used, field, ipNo);
            }
            //如果获取到执行覆盖操作的锁
            //新代理覆盖旧代理
            redisComponent.hset(RedisConst.PROXY_NO + fieldNo, "proxy", ipProxy);
            //这里把原来初始值0改成了1，代表使用了一次
            redisComponent.hset(RedisConst.PROXY_NO + fieldNo, "count", 1);
            //设置该ip过期时间
            redisComponent.expire(RedisConst.PROXY_NO + fieldNo, (ipProxy.getExpireTime().getTime() - System.currentTimeMillis())/1000 - 30);
            //修改上次使用的IP值
            redisComponent.set(RedisConst.APP_MAX_PROXY_ID + field, ipProxy.getId());
            //释放锁
            redisComponent.del("proxy_set_lock:" + field);

            //这里打印一下ip使用次数
            logger.info("ip使用次数:1");

            return ipProxy;
        }
        //如果数据库IP池空了，去获取代理IP
        //保证1秒钟内不会发出多条请求
        if (redisComponent.acquire(RedisConst.CRAW_IP_LOCK.toString(), 1)) {
            List<IpProxy> ipProxies = crawIpProxy.craw();
            logger.info("一次获取得ip:" + ipProxies);
            ipProxyMapper.insertList(ipProxies);
        } else {
            Thread.sleep(1001L);
        }
        ipNo = RandomUtils.nextInt(0, 10);
        return getAvailableIpProxy2Step(used, field, ipNo);
    }

    /**
     * 当本次支付失败后，返回可使用次数
     *
     * @param ipProxy
     * @return
     */
    @Override
    public int restoreUsed(IpProxy ipProxy) {
        Long id = ipProxy.getId();
        return ipProxyMapper.restoreUsed(id);
    }

    /**
     * 获取可用IP，每个业务独立
     *
     * @param used ip可使用次数
     * @return
     * @Param field 业务+pid
     */

    private IpProxy getIpProxy(int used, String field) throws Exception {
        //获取离过期时间大于30秒的IP 防止在使用过程中IP过期
        IpProxy ipProxy = redisComponent.rpop(RedisConst.APP_PROXY_KEY + field, IpProxy.class);
        if (ipProxy != null) {
            if ((new DateTime(ipProxy.getExpireTime()).toDate().getTime() - System.currentTimeMillis()) < 30 * 1000) {
                logger.info("过期的代理, expireTime = {}, currentTime = {}", new DateTime(ipProxy.getExpireTime()).toDate().getTime(), System.currentTimeMillis());
                return getIpProxy(used, field);
            }
            return ipProxy;
        }
        if (redisComponent.acquire(RedisConst.DB_PROXY_LOCK.toString(), 1)) {
            Long maxIndex = redisComponent.get(RedisConst.APP_MAX_PROXY_ID + field, Long.class);
            logger.info("maxId={}", maxIndex);
            //找不到对应计费点的IP使用信息,则重新取IP
            if (maxIndex == null) {
                maxIndex = 0L;
            }
            Date date = new DateTime().plusMinutes(1).toDate();
            List<IpProxy> ipProxyList = ipProxyMapper.getAvailableProxyList(date, used, maxIndex);
            if (ipProxyList == null || ipProxyList.size() == 0) {
                return null;
            }
            insertIpProxyToQueue(ipProxyList, field);
        } else {
            Thread.sleep(1000L);
        }
        return redisComponent.rpop(RedisConst.APP_PROXY_KEY + "1_" + field, IpProxy.class);
    }

    private void insertIpProxyToQueue(List<IpProxy> ipProxyList, String field) {
        Long startTime = System.currentTimeMillis();
        Long maxIndex = ipProxyList.get(ipProxyList.size() - 1).getId();
        redisComponent.set(RedisConst.APP_MAX_PROXY_ID + field, maxIndex);
        for (IpProxy ipProxy : ipProxyList) {
            for (int j = 0; j < 5; j++) {
                redisComponent.lpush(RedisConst.APP_PROXY_KEY + field, ipProxy);
            }
        }
        logger.info("ipproxy插入redis,耗时={}", System.currentTimeMillis() - startTime);
    }

/*    private IpProxy getIpProxy(int used) {
        //获取离过期时间大于1分钟的IP 防止在使用过程中IP过期
        Date date = new DateTime().plusMinutes(1).toDate();
        IpProxy ipProxy = ipProxyMapper.getAvailableProxy(date, used);
        if (ipProxy == null) {
            return null;
        }
        int lastNum = ipProxy.getNum();
        int updateNum = ipProxyMapper.addProxyNum(ipProxy.getId(), lastNum);
        if (updateNum == 1) {
            return ipProxy;
        }
        return this.getIpProxy(used);
    }*/


    public static void main(String[] args) {
        System.out.println(new DateTime().plusMinutes(2).toDate().getTime() - System.currentTimeMillis());
    }
}
