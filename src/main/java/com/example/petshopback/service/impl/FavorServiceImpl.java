package com.example.petshopback.service.impl;

import com.example.petshopback.entity.Favor;
import com.example.petshopback.entity.FavorVO;
import com.example.petshopback.mapper.FavorMapper;
import com.example.petshopback.service.FavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class FavorServiceImpl extends ServiceImpl<FavorMapper, Favor> implements FavorService {
    @Autowired
    private FavorMapper favorMapper;
    @Override
    public Result getA(Integer userId){
        Result result=new Result();
        result.setData(favorMapper.getA(userId));
        return result;
    }
}
