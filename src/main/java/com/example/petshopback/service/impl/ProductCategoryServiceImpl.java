package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.OrderItem;
import com.example.petshopback.entity.PetCategory;
import com.example.petshopback.entity.Product;
import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.mapper.ProductCategoryMapper;
import com.example.petshopback.service.OrderItemService;
import com.example.petshopback.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.service.ProductService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    @Lazy
    private ProductService productService;

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
        if (oldCategory != null) {
            if (!productCategory.getName().equals(oldCategory.getName())) {
                // 旧useful置为false
                oldCategory.setUseful(false);
                updateById(oldCategory);
                ProductCategory newCategory = new ProductCategory();
                newCategory.setName(productCategory.getName());
                newCategory.setImg(productCategory.getImg());
                newCategory.setUseful(productCategory.getUseful());
                save(newCategory);
            } else {
                oldCategory.setImg(productCategory.getImg());
                oldCategory.setUseful(productCategory.getUseful());
                updateById(oldCategory);
            }
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

    @Override
    public Page<ProductCategory> getAllPageCate(Integer pageNum, Integer pageSize) {
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        Page<ProductCategory> page = new Page<>(pageNum, pageSize);
        return this.page(page, queryWrapper);
    }

    @Override
    public boolean deleteByIds(String ids) {
        List<String> listIds = new ArrayList<>();
        String[] aryIds = ids.split(",");
        for(String id: aryIds){
            listIds.add(id);
        }
        return this.removeByIds(listIds);
    }
    @Override
    public Map<String, List<String>> getCategoryData() {
        Map<String, List<String>> result = new HashMap<>();
        List<String> data = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<ProductCategory> categories = this.list();

        for (ProductCategory productCategory : categories) {
            data.add("0.0"); // 初始值为 "0.0"
            name.add(productCategory.getName());
        }

        List<OrderItem> orderItems = orderItemService.list();
        for (OrderItem orderItem : orderItems) {
            // 根据 orderItem 中的 productId 获取对应的产品信息
            Product product = productService.getById(orderItem.getProductId());

            if (product != null) {
                // 在上面的 List 中增加对应类别的数量
                int categoryId = product.getCategoryId();
                double sum = Double.parseDouble(data.get(categoryId)) + orderItem.getCount() * product.getPrice();
                data.set(categoryId, String.valueOf(sum));
            }
        }

        result.put("data", data);
        result.put("name", name);
        return result;
    }
}
