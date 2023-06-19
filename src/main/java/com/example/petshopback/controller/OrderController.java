package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Order;
import com.example.petshopback.service.OrderService;
import com.example.petshopback.service.UserAddressService;
import com.example.petshopback.service.UserService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAddressService userAddressService;

    // 新增
    @PostMapping("/add")
    public Result add(Double sumPrice, Integer isPay, Integer addressId) throws ParseException {
        // isPay: 0 未支付 1 已支付
        System.out.println(sumPrice);
        Result result = new Result();
        result.setData(orderService.add(sumPrice, isPay, addressId));
        result.success("订单生成成功");

        return result;
    }

    // 查询全部订单
    @GetMapping( "/getAll")
    public Result getAll(Integer pageNum, Integer pageSize) {
        Result result = new Result();
        //通过id查找
        Page<Order> orderPage = orderService.getAll(pageNum, pageSize);
        for (Order order:orderPage.getRecords()) {
            order.put("username", userService.getById(order.getUserId()).getUsername());
//            order.put("address", userAddressService.getById(order.getUserId()).getAddress());
        }
        result.setData(orderPage);
        result.success("查询成功");
        return result;
    }

    //根据status查订单
    @GetMapping( "/getByStatus")
    public Result getByStatus(Integer status) {
        Result result = new Result();
        //通过id查找
        List<Order> list = orderService.getByStatus(status);
//        System.out.println(list);
        if (!list.isEmpty()) {
            result.setData(list);
            result.success("查询成功");
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }
}
