package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.service.ShopService;
import com.example.petshopback.service.UserService;
import com.example.petshopback.utils.JwtUtil;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.example.petshopback.utils.Transform;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private HttpServletRequest request;
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
        String token = request.getHeader("token");
        Integer userId = Integer.parseInt(JwtUtil.validateToken(token));
        Page<Shop> page = shopService.getAllShop(pageNum, pageSize);
        List<Shop> list = new ArrayList<>();
        if (page.getRecords() != null) {
            result.success("查询成功");
            for (int i=0; i < page.getRecords().size(); i++) {
                page.getRecords().get(i).put("userName",userService.getById(page.getRecords().get(i).getUserId()).getUsername());
                if(Objects.equals(page.getRecords().get(i).getUserId(), userId)) {
                    //排除其他用户的商店
                    list.add(page.getRecords().get(i));
                }
            }
            //把list的结果放到page里面
            page.setRecords(list);
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

    // 根据id查询商店
    @GetMapping("/getById")
    public Result getById(int id) {
        Result result = new Result();
        Shop shop = shopService.getById(id);
        if (shop != null) {
            result.success("查询成功");
            result.setData(shop);
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }
}
