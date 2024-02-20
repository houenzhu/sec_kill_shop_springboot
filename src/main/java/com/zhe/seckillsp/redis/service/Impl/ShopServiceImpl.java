package com.zhe.seckillsp.redis.service.Impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.seckillsp.redis.entity.Shop;
import com.zhe.seckillsp.redis.mapper.ShopMapper;
import com.zhe.seckillsp.redis.mapper.UserShopMapper;
import com.zhe.seckillsp.redis.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
@Slf4j
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private UserShopMapper userShopMapper;

    static String secKillScript = "local userid=KEYS[1];\r\n" +
            "local shopid=KEYS[2];\r\n" +
            "local shopKey='sk:'..shopid..\":sp\";\r\n" +
            "local usersKey='sk:'..shopid..\":user\";\r\n" +
            "local userExists=redis.call(\"sismember\",usersKey,userid);\r\n" +
            "if tonumber(userExists)==1 then \r\n" +
            "   return 2;\r\n" +
            "end\r\n" +
            "local num= redis.call(\"get\" ,shopKey);\r\n" +
            "if tonumber(num)<=0 then \r\n" +
            "   return 0;\r\n" +
            "else \r\n" +
            "   redis.call(\"decr\",shopKey);\r\n" +
            "   redis.call(\"sadd\",usersKey,userid);\r\n" +
            "end\r\n" +
            "return 1";

    @Override
    public Boolean SecKillShop(Integer userId, Integer shopId) {
        if (null == userId || null == shopId) {
            log.info("商品尚未开始秒杀...");
            return false;
        }
        String shopName = shopMapper.getShopName(shopId);
        String shopKey = "sk:" + shopName + ":sp";
        String userKey = "sk:" + shopName + ":user";
        Object o = redisTemplate.opsForValue().get(shopKey);
        if (null == o) {
            log.info("商品尚未开始秒杀...");
            return false;
        }
        String shopStockStr = String.valueOf(o);
        int shopStock = Integer.parseInt(shopStockStr);
        if (shopStock <= 0) {
            log.info("该商品没有库存了...");
            return false;
        }
        Boolean isExists = redisTemplate.opsForSet().isMember(userKey, userId);
        if (Boolean.TRUE.equals(isExists)) {
            log.info("该用户无法重复下单...");
            return false;
        }

        redisTemplate.opsForValue().decrement(shopKey);
        redisTemplate.opsForSet().add(userKey, userId);
        return true;
    }

    @Override
    public Long secKillShop2(Integer userId, Integer shopId) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(secKillScript, Long.class);
        String shopName = shopMapper.getShopName(shopId);
//        String shopKey = "sk:" + shopName + ":sp";
//        String userKey = "sk:" + shopName + ":user";
        Long result = redisTemplate.execute(redisScript, Arrays.asList(userId.toString(), shopName),
                userId, shopName);
        if (result == 0L) {
            return 0L;
        } else if (result == 1L) {
            return 1L;
        } else if (result == 2L) {
            return 2L;
        }else {
            return -1L;
        }
    }

    @Override
    public void descShopStock(Integer id) {
        shopMapper.descShopStock(id);
    }

    @Override
    public void insertSecUser(Integer userId, Integer shopId) {
        userShopMapper.insertOne(userId, shopId);
    }

}
