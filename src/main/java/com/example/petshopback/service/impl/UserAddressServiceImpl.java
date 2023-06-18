package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.UserAddress;
import com.example.petshopback.mapper.UserAddressMapper;
import com.example.petshopback.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {
    @Autowired
    private HttpServletRequest request;
        @Override
        public List<UserAddress> getAddress() {
            String token = request.getHeader("token");
//        System.out.println("token" + token);
            String userId = JwtUtil.validateToken(token);
            QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", Integer.valueOf(userId));
            return this.list(queryWrapper);
        }

    @Override
    public Boolean updateDefault(Integer addressId, Boolean isDefault) {
        QueryWrapper<UserAddress> userAddressQueryWrapper= new QueryWrapper<>();
        UserAddress userAddress=getById(addressId);
        if(userAddress==null){
            return false;
        }
        userAddress.setIsDefault(isDefault);
        this.updateById(userAddress);
        return true;
    }

    @Override
    public Boolean setNotDefault() {
            QueryWrapper<UserAddress> userAddressQueryWrapper= new QueryWrapper<>();
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
            userAddressQueryWrapper.eq("user_id",Integer.valueOf(userId));
            userAddressQueryWrapper.eq("is_default",true);
            UserAddress userAddress=getOne(userAddressQueryWrapper);
            //如果有默认地址
            if(userAddress!=null   ) {
                userAddress.setIsDefault(false);
                this.updateById(userAddress);
            }else {
                return false;
            }
            return true;
    }

    @Override
    public UserAddress getById(Integer addressId) {
        QueryWrapper<UserAddress> userAddressQueryWrapper= new QueryWrapper<>();
        userAddressQueryWrapper.eq("id",addressId);
        return this.getOne(userAddressQueryWrapper);
    }

    @Override
    public UserAddress getDefault() {
        QueryWrapper<UserAddress> userAddressQueryWrapper= new QueryWrapper<>();
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
        userAddressQueryWrapper.eq("user_id",Integer.valueOf(userId)).eq("is_default",true);

        return this.getOne(userAddressQueryWrapper);
    }

}
