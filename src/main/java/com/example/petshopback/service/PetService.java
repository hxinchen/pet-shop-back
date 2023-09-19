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
    // 根据shopid分页查询宠物
    Page<Pet> pageByShopId(Integer pageNum, Integer pageSize, Integer shopId);
    Page<Pet> getByCategory(Integer pageNum, Integer pageSize, Integer category);
    Pet addVideo(Integer petId,Integer videoId);

    boolean deleteByIds(String ids);

    void updateUseful(Integer petId);
}
