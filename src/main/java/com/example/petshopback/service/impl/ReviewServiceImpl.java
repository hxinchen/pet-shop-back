package com.example.petshopback.service.impl;

import com.example.petshopback.entity.Review;
import com.example.petshopback.mapper.ReviewMapper;
import com.example.petshopback.service.ReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评价表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:54:17
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

}
