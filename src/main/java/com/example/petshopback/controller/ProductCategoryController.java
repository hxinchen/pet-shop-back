package com.example.petshopback.controller;

import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.service.ProductCategoryService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 产品（商品）类别 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping("/add")
    public Result add(@RequestBody ProductCategory productCategory) {
        Result result = new Result();
        try {
            ProductCategory isExit = productCategoryService.getByName(productCategory.getName());
            if (isExit != null) {
                result.fail("周边分类：" + productCategory.getName() + "已存在");
            } else {
                result.setData(productCategoryService.save(productCategory));
                result.success("新增分类成功");
            }
        } catch (Exception e) {
            result.fail("新增分类失败：" + e.getMessage());
        }
        return result;
    }

    @PostMapping("/update")
    public Result update(@RequestBody ProductCategory productCategory) {
        Result result = new Result();
        try {
            ProductCategory isExit = productCategoryService.getById(productCategory.getId());
            if (isExit == null) {
                result.fail("周边分类：" + productCategory.getName() + "不存在");
            } else {
                result.setData(productCategoryService.updateById(productCategory));
                result.success("更新分类成功");
            }
        } catch (Exception e) {
            result.fail("更新分类失败：" + e.getMessage());
        }
        return result;
    }

    @GetMapping( "/getAllCate")
    public Result getAllCate() {
        Result result = new Result();

        List<ProductCategory> isExit = productCategoryService.getAllCate();
//        System.out.println(isExit);
        if (isExit != null) {
            result.success("查询成功");
            result.setData(isExit);
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }
}
