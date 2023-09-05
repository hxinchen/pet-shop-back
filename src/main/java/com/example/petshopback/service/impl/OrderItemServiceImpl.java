package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.OrderItem;
import com.example.petshopback.mapper.OrderItemMapper;
import com.example.petshopback.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    @Autowired
    private HttpServletRequest request;

    @Override
    public List<OrderItem> getDetail(String orderIds, Integer status) {
        List<String> list = new ArrayList<>();
        String[] array = orderIds.split(",");
        for (String i:array) {
            list.add(i);
        }
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        if (status == 0)
            queryWrapper.in("order_id", list);
        else
            queryWrapper.in("order_id", list).eq("status", status);
        return this.list(queryWrapper);
    }

    @Override
    public List<OrderItem> getByOrderId(Integer orderId) {
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);

        return this.list(queryWrapper);
    }

    @Override
    public List<OrderItem> add(Integer orderId, Integer status, String ids, String nums, Integer isPet, String shopIds) {

//      String token = request.getHeader("Authorization");
//      String userId = JwtUtil.validateToken(token);
        List<OrderItem> orderItems = new ArrayList<>();

        String[] arrayId = ids.split(",");
        String[] arrayNum = nums.split(",");
        String[] arrayShopId = shopIds.split(",");

        for (int i = 0; i < arrayId.length; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setStatus(status);
            orderItem.setShopId(Integer.valueOf(arrayShopId[i]));
            orderItem.setProductId(Integer.valueOf(arrayId[i]));
            orderItem.setCount(Integer.valueOf(arrayNum[i]));
            // 判断是否是宠物
            if (isPet == 1)
                orderItem.setIsPet(true);
            else
                orderItem.setIsPet(false);
            this.save(orderItem);
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
