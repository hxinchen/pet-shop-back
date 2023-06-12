package com.example.petshopback.controller;

import com.example.petshopback.entity.PetCategory;
import com.example.petshopback.service.PetCategoryService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 宠物类别表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/petCategory")
public class PetCategoryController {
    @Autowired
    private PetCategoryService petCategoryService;

    @PostMapping("/add")
    public Result add(@RequestBody PetCategory petCategory) {
        Result result = new Result();
        try {
            PetCategory isExit = petCategoryService.getByName(petCategory.getName());
            if (isExit != null) {
                result.fail("宠物分类：" + petCategory.getName() + "已存在");
            } else {
                 result.setData(petCategoryService.save(petCategory));
                 result.success("新增分类成功");
            }
        } catch (Exception e) {
            result.fail("新增分类失败：" + e.getMessage());
        }
        return result;
    }

    @PostMapping("/update")
    public Result update(@RequestBody PetCategory petCategory) {
        Result result = new Result();
        try {
            PetCategory oldCategory = petCategoryService.getById(petCategory.getId());
            if (oldCategory == null) {
                result.fail("宠物分类：" + petCategory.getName() + "不存在");
            } else {
                oldCategory.setUseful(false);
                petCategoryService.updateById(oldCategory);
                PetCategory newCategory = new PetCategory();
                newCategory.setName(petCategory.getName());
                newCategory.setUseful(true);
                petCategoryService.save(newCategory);
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

        List<PetCategory> isExit = petCategoryService.getAllCate();
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
