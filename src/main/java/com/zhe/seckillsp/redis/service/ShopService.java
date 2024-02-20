package com.zhe.seckillsp.redis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.seckillsp.redis.entity.Shop;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface ShopService extends IService<Shop> {

    Boolean SecKillShop(Integer userId, Integer shopId);
    void descShopStock(Integer id);
    void insertSecUser(Integer userId, Integer shopId);
    Long secKillShop2(Integer userId, Integer shopId);
}
