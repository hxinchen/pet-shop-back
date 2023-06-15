package com.example.petshopback.service;

import com.example.petshopback.entity.Favor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petshopback.utils.Result;

import java.util.List;

/**
 * <p>
 * 收藏表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface FavorService extends IService<Favor> {
    Result getA(Integer userId);
}
