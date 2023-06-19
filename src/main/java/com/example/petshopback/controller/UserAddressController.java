package com.example.petshopback.controller;

import com.example.petshopback.entity.UserAddress;
import com.example.petshopback.service.UserAddressService;
import com.example.petshopback.utils.JwtUtil;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/getAddress")
    public Result getAddress() {
        Result result = new Result();
        String token= request.getHeader("token");
        int userId=Integer.parseInt(JwtUtil.validateToken(token));
        System.out.println(userId);
        result.setData(userAddressService.getAddress());
        result.success("获取地址成功");
        return result;
    }

    // 查默认地址
    @GetMapping("/getDefault")
    public Result getDefault() {
        Result result = new Result();
        UserAddress userAddress = userAddressService.getDefault();
        if (userAddress == null) {
            result.setData("无默认地址，请点击选择");
            result.fail("获取默认地址失败");
            return result;
        }
        result.setData(userAddress);
        result.success("获取默认地址成功");
        return result;
    }

    @GetMapping("/getAddressById")
    public Result getAddressById(Integer addressId) {
        Result result = new Result();
        result.setData(userAddressService.getById(addressId));
        result.success("获取地址成功");
        return result;
    }

    @PostMapping("/updateDefaultById")
    public Result updateDefaultById(Integer addressId,Boolean isDefault) {
        Result result = new Result();
        if(isDefault==null){
            result.fail("修改默认地址失败");
            return result;
        }
        if(!userAddressService.updateDefault(addressId,isDefault)){
            result.fail("修改默认地址失败,地址不存在");
            return result;
        }
        result.success("修改默认地址成功");
        return result;
    }
    //修改，添加地址
    @PostMapping("/updateById")
    public Result updateById(@RequestBody UserAddress userAddress) {
        Result result = new Result();
        String token= request.getHeader("token");
        int userId=Integer.parseInt(JwtUtil.validateToken(token));
        userAddress.setUserId(userId);
        if(!userAddressService.updateById(userAddress)){
            //如果需要设置为默认地址
            if(userAddress.getIsDefault()){
                   //置默认为非默认
                   userAddressService.setNotDefault();
            }
            userAddressService.save(userAddress);
            result.success("添加地址成功");
        }else {
            result.success("修改地址成功");
        }
        return result;
    }

    @PostMapping("/deleteById")
    public Result deleteById(Integer addressId) {
        Result result = new Result();
        if(!userAddressService.removeById(addressId)){
            result.fail("删除地址失败");
            return result;
        }
        result.success("删除地址成功");
        return result;
    }
}
