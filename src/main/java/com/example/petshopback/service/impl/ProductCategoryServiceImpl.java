package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.mapper.ProductCategoryMapper;
import com.example.petshopback.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品（商品）类别 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Override
    public List<ProductCategory> getAllCate() {

        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "useful");
        queryWrapper.eq("useful", 1);
//        System.out.println(this.list(queryWrapper));
        return this.list(queryWrapper);
    }

    @Override
    public ProductCategory getByName(String name) {
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return this.getOne(queryWrapper);
    }


}
