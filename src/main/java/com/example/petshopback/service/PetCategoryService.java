package com.example.petshopback.service;

import com.example.petshopback.entity.PetCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 宠物类别表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface PetCategoryService extends IService<PetCategory> {

    List<PetCategory> getAllCate();


    PetCategory getByName(String name);

}
