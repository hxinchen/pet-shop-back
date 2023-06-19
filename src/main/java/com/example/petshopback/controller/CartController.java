package com.example.petshopback.controller;

import com.example.petshopback.entity.Cart;
import com.example.petshopback.mapper.CartMapper;
import com.example.petshopback.service.CartService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @Autowired
    private CartMapper cartMapper;

    @GetMapping("/list")
    public List<Cart> list() {
        return cartService.list();
    }

    @PostMapping("/add")
    public Result add(Integer productId) {
        Result result = new Result();

        Cart cart = cartService.add(productId);
        result.setData(cart);
        result.success("加入购物车成功");
        return result;
    }

    @PostMapping("/sub")
    public Result sub(Integer productId) {
        Result result = new Result();

        Cart cart = cartService.sub(productId);
        result.setData(cart);
        result.success("减少购物车成功");
        return result;
    }

    @PostMapping("/deleteByIds")
    public Result deleteByIds(String ids) {
        Result result = new Result();
        result.setData(cartService.deleteByIds(ids));
        result.success("删除购物车成功");
        return result;
    }

    @GetMapping("/getAll")
    public Result getAll() {
        Result result = new Result();

        result.setData(cartService.getAll());
        result.success("查询成功");
        return result;
    }

    @PostMapping("/update")
    public Result update(Integer productId) {
        Result result = new Result();
        result.setData(cartService.check(productId));
        result.success("更新购物车成功");
        return result;

    }

    @PostMapping("/updateAll")
    public Result updateAll(String ids) {
        Result result = new Result();
        result.setData(cartService.updateAll(ids));
        result.success("更新购物车成功");
        return result;

    }
}