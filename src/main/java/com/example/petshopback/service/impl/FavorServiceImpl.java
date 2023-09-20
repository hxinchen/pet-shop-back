package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.Favor;
import com.example.petshopback.entity.FavorVO;
import com.example.petshopback.mapper.FavorMapper;
import com.example.petshopback.service.FavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.DateTool;
import com.example.petshopback.utils.JwtUtil;
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
    public List<FavorVO> getAll(int userId,Boolean isPet){
        List<FavorVO> favorVOList=favorMapper.getAll(userId,isPet);
        return favorVOList;
    }

    @Override
    public void add(Favor favor) {
        favor.setCreateTime(DateTool.getCurrTime());
        this.save(favor);
    }

    @Override
    public int findByfavorId(int favorId, Boolean isPet) {
        String token= request.getHeader("token");
        if(token==""){
            return 0;
        }
        Integer userId=Integer.parseInt(JwtUtil.validateToken(token));
        QueryWrapper<Favor> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("is_pet",isPet);
        queryWrapper.eq("favor_id",favorId);
        queryWrapper.select("id");
        Favor favor=this.getOne(queryWrapper);
        //如果查到了，返回id，否则返回0
        if(favor!=null) {
            return favor.getId();
        }else{
            return 0;
        }
    }
}
