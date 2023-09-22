package com.example.petshopback.controller;

import com.example.petshopback.entity.Favor;
import com.example.petshopback.entity.FavorVO;
import com.example.petshopback.service.FavorService;
import com.example.petshopback.utils.DateTool;
import com.example.petshopback.utils.JwtUtil;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/list")
    public List<Favor> list(){
        return favorService.list();
    }

    @PostMapping("/add")
    public Result add(@RequestBody Favor favor){
        Result result = new Result();
        String token= request.getHeader("token");
        Integer userId=Integer.parseInt(JwtUtil.validateToken(token));
        if(userId==0){
            result.fail("请先登录");
            return result;
        }
        Integer isExit = favorService.findByfavorId(favor.getFavorId(),favor.getIsPet());
        if (isExit!=0) {
            result.fail("已收藏");
        } else {
            favor.setUserId(userId);
            favorService.add(favor);
            result.setData(favor.getId());
            result.success("收藏成功");
        }
        return result;
    }

    @PostMapping("/deleteById")
    public Result deleteById(Integer id){
        Result result=new Result();
        if(favorService.removeById(id)) {
            result.success("删除成功");
        }else{
            result.fail("删除失败");
        }
        return result;
    }
    //根据宠物或商品id查找收藏
    @GetMapping("/findByPetId")
    public Result findByPetId(Integer favorId,Boolean isPet){
        Result result=new Result();
        Integer id=favorService.findByfavorId(favorId,isPet);
        if(id!=0){
            result.setData(id);
            result.success("已收藏");
        }else {
            result.fail("未收藏");
        }
        return result;
    }

    @GetMapping("/getAll")
    public  Result getAll(Boolean isPet){
        Result result = new Result();
        System.out.println("进入getAll");
        String token= request.getHeader("token");
        Integer userId=Integer.parseInt(JwtUtil.validateToken(token));
        if(userId==0){
            result.fail("请先登录");
            return result;
        }
        List<FavorVO> favorVOList=favorService.getAll(userId,isPet);
        result.setData(favorVOList);
        return result;
}
}