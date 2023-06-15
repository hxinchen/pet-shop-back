package com.example.petshopback.controller;

import com.example.petshopback.entity.Cart;
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
    @GetMapping("/list")
    public List<Cart> list(){
        return cartService.list();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Cart card){
        Result result = new Result();
        try {
            Cart isExit = cartService.getById(card.getId());
            if (isExit != null) {
                result.fail("已加入购物车");
            } else {
                result.setData(cartService.save(card));
                result.success("加入购物车成功");
            }
        } catch (Exception e) {
            result.fail("加入购物车失败：" + e.getMessage());
        }
        return result;
    }
    @PostMapping("/del")
    public boolean del(@RequestParam String id){
        return cartService.removeById(id);
    }

    @GetMapping("/get")
    public Result getA(Integer userId){
        Result result=new Result();
        result.setData(cartService.getA(userId));
        return result;
    }

    @PostMapping("/mod")
    public boolean mod(@RequestBody Cart card){
        return cartService.updateById(card);
    }
}