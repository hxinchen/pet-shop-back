package com.example.petshopback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface UserService extends IService<User> {
     Map<String, Object> register(User user);

     User getByName(String username);

     Map<String, Object> login(User user);

    Page<User> getAllUser(Integer pageNum, Integer pageSize);

    User updateUser(User user);

    void deleteByIds(String ids);

    Boolean updateAvatar(Integer userId, String avatar);
}
