package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.config.RedisConfig;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.Product;
import com.example.petshopback.entity.Search;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.service.SearchService;
import com.example.petshopback.utils.JwtUtil;
import com.example.petshopback.utils.RedisUtils;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * <p>
 * 搜索 前端控制器
 * </p>
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private HttpServletRequest request;
    /**
     * 根据关键字搜索实体
     *
     * @param pageNum   当前页码
     * @param pageSize  每页大小
     * @param option    查询选项
     * @param key       搜索关键字
     * @return          搜索结果
     */
    @GetMapping("/searchByKey")
    public Result searchByKey(Integer pageNum, Integer pageSize, Integer option, String key) {
        Result result = searchService.searchEntityByKey(pageNum, pageSize, option, key);
        String token = request.getHeader("token");
        if (token == null) {
            return result;
        }
        String username = JwtUtil.validateToken(token);
        //用redis操作zset记录用户的搜索历史和搜索次数
        redisUtils.zAdd("searchHistory:"+username, key,System.currentTimeMillis() );
        return result;

    }

//    获取用户搜索历史
    @GetMapping("/getUserSearchHistory")
    public Result getUserSearchHistory() {
        Result result = new Result();
        String token = request.getHeader("token");
        if (token == null) {
            return result;
        }
        String username =  JwtUtil.validateToken(token);
        if(redisUtils.zSize("searchHistory:"+username)>10){
            redisUtils.zRemoveRange("searchHistory:"+username,0,redisUtils.zSize("searchHistory:"+username)-11);
        }
        result.setData(redisUtils.zReverseRange("searchHistory:"+username,0,9));
//        redisUtils.hDeleteKey("searchHistory:"+username);
        System.out.println("searchHistory:"+username);
        result.success("获取到用户搜索历史");
        return result;
    }

    //删除用户搜索历史
    @PostMapping("/deleteUserSearchHistory")
    public Result deleteUserSearchHistory(String key) {
        Result result = new Result();
        String token = request.getHeader("token");
        if (token == null) {
            return result;
        }
        String username = JwtUtil.validateToken(token);
        if(redisUtils.zRemove("searchHistory:"+username,key)==0){
            result.fail("删除失败");
            return result;
        }else {
            result.success("删除成功");
        }
        return result;
    }

    //清空用户搜索历史
    @PostMapping("/clearUserSearchHistory")
    public Result clearUserSearchHistory() {
        Result result = new Result();
        String token = request.getHeader("token");
        if (token == null) {
            result.fail("清空失败,请先登录");
            return result;
        }
        String username = JwtUtil.validateToken(token);
        if(redisUtils.zSize("searchHistory:"+username)==0){
            result.fail("清空失败,搜索历史为空");
            return result;
        }
        redisUtils.zRemoveRange("searchHistory:"+username,0,-1);
        result.success("清空成功");
        return result;
    }
}
