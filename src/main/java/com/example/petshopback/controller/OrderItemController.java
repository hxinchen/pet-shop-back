package com.example.petshopback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.Order;
import com.example.petshopback.entity.OrderItem;
import com.example.petshopback.service.*;
import com.example.petshopback.utils.DateTool;
import com.example.petshopback.utils.JwtUtil;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单详情表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private PetService petService;

    @PostMapping("/add")
    public Result add(Integer orderId, Integer status, String ids, String nums, Integer isPet, String shopIds){
        Result result = new Result();

        List<OrderItem> list = orderItemService.add(orderId, status, ids, nums, isPet, shopIds);

        System.out.println(list);
        result.setData(list);
        result.success("添加成功");
        return result;
    }

    @GetMapping( "/getAll")
    public Result getAll() {
        Result result = new Result();
        //通过id查找
        List<OrderItem> list = orderItemService.list();
        for (OrderItem orderItem : list) {
            Order order = orderService.getById(orderItem.getOrderId());
//            orderItem.put("createTime", order.getCreateTime());
//            orderItem.put("no", order.getNo());
        }
        if (!list.isEmpty()) {
            result.setData(list);
            result.success("查询成功");
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }

    //更新订单详情状态
    @PostMapping("/update")
    public Result update(Integer orderId, Integer proId, Integer status) {
        Result result = new Result();
        OrderItem orderItem = new OrderItem();


        if (status == 1) { //继续付款
            // 更新订单表状态
            orderService.updateOrder(orderId, status);
        }
        else {
            orderItem = orderItemService.update(orderId, proId, status);
            // 判断若该订单id全部详情都更新，则更新订单状态
            Boolean flag = orderItemService.checkStatus(orderId, status+1);
            if (flag) {
                // 更新订单表状态
                orderService.updateOrder(orderId, status);
            }
        }
        result.success("更新成功");
        result.setData(orderItem);
        return result;
    }

    // 申请退款
    @PostMapping("/applyRefund")
    public Result refund(Integer orderId, Integer proId, Boolean isPet, String reason) {
        Result result = new Result();
        orderItemService.applyRefund(orderId, proId, isPet, reason);

        // 更新订单表状态
        Order order = orderService.getById(orderId);
        order.setRefundTime(DateTool.getCurrTime());
        orderService.updateById(order);

        result.success("申请退款成功");
        return result;
    }

    // 审核退款
    @PostMapping("/checkRefund")
    public Result checkRefund(Integer orderId, Integer refundStatus, Integer proId, Integer isPet) {

        Result result = new Result();
        OrderItem orderItem = orderItemService.checkRefund(orderId, refundStatus, proId, isPet);
        // 判断审核是否通过
        if (refundStatus == 1) { // 1--通过
            // 判断若该订单id全部详情都更新，则更新订单状态
            Boolean flag = orderItemService.checkStatus(orderId, 7);
            if (flag) {
                // 更新订单表状态
                orderService.updateOrder(orderId, 7);
            }
        }
        result.success("审核退款");
        result.setData(orderItem);
        return result;
    }

    @GetMapping( "/getByOrderId")
    public Result getByOrderId(Integer orderId) {
        Result result = new Result();
        //通过id查找
        List<OrderItem> list = orderItemService.getByOrderId(orderId);
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

    //查订单详情
    @GetMapping( "/getDetail")
    public Result getDetail(String orderIds, Integer status) {
        Result result = new Result();
        //通过id查找
        List<OrderItem> list = orderItemService.getDetail(orderIds, status);
        System.out.println(list);
        for (OrderItem orderItem : list) {
            Order order = orderService.getById(orderItem.getOrderId());
//            if (order.getStatus() == -1) //追评
//                OrderItem.put("commentId", commentService.getByBookId(OrderItem.getBookId()));
            orderItem.put("createTime", order.getCreateTime());
            orderItem.put("no", order.getNo());
            orderItem.put("totalPrice", order.getSumPrice());
            if (order.getCancelTime() != null)
                orderItem.put("cancelTime", order.getCancelTime().getTime());
            else
                orderItem.put("cancelTime", 0);
            orderItem.put("cancelReason", order.getCancelReason());
//            OrderItem.put("status", order.getStatus());
            orderItem.put("address", userAddressService.getDefault());
        }

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
