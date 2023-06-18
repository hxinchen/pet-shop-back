package com.example.petshopback.controller;

import com.example.petshopback.entity.Favor;
import com.example.petshopback.service.FavorService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 收藏表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/favor")
public class FavorController {
    @Autowired
    private FavorService favorService;
    @GetMapping("/list")
    public List<Favor> list(){
        return favorService.list();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Favor favor){
        Result result = new Result();
            Favor isExit = favorService.getById(favor.getId());
            if (isExit != null) {
                result.fail("已收藏");
            } else {
                result.setData(favorService.save(favor));
                result.success("收藏成功");
            }
        return result;
    }

    @PostMapping("/del")
    public boolean del(@RequestParam String id){
        return favorService.removeById(id);
    }

@GetMapping("/get")
    public Result getA(Integer userId){
        Result result=new Result();
        result.setData(favorService.getA(userId));
        return result;
}
}