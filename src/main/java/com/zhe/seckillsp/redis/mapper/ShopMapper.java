package com.zhe.seckillsp.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhe.seckillsp.redis.entity.Shop;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Mapper
public interface ShopMapper extends BaseMapper<Shop> {
    String getShopName(Integer id);
    void descShopStock(Integer id);
}
