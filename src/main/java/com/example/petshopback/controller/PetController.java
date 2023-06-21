package com.example.petshopback.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.Product;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.service.FavorService;
import com.example.petshopback.service.PetCategoryService;
import com.example.petshopback.service.PetService;
import com.example.petshopback.service.ShopService;
import com.example.petshopback.utils.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 宠物表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private PetCategoryService petCategoryService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private FavorService favorService;

    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody Pet pet) {
        Result result = new Result();
        if (petService.addPet(pet)) {
            result.setData(pet);
            result.success("新增宠物成功");
        } else {
            result.fail("新增宠物失败");
        }
        return result;
    }

    // 根据id查询
    @GetMapping("/getById")
    public Result getById(Integer id) {
        Result result = new Result();
        Pet pet = petService.getById(id);
        pet.put("cateName", petCategoryService.getById(pet.getCategoryId()).getName());
        pet.put("shopName", shopService.getById(pet.getShopId()).getName());
        result.setData(pet);
        result.success("查询宠物成功");
        return result;
    }

    // 修改宠物信息
    @PostMapping("/modify")
    public Result modify(@RequestBody Pet pet) {
        Result result = new Result();
        if (petService.updateById(pet)) {
            result.success("修改宠物成功");
        } else {
            result.fail("修改宠物失败");
        }

        return result;
    }

    //根据ids删除宠物
    @PostMapping("/deleteByIds")
    public Result deleteByIds(String ids){
        Result result = new Result();

        if (petService.deleteByIds(ids)) {
            result.success("批量删除成功");
        } else {
            result.fail("批量删除失败");
        }
        return result;
    }

    // 按类别查询
    @GetMapping("/getByCategory")
    public Result getByCategory(Integer pageNum, Integer pageSize, Integer category) {
        Result result = new Result();
        Page<Pet> page = petService.getByCategory(pageNum, pageSize, category);

        return getPet(result, page);
    }


    private Result getPet(Result result, Page<Pet> pet) {
        for (int i=0; i < pet.getRecords().size(); i++) {
            pet.getRecords().get(i).put("cateName",petCategoryService.getById(pet.getRecords().get(i).getCategoryId()).getName());
            pet.getRecords().get(i).put("shopName",shopService.getById(pet.getRecords().get(i).getShopId()).getName());
//            pet.getRecords().get(i).put("isFavor", favorService.getFavorByPetId(pet.getRecords().get(i).getId()) == null ? 0 : 1);
        }
        result.setData(pet);
        result.success("查询成功");
        return result;
    }

    //按id添加视频
    @PostMapping("/addVideo")
    public Result addVideo(Integer id,Integer videoId){
        Result result = new Result();
        Pet pet= petService.addVideo(id,videoId);
        if (petService.updateById(pet)) {
            result.success("添加视频成功");
        } else {
            result.fail("添加视频失败");
        }
        return result;
    }

    // 根据shopid分页查询宠物
    @GetMapping("/pageByShopId")
    public Result pageByShopId(Integer pageNum, Integer pageSize, Integer shopId) {
        Result result = new Result();
        Page<Pet> page = petService.pageByShopId(pageNum, pageSize, shopId);
        result.setData(page);
        result.success("查询宠物成功");
        return result;
    }

}
