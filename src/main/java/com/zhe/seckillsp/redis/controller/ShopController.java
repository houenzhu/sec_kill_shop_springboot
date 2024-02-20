package com.zhe.seckillsp.redis.controller;

import com.zhe.seckillsp.redis.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping("/secKill/{shopId}")
    public String secKill(@PathVariable("shopId") Integer shopId) {
        Random random = new Random();
        Integer userId = random.nextInt(10000);
        Long result = shopService.secKillShop2(userId, shopId);
        if (result == 1L) {
            shopService.descShopStock(shopId);
            shopService.insertSecUser(userId, shopId);
            return "秒杀成功!";
        } else if (result == 2L) {
            return "你已经参与秒杀过了...";
        } else if (result == 0L) {
            return "秒杀失败, 没有库存...";
        } else {
            return "秒杀失败...";
        }
    }
}
