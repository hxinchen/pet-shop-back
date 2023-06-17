package com.example.petshopback.controller;

import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.service.ShopService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 	商店表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    // 新增商店
    @PostMapping("/add")
    public Result add(@RequestBody Shop shop) {
        Result result = new Result();
        if (shopService.addShop(shop)) {
            result.setData(shop);
            result.success("新增商店成功");
        } else {
            result.fail("新增商店失败");
        }
        return result;
    }

    // 查询全部商店
    @PostMapping("/getAllShop")
    public Result getAllShop() {
        Result result = new Result();
        List<Shop> isExit = shopService.getAllShop();
//        System.out.println(isExit);
        if (isExit != null) {
            result.success("查询成功");
            result.setData(isExit);
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }
}
