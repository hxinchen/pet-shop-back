package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.service.ShopService;
import com.example.petshopback.service.UserService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.example.petshopback.utils.Transform;

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
    private UserService userService;
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

    // 编辑商店
    @PostMapping("/modify")
    public Result modify(@RequestBody Shop shop) {
        Result result = new Result();
        if (shopService.updateById(shop)) {
            result.setData(shop);
            result.success("编辑商店成功");
        } else {
            result.fail("编辑商店失败");
        }
        return result;
    }

    @GetMapping("/getList")
    public Result getList() {
        Result result = new Result();
        List<Shop> list = shopService.list();
        System.out.println(list);
        if (list != null) {
            result.success("查询成功");
            result.setData(list);
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }

    // 查询全部商店
    @GetMapping("/getAll")
    public Result getAll(Integer pageNum, Integer pageSize) {
        Result result = new Result();
        Page<Shop> page = shopService.getAllShop(pageNum, pageSize);
        if (page.getRecords() != null) {
            result.success("查询成功");
            for (int i=0; i < page.getRecords().size(); i++) {
                page.getRecords().get(i).put("userName",userService.getById(page.getRecords().get(i).getUserId()).getUsername());
            }
            result.setData(page);
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }

    // 根据id查询商店
    @GetMapping("/getByUserId")
    public Result getByUserId() {
        Result result = new Result();
        List<Shop> shop = shopService.getShop();
        Transform transform = new Transform();
        // 输出
        System.out.println(shop);
        if (shop != null) {
            result.success("查询成功");
            result.setData(transform.listToPage(shop, 1, 5));
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }
}
