package com.example.petshopback.service;

import com.example.petshopback.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petshopback.entity.ShopCart;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface CartService extends IService<Cart> {
    List<ShopCart> getAll();

    Cart add(Integer productId);

    boolean check(Integer productId);

    Cart sub(Integer productId);

    boolean deleteByIds(String ids);

    Boolean updateAll(String ids);

    List<Cart> getCartByUserId();
}
