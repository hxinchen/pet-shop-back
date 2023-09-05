package com.example.petshopback.controller;

import com.example.petshopback.entity.Order;
import com.example.petshopback.entity.OrderItem;
import com.example.petshopback.service.OrderItemService;
import com.example.petshopback.service.OrderService;
import com.example.petshopback.service.UserAddressService;
import com.example.petshopback.service.UserService;
import com.example.petshopback.utils.JwtUtil;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    private HttpServletRequest request;
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
        String token = request.getHeader("Authorization");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
        for (OrderItem OrderItem : list) {
            Order order = orderService.getById(OrderItem.getOrderId());
//            if (order.getStatus() == -1) //追评
//                OrderItem.put("commentId", commentService.getByBookId(OrderItem.getBookId()));
            OrderItem.put("createTime", order.getCreateTime());
            OrderItem.put("no", order.getNo());
            if (order.getCancelTime() != null)
                OrderItem.put("cancelTime", order.getCancelTime().getTime());
            else
                OrderItem.put("cancelTime", 0);
            OrderItem.put("cancelReason", order.getCancelReason());
//            OrderItem.put("status", order.getStatus());
            OrderItem.put("address", userAddressService.getDefault());
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
