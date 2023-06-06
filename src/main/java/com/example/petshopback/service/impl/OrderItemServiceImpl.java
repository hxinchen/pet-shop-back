package com.example.petshopback.service.impl;

import com.example.petshopback.entity.OrderItem;
import com.example.petshopback.mapper.OrderItemMapper;
import com.example.petshopback.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:52:37
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
