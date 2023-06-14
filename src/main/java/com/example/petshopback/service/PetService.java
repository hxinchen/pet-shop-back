package com.example.petshopback.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Pet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 宠物表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface PetService extends IService<Pet> {

    boolean addPet(Pet pet);

    Page<Pet> getByCategory(Integer pageNum, Integer pageSize, Integer category);


    boolean deleteByIds(String ids);
}
