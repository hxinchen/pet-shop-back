package com.example.petshopback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    Page<PetCategory> getPageCate(Integer pageNum, Integer pageSize);

    PetCategory getByName(String name);

    boolean modifyPetCategory(PetCategory petCategory);

    boolean addPetCategory(PetCategory petCategory);

    boolean updateUsefulById(Integer id);
}
