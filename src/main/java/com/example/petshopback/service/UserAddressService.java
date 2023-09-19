package com.example.petshopback.service;

import com.example.petshopback.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户地址表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface UserAddressService extends IService<UserAddress> {

    List<UserAddress> getAddress();

    Boolean updateDefault(Integer addressId, Boolean isDefault);
//    置默认为非默认
    Boolean setNotDefault();
    UserAddress getById(Integer addressId);
    UserAddress getByUserId(Integer userId);
    UserAddress getDefault();
}
