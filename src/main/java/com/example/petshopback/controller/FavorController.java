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
        int userId=Integer.parseInt(JwtUtil.validateToken(token));
        Favor isExit = favorService.getById(favor.getId());
        if (isExit != null) {
            result.fail("已收藏");
        } else {
            favor.setUserId(userId);
            favorService.add(favor);
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

    @GetMapping("/getAll")
    public  Result getAll(){
        Result result = new Result();
        System.out.println("进入getAll");
        String token= request.getHeader("token");
        int userId=Integer.parseInt(JwtUtil.validateToken(token));
        List<FavorVO> favorVOList=favorService.getAll(userId);
        result.setData(favorVOList);
        return result;
}
}