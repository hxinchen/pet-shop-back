package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody PetCategory petCategory) {
        Result result = new Result();
        if (petCategoryService.addPetCategory(petCategory)) {
            result.setData(petCategory);
            result.success("新增分类成功");
        } else {
            result.fail("新增分类失败");
        }
        return result;
    }

    // 修改类别
    @PostMapping("/modify")
    public Result modify(@RequestBody PetCategory petCategory) {
        Result result = new Result();
        if (petCategoryService.modifyPetCategory(petCategory)) {
            result.success("修改分类成功");
        } else {
            result.fail("修改分类失败");
        }

        return result;
    }

    // 启用禁用useful
    @PostMapping("/updateUseful")
    public Result updateUseful(Integer id) {
        Result result = new Result();
        if (petCategoryService.updateUsefulById(id)) {
            result.success("更新分类成功");
        } else {
            result.fail("更新分类失败");
        }
        return result;
    }

    // 查询全部类别
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

    @GetMapping( "/getPageCate")
    public Result getPageCate(Integer pageNum, Integer pageSize) {
        Result result = new Result();
        Page<PetCategory> isExit = petCategoryService.getPageCate(pageNum,pageSize);
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
