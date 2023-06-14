package com.example.petshopback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 产品表（商品，周边） 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface ProductService extends IService<Product> {

    boolean addProduct(Product product);

    Page<Product> getByCategory(Integer pageNum, Integer pageSize, Integer category);

    boolean deleteByIds(String ids);
}
