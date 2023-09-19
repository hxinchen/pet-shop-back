package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.Product;
import com.example.petshopback.service.FavorService;
import com.example.petshopback.service.ProductCategoryService;
import com.example.petshopback.service.ProductService;
import com.example.petshopback.service.ShopService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

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
    private ShopService shopService;

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
        Product product = productService.getByIdStock(id);
        if (product == null) {
            result.fail("查询产品失败");
            return result;
        }
        product.put("cateName", productCategoryService.getById(product.getCategoryId()).getName());
        product.put("shopName", shopService.getById(product.getShopId()).getName());
//        product.put("isFavor", favorService.getFavorByProductId(product.getId()));
        result.setData(product);
        result.success("查询产品成功");
        return result;
    }

    // 根据产品ids查询产品
    @GetMapping("/getByProIds")
    public Result getByIds(String ids) {
        Result result = new Result();
        List<Product> list = productService.getByProIds(ids);
        if (list == null) {
            result.fail("查询产品失败");
            return result;
        }
        result.setData(list);
        result.success("查询产品成功");
        return result;
    }

    // 根据ids查询产品，或者宠物
    @GetMapping("/getProOrPetByIds")
    public Result getProOrPetByIds(String ids, String isPet) {
        Result result = new Result();
        List<Object> list = productService.getProOrPetByIds(ids, isPet);
        if (list == null) {
            result.fail("查询产品失败");
            return result;
        }
        result.setData(list);
        result.success("查询产品成功");
        return result;
    }

    // 根据ids产品获取商店ids
    @GetMapping("/getShopIds")
    public Result getShopIds(String ids) {
        Result result = new Result();
        List<Integer> list = productService.getShopIds(ids);
        if (list == null) {
            result.fail("查询产品失败");
            return result;
        }
        result.setData(list);
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

    // 根据ids修改库存, 是否宠物
    @PostMapping("/modifyStockByIds")
    public Result modifyStockByIds(String ids, String isPets, String counts) {
        Result result = new Result();
        if (productService.modifyStockByIds(ids, isPets, counts)) {
            result.success("修改产品库存成功");
        } else {
            result.fail("修改产品库存失败");
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
            productPage.getRecords().get(i).put("shopName", shopService.getById(productPage.getRecords().get(i).getShopId()).getName());
            // productPage.getRecords().get(i).put("isFavor", favorService.getFavorById(productPage.getRecords().get(i).getId()) == null ? 0 : 1);
        }
        result.setData(productPage);
        result.success("查询成功");
        return result;
    }

    //根据shopId查询分页类别
    @GetMapping("/pageByShopId")
    public Result pageByShopId(Integer pageNum, Integer pageSize, Integer shopId) {
        Result result = new Result();
        Page<Product> productPage = productService.pageByShopId(pageNum, pageSize, shopId);
        result.setData(productPage);
        result.success("查询成功");
        return result;
    }

    //检查商品库存
    @GetMapping("/checkStock")
    public Result checkStock(Integer productId) {
        Result result = new Result();
        if (productService.checkStock(productId)) {
            result.success("库存充足");
        } else {
            result.fail("库存不足");
        }
        return result;
    }
    // 增加访问量
    @PostMapping("/addVisit")
    public Result addVisit(Integer productId) {
        Result result = new Result();
        Product product = productService.getById(productId);
        product.setAccessCount(product.getAccessCount() + 1);
        if (productService.updateById(product)) {
            result.success("增加访问量成功");
        } else {
            result.fail("增加访问量失败");
        }
        return result;
    }
}
