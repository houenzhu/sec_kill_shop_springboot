package com.zhe.seckillsp.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShop implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer shopId;
}
