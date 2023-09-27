package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.PetCategory;
import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.service.ProductCategoryService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

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
        if (productCategoryService.addProductCategory(productCategory)) {
            result.setData(productCategory);
            result.success("新增分类成功");
        } else {
            result.fail("新增分类失败");
        }
        return result;
    }

    // 启用禁用useful
    @PostMapping("/updateUseful")
    public Result updateUseful(Integer id) {
        Result result = new Result();
        if (productCategoryService.updateUsefulById(id)) {
            result.success("更新分类成功");
        } else {
            result.fail("更新分类失败");
        }
        return result;
    }

    // 修改类别
    @PostMapping("/modify")
    public Result modify(@RequestBody ProductCategory productCategory) {
        Result result = new Result();
        if (productCategoryService.modifyProductCategory(productCategory)) {

            result.success("修改分类成功");
        } else {
            result.fail("修改分类失败");
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

    @GetMapping( "/getPageCate")
    public Result getPageCate(Integer pageNum, Integer pageSize) {
        Result result = new Result();
        Page<ProductCategory> isExit = productCategoryService.getPageCate(pageNum,pageSize);
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

    @GetMapping( "/getAllPageCate")
    public Result getAllPageCate(Integer pageNum, Integer pageSize) {
        Result result = new Result();
        Page<ProductCategory> isExit = productCategoryService.getAllPageCate(pageNum,pageSize);
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

    // 根据ids删除
    @PostMapping("/deleteByIds")
    public Result deleteByIds(String ids) {
        Result result = new Result();
        if (productCategoryService.deleteByIds(ids)) {
            result.success("删除分类成功");
        } else {
            result.fail("删除分类失败");
        }
        return result;
    }

    @GetMapping("/getCategoryData")
    public Result<Map<String,List<String>>> getCategoryData() {
        Result<Map<String,List<String>>> result = new Result<>();
        result.setData(productCategoryService.getCategoryData());
            result.success("获得数据成功");

        return result;
    }
}
