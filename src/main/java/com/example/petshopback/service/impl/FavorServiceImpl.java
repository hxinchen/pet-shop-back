package com.example.petshopback.service.impl;

import com.example.petshopback.entity.Favor;
import com.example.petshopback.entity.FavorVO;
import com.example.petshopback.mapper.FavorMapper;
import com.example.petshopback.service.FavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.DateTool;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private HttpServletRequest request;
    @Override
    public List<FavorVO> getAll(int userId){
        List<FavorVO> favorVOList=favorMapper.getAll(userId);
        return favorVOList;
    }

    @Override
    public void add(Favor favor) {
        favor.setCreateTime(DateTool.getCurrTime());
        this.save(favor);
    }
}
