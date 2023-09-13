package com.example.petshopback.service;

import com.example.petshopback.entity.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

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

    List<OrderItem> getDetail(String orderIds, Integer status);

    List<OrderItem> getByOrderId(Integer orderId);
    List<OrderItem> getByOrderIdStatus(Integer orderId, Integer status);
    OrderItem update(Integer orderId, Integer proId, Integer status);
    List<OrderItem> add(Integer orderId, Integer status, String ids, String nums, Integer isPet, String shopIds);

    void applyRefund(Integer orderId, Integer status, Integer proId, Boolean isPet, String reason);
}
