package com.example.petshopback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petshopback.entity.Search;
import com.example.petshopback.utils.Result;

import java.io.Serializable;

/**
 * <p>
 *  搜索服务类
 * </p>
 * @author hahaha
 * @since 2022-10-18
 */
public interface SearchService extends IService<Search> {
     Result searchEntityByKey(Integer pageNum, Integer pageSize, Integer option, String key);
}
