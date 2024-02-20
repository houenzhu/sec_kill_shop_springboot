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
public class Shop implements Serializable {
    private Integer id;
    private String name;
    private Integer stock;
}
