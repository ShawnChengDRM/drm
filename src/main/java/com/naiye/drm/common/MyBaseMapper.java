package com.naiye.drm.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MyBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
