package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.mapper.ShopMapper;
import com.example.petshopback.service.ShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 	商店表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Override
    public boolean addShop(Shop shop) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", shop.getName());
        Shop isExist = getOne(queryWrapper);
        if (isExist != null) {
            System.out.println("商店已存在，新增商店失败");
            return false;
        }
        save(shop);
        return true;
    }

    @Override
    public List<Shop> getAllShop() {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        return this.list(queryWrapper);
    }
}
