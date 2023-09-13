package com.example.petshopback.service;

import com.example.petshopback.entity.Review;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评价表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface ReviewService extends IService<Review> {

    void add(Review review);

    void delete(int id);

    void like(int id);

    List<Review> getByShopId(int shopId,int productId);
}
