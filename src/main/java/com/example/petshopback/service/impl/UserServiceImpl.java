package com.example.petshopback.service.impl;

import com.example.petshopback.entity.User;
import com.example.petshopback.mapper.UserMapper;
import com.example.petshopback.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:54:45
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
