package com.example.petshopback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 	商店表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface ShopService extends IService<Shop> {

    boolean addShop(Shop shop);

    Page<Shop> getAllShop(Integer pageNum, Integer pageSize);

    List<Shop> getShop();

    Double getSalesByTime(Integer shopId,String startTime, String endTime, Integer isPet);

    Integer getSalesCountByTime(Integer shopId, String startTime, String endTime, Integer isPet);

    Integer getAccessCount(Integer shopId,Integer isPet);
}
