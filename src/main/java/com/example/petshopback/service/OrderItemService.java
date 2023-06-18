package com.example.petshopback.service;

import com.example.petshopback.entity.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单详情表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface OrderItemService extends IService<OrderItem> {

    List<OrderItem> getByOrderId(Integer orderId);

    List<OrderItem> add(Integer orderId, Integer status, String ids, String nums, Integer isPet);
}
