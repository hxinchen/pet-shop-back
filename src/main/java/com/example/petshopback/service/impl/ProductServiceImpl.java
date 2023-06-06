package com.example.petshopback.service.impl;

import com.example.petshopback.entity.Product;
import com.example.petshopback.mapper.ProductMapper;
import com.example.petshopback.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品表（商品，周边） 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:53:20
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
