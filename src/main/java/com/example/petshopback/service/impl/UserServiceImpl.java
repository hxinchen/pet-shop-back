package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.User;
import com.example.petshopback.mapper.UserMapper;
import com.example.petshopback.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Map<String, Object> register(User user) {
        // 设置默认头像
        user.setAvatar("default.png");
        // 设置创建时间
        user.setCreateTime(String.valueOf(LocalDateTime.now()));
        // 设置默认为普通用户
        user.setIsAdmin(false);
        user.setIsBusiness(false);
        // 对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        this.save(user);
        // 生成Token
        String token = JwtUtil.generateToken(user.getId().toString());
        Map<String, Object> map = new HashMap<>();
        // 将Token存储到Map中
        map.put("token", token);
        map.put("username", user.getUsername());
        return map;
    }

    @Override
    public User getByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = this.getOne(wrapper);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public Map<String, Object> login(User user) {
//        String password = user.getPassword();
//        // 对密码进行加密
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        result.success("登录成功");
//        // 生成Token
//        String token = JwtUtil.generateToken(loginUser.getId().toString());
//        Map<String, Object> map = new HashMap<>();
//        // 将Token存储到Map中
//        user.setPassword(null);
//        map.put("token", token);
//        map.put("user", loginUser);
        return null;
    }
}
