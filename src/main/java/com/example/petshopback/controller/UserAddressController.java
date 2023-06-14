package com.example.petshopback.controller;

import com.example.petshopback.entity.UserAddress;
import com.example.petshopback.service.UserAddressService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户地址表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;
    @PostMapping("/add")
    public Result add(@RequestBody UserAddress userAddress) {

        Result result = new Result();
        try {
            this.userAddressService.save(userAddress);
            result.success("新增地址成功");
        } catch (Exception e) {
            result.fail("新增地址失败：" + e.getMessage());
        }
        return result;
    }
}
