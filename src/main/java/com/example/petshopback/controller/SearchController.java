package com.example.petshopback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.Product;
import com.example.petshopback.entity.Search;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.service.SearchService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return searchService.searchEntityByKey(pageNum, pageSize, option, key);
    }


}
