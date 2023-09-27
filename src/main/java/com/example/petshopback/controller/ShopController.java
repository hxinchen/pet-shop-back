package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.service.ShopService;
import com.example.petshopback.service.UserService;
import com.example.petshopback.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 	商店表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisUtils redisUtils;
    // 新增商店
    @PostMapping("/add")
    public Result add(@RequestBody Shop shop) {
        Result result = new Result();
        if (shopService.addShop(shop)) {
            result.setData(shop);
            result.success("新增商店成功");
        } else {
            result.fail("新增商店失败");
        }
        return result;
    }

    // 编辑商店
    @PostMapping("/modify")
    public Result modify(@RequestBody Shop shop) {
        Result result = new Result();
        if (shopService.updateById(shop)) {
            result.setData(shop);
            result.success("编辑商店成功");
        } else {
            result.fail("编辑商店失败");
        }
        return result;
    }

    @GetMapping("/getList")
    public Result getList() {
        Result result = new Result();
        List<Shop> list = shopService.list();
        System.out.println(list);
        if (list != null) {
            result.success("查询成功");
            result.setData(list);
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }

    // 查询全部商店
    @GetMapping("/getAll")
    public Result getAll(Integer pageNum, Integer pageSize) {
        Result result = new Result();
        String token = request.getHeader("token");
        Integer userId = Integer.parseInt(JwtUtil.validateToken(token));
        Page<Shop> page = shopService.getAllShop(pageNum, pageSize);
        List<Shop> list = new ArrayList<>();
        if (page.getRecords() != null) {
            result.success("查询成功");
            for (int i=0; i < page.getRecords().size(); i++) {
                page.getRecords().get(i).put("userName",userService.getById(page.getRecords().get(i).getUserId()).getUsername());
                if(!userService.getById(userId).getIsAdmin()){
                    if(Objects.equals(page.getRecords().get(i).getUserId(), userId)) {
                        //排除其他用户的商店
                        list.add(page.getRecords().get(i));
                    }
                }else{
                    list.add(page.getRecords().get(i));
                }
            }
            //把list的结果放到page里面

            page.setRecords(list);
            result.setData(page);
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }

    // 根据id查询商店
    @GetMapping("/getByUserId")
    public Result getByUserId() {
        Result result = new Result();
        List<Shop> shop = shopService.getShop();
        Transform transform = new Transform();
        // 输出
        System.out.println(shop);
        if (shop != null) {
            result.success("查询成功");
            result.setData(transform.listToPage(shop, 1, 5));
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }

    // 根据id查询商店
    @GetMapping("/getById")
    public Result getById(int id) {
        Result result = new Result();
        Shop shop = shopService.getById(id);
        if (shop != null) {
            result.success("查询成功");
            result.setData(shop);
        }
        else {
            result.fail("查询失败");
        }
        return result;
    }

    //根据时间和商品类型 获取商店销售额
    @GetMapping("/getSalesByTime")
    public Result getSalesByTime(Integer shopId,String startTime,String endTime,Integer isPet) throws ParseException {
        Result result = new Result();
        Double sales=null;
        String key=shopId+"-"+startTime+"-"+endTime+"-"+isPet;
        //检查redis中是否有缓存
        //endTime小于当前时间，缓存一天
        Long time1=DateTool.countDay(endTime);
        if(redisUtils.hExists("shopSales",key)&&time1<0){
            sales= (Double) redisUtils.hGet("shopSales",key);
        }else {
            sales = shopService.getSalesByTime(shopId,startTime,endTime,isPet);
            //查过去的数据则存入redis
            if(time1<0){
                //将结果放入redis中
                redisUtils.hPut("shopSales",key,sales);
                //设置过期时间
                redisUtils.HExpire("shopSales",60*60);
            }
        }
        if (sales != null) {
            result.success("查询成功");
            result.setData(sales);
        }
        else {
            result.fail("查询失败,sales为null");
        }
        return result;
    }

    // 根据时间和商品类型 获取商店销售额量
    @GetMapping("/getSalesCountByTime")
    public Result getSalesCountByTime(Integer shopId,String startTime,String endTime,Integer isPet) throws ParseException {
        Result result = new Result();
        Integer salesCount=null;
        String key=shopId+"-"+startTime+"-"+endTime+"-"+isPet;
        //检查redis中是否有缓存
        //endTime小于当前时间，缓存一天
        Long time1=DateTool.countDay(endTime);
        if(redisUtils.hExists("shopSalesCount",key)&&time1<0){
            salesCount= (Integer) redisUtils.hGet("shopSalesCount",key);
        }else {
            salesCount = shopService.getSalesCountByTime(shopId,startTime,endTime,isPet);
            if(time1<0){
                //将结果放入redis中
                redisUtils.hPut("shopSalesCount",key,salesCount);
                //设置过期时间
                redisUtils.HExpire("shopSalesCount",60*60);
            }
        }
        if (salesCount != null) {
            result.success("查询成功");
            result.setData(salesCount);
        }
        else {
            result.fail("查询失败,salesCount为null");
        }
        return result;
    }

    // 获取商店访问量
    @GetMapping("/getAccessCount")
    public Result getAccessCount(Integer shopId,Integer isPet) throws ParseException {
        Result result = new Result();
        Integer accessCount=null;
        String key=shopId+"-"+isPet;
        //检查redis中是否有缓存
        if(redisUtils.hExists("shopAccessCount",key)){
            accessCount= (Integer) redisUtils.hGet("shopAccessCount",key);
        }else {
            accessCount = shopService.getAccessCount(shopId,isPet);
            //将结果放入redis中
            redisUtils.hPut("shopAccessCount",key,accessCount);
            //设置过期时间
            redisUtils.HExpire("shopAccessCount",60*60);
        }
        if (accessCount != null) {
            result.success("查询成功");
            result.setData(accessCount);
        }
        else {
            result.fail("查询失败,accessCount为null");
        }
        return result;
    }

}
