package com.example.petshopback.service.impl;

import com.example.petshopback.entity.Cart;
import com.example.petshopback.mapper.CartMapper;
import com.example.petshopback.mapper.FavorMapper;
import com.example.petshopback.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Override
    public Result getA(Integer userId){
        Result result=new Result();
        result.setData(cartMapper.getA(userId));
        return result;
    }
}
