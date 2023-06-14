package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.PetCategory;
import com.example.petshopback.mapper.PetCategoryMapper;
import com.example.petshopback.service.PetCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 宠物类别表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class PetCategoryServiceImpl extends ServiceImpl<PetCategoryMapper, PetCategory> implements PetCategoryService {
    @Override
    public List<PetCategory> getAllCate() {

        QueryWrapper<PetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("useful", 1);
//        System.out.println(this.list(queryWrapper));
        return this.list(queryWrapper);
    }

    public boolean addPetCategory(PetCategory petCategory) {
        try {
            PetCategory isExit = getByName(petCategory.getName());
            if (isExit != null) {
                throw new Exception("宠物分类：" + petCategory.getName() + "已存在");
            } else {
                save(petCategory);
                return true;
            }
        } catch (Exception e) {
            System.out.println("新增分类失败：" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUsefulById(Integer id) {
        try {
            PetCategory isExit = getById(id);
            if (isExit == null) {
                throw new Exception("宠物分类：" + id + "不存在");
            } else {
                isExit.setUseful(!isExit.getUseful());
                updateById(isExit);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public PetCategory getByName(String name) {
        QueryWrapper<PetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean modifyPetCategory(PetCategory petCategory) {
        try {
            PetCategory oldCategory = getById(petCategory.getId());
            if (oldCategory == null) {
                throw new Exception("宠物分类：" + petCategory.getName() + "不存在");
            } else {
                // 旧useful置为false
                oldCategory.setUseful(false);
                updateById(oldCategory);
                PetCategory newCategory = new PetCategory();
                newCategory.setName(petCategory.getName());
                save(newCategory);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
