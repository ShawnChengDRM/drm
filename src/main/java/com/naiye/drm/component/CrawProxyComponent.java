package com.naiye.drm.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.naiye.drm.model.IpProxy;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: csf
 * @Descriptions: 通过url获取ip
 * @Date: create in  9:14 2018/6/26
 */

@Component
public class CrawProxyComponent {
    @Value("${migu.proxy.url}")
    private String crawUrl;

    public List<IpProxy> craw() throws Exception {
        String result = Jsoup.connect(crawUrl).get().body().html();
        JSONObject resJson = JSONObject.parseObject(result);
        if (resJson.getBoolean("success")) {
            List<IpProxy> resultList = JSON.parseArray(resJson.getString("data").replaceAll("expire_time", "expireTime"), IpProxy.class);
            return resultList;
        } else {
            throw new Exception("获取代理IP失败, msg =" + resJson.getString("msg"));
        }
    }

}