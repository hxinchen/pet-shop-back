package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.PetCategory;
import com.example.petshopback.mapper.PetCategoryMapper;
import com.example.petshopback.service.PetCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 宠物类别表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class PetCategoryServiceImpl extends ServiceImpl<PetCategoryMapper, PetCategory> implements PetCategoryService {
    @Override
    public List<PetCategory> getAllCate() {

        QueryWrapper<PetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "useful");
        queryWrapper.eq("useful", 1);
//        System.out.println(this.list(queryWrapper));
        return this.list(queryWrapper);
    }


    @Override
    public PetCategory getByName(String name) {
        QueryWrapper<PetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return this.getOne(queryWrapper);
    }

}
