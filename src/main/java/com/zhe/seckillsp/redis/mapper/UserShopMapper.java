package com.zhe.seckillsp.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhe.seckillsp.redis.entity.UserShop;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Mapper
public interface UserShopMapper extends BaseMapper<UserShop> {
    void insertOne(Integer userId, Integer shopId);
}
