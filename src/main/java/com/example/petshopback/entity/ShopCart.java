package com.example.petshopback.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * 购物车自定义类
 *
 */

@Getter
@Setter
public class ShopCart {


    // 商店名字
    private String shopName;

    // 商店商品
    private List<CartVO> products;

    // 购物车商品数量
    private Integer productNum;
}
