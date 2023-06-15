package com.example.petshopback.service;

import com.example.petshopback.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品（商品）类别 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    List<ProductCategory> getAllCate();

    ProductCategory getByName(String name);

    boolean addProductCategory(ProductCategory productCategory);

    boolean modifyProductCategory(ProductCategory productCategory);

    boolean updateUsefulById(Integer id);
}
