package com.naiye.drm.service;

import com.naiye.drm.model.IpProxy;

/**
 * @Author: csf
 * @Descriptions:
 * @Date: create in  16:30 2018/6/25
 */
public interface IpProxyService {

    /**
     *
     * @param used IP可使用次数
     * @param field 域
     * @return
     *
     */
    IpProxy getAvailableIpProxy(int used, String field) throws Exception;

    IpProxy getAvailableIpProxy2Step(int used, String field, Integer ipNo) throws Exception;

    int restoreUsed(IpProxy ipProxy);

}

