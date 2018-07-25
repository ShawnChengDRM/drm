package com.naiye.drm.mapper;

import com.naiye.drm.common.MyBaseMapper;
import com.naiye.drm.model.IpProxy;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface IpProxyMapper extends MyBaseMapper<IpProxy> {


    @Select("select top 5 id, ip, port, expire_time, num from outall_ip_proxy where id > #{id} and expire_time > #{expireTime} and num < #{num} order by id;")
//    @Select("select id, ip, port, expire_time, num from outall_ip_proxy where id > #{id} and expire_time > #{expireTime} and num < #{num} order by id limit 5;")
    List<IpProxy> getAvailableProxyList(@Param("expireTime") Date expireTimel, @Param("num") Integer num, @Param("id") Long id);

    @Insert("<script>" +
            "insert outall_ip_proxy(ip, port, city, expire_time) values " +
            " <foreach collection=\"list\" item=\"item\" separator=\",\"> " +
            "(#{item.ip}, #{item.port}, #{item.city}, #{item.expireTime})" +
            "</foreach>" +
            "</script>")
    int insertList(@Param("list") List<IpProxy> list);

    @Update("update outall_ip_proxy set num = num - 1 where id = #{id}")
    int restoreUsed(Long id);

    @Update("update outall_ip_proxy set num = #{num} where id = #{id}")
    int UpdateIpNum(@Param("num") Long num, @Param("id") Long id);

    @Select("select id, ip, port, expire_time, num from outall_ip_proxy where id > #{maxid} and expire_time > #{expireTime} order by id limit 1")
    IpProxy getIpProxyByMaxId(@Param("maxid") Long maxid, @Param("expireTime") Date expireTimel);
}
