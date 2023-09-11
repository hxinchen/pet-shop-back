package com.example.petshopback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface OrderService extends IService<Order> {

    void cancelOrder();

    DelayQueue<Order> pushOrder(Order order);
    Order add(Double sumPrice, Integer isPay, Integer addressId) throws ParseException;

    Order cancel(Integer orderId, String reason);

    Page<Order> getAll(Integer pageNum, Integer pageSize);

    List<Order> getByStatus(Integer status);

    List<Order> getByUserId();

    void deleteByIds(String ids);
}
