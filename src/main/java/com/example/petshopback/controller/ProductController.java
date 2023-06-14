package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Product;
import com.example.petshopback.service.FavorService;
import com.example.petshopback.service.ProductCategoryService;
import com.example.petshopback.service.ProductService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 产品表（商品，周边） 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private FavorService favorService;

    // 新增产品
    @PostMapping("/add")
    public Result add(@RequestBody Product product) {
        Result result = new Result();
        if (productService.addProduct(product)) {
            result.setData(product);
            result.success("新增产品成功");
        } else {
            result.fail("新增产品失败");
        }
        return result;
    }

    // 根据id查询产品
    @GetMapping("/getById")
    public Result getById(Integer id) {
        Result result = new Result();
        Product product = productService.getById(id);
        product.put("cateName", productCategoryService.getById(product.getCategoryId()).getName());
        result.setData(product);
        result.success("查询产品成功");
        return result;
    }

    // 按类别查询产品
    @GetMapping("/getByCategory")
    public Result getByCategory(Integer pageNum, Integer pageSize, Integer category) {
        Result result = new Result();
        Page<Product> page = productService.getByCategory(pageNum, pageSize, category);

        return getProduct(result, page);
    }

    // 修改周边产品信息
    @PostMapping("/modify")
    public Result modify(@RequestBody Product product) {
        Result result = new Result();
        if (productService.updateById(product)) {
            result.success("修改产品成功");
        } else {
            result.fail("修改产品失败");
        }

        return result;
    }

    // 根据ids删除周边产品
    @PostMapping("/deleteByIds")
    public Result deleteByIds(String ids){
        Result result = new Result();
        if (productService.deleteByIds(ids)) {
            result.success("删除产品成功");
        } else {
            result.fail("删除产品失败");
        }
        return result;
    }

    private Result getProduct(Result result, Page<Product> productPage) {
        for (int i = 0; i < productPage.getRecords().size(); i++) {
            productPage.getRecords().get(i).put("cateName", productCategoryService.getById(productPage.getRecords().get(i).getCategoryId()).getName());
            // productPage.getRecords().get(i).put("isFavor", favorService.getFavorById(productPage.getRecords().get(i).getId()) == null ? 0 : 1);
        }
        result.setData(productPage);
        result.success("查询成功");
        return result;
    }

}
