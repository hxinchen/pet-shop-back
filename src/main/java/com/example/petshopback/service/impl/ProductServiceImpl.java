package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.Product;
import com.example.petshopback.mapper.ProductMapper;
import com.example.petshopback.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品表（商品，周边） 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public boolean addProduct(Product product) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", product.getName());
        Product isExist = getOne(queryWrapper);
        if (isExist != null) {
            System.out.println("该宠物周边已存在，新增失败");
            return false;
        }
        save(product);
        return true;
    }
    @Override
    public Page<Product> getByCategory(Integer pageNum, Integer pageSize, Integer category) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (category == 0)
            return this.page(page, queryWrapper);
        queryWrapper.eq("category_id", category);
        return this.page(page, queryWrapper);
    }


    @Override
    public boolean deleteByIds(String ids) {
        List<String> list = new ArrayList<>();
        String[] array = ids.split(",");
        for (String i:array) {
            list.add(i);
        }
        return this.removeByIds(list);
    }

    @Override
    public List<Integer> getShopIds(String ids) {
        List<Integer> list = new ArrayList<>();
        String[] array = ids.split(",");

        for (String id : array) {
            Integer productId = Integer.valueOf(id);
            Product product = this.getById(productId);
            if (product != null) {
                list.add(product.getShopId());
            }
        }

        return list;
    }

}
