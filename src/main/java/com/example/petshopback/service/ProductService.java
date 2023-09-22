package com.example.petshopback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品表（商品，周边） 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface ProductService extends IService<Product> {

    //根据shopId查询分页类别
    Page<Product> pageByShopId(Integer pageNum, Integer pageSize, Integer shopId);

    boolean addProduct(Product product);

    Page<Product> getByCategory(Integer pageNum, Integer pageSize, Integer category);

    Page<Product> getByShop(Integer pageNum, Integer pageSize, Integer category);

    boolean deleteByIds(String ids);

    List<Integer> getShopIds(String ids);

    Product getByIdStock(Integer id);

    boolean modifyStockByIds(String ids, Integer isPet, String counts);


    List<Object> getProOrPetByIds(String ids, String isPet);

    List<Product> getByProIds(String ids);

    boolean checkStock(Integer id);

    void addAccessCount(Integer id);

    List<Product> getProductByShopId(Integer shopId);
}
