package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.PetCategory;
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
        queryWrapper.eq("useful", true);
//        System.out.println(this.list(queryWrapper));
        return this.list(queryWrapper);
    }
    @Override
    public Page<ProductCategory> getPageCate(Integer pageNum, Integer pageSize) {

        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("useful", true);
        Page<ProductCategory> page = new Page<>(pageNum, pageSize);
        return this.page(page, queryWrapper);
    }
    @Override
    public ProductCategory getByName(String name) {
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean addProductCategory(ProductCategory productCategory) {
        try {
            ProductCategory isExit = getByName(productCategory.getName());
            if (isExit != null) {
                throw new Exception("产品分类：" + productCategory.getName() + "已存在");
            } else {
                save(productCategory);
                return true;
            }
        } catch (Exception e) {
            System.out.println("新增分类失败：" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modifyProductCategory(ProductCategory productCategory) {
            ProductCategory oldCategory = getById(productCategory.getId());
            if (oldCategory == null) {
                System.out.println("商品分类：" + productCategory.getName() + "不存在");
            } else {
                // 旧useful置为false
                oldCategory.setUseful(false);
                updateById(oldCategory);
                ProductCategory newCategory = new ProductCategory();
                newCategory.setName(productCategory.getName());
                save(newCategory);
                return true;
            }
        return false;
    }

    @Override
    public boolean updateUsefulById(Integer id) {
        try {
            ProductCategory isExit = getById(id);
            if (isExit == null) {
                throw new Exception("商品分类：" + id + "不存在");
            } else {
                isExit.setUseful(false);
                updateById(isExit);
                return true;
            }
        } catch (Exception e) {

            return false;
        }
    }
}
