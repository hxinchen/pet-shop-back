package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.ProductCategory;
import com.example.petshopback.entity.Shop;
import com.example.petshopback.mapper.ShopMapper;
import com.example.petshopback.service.ShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private HttpServletRequest request;

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
    public Page<Shop> getAllShop(Integer pageNum, Integer pageSize) {
        Page<Shop> page = new Page<>(pageNum, pageSize);
        return this.page(page);
    }

    @Override
    public List<Shop> getShop() {
        String token = request.getHeader("token");

        String userId = JwtUtil.validateToken(token);
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", Integer.valueOf(userId));
        queryWrapper.eq("user_id", userId);
        return this.list(queryWrapper);
    }

}
