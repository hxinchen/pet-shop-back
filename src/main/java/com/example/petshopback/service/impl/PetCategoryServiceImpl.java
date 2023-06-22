package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.PetCategory;
import com.example.petshopback.entity.ProductCategory;
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
    public Page<PetCategory> getPageCate(Integer pageNum, Integer pageSize) {

        QueryWrapper<PetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("useful", true);
        Page<PetCategory> page = new Page<>(pageNum, pageSize);
        return this.page(page, queryWrapper);
    }
    @Override
    public List<PetCategory> getAllCate() {

        QueryWrapper<PetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("useful", true);
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
    public Page<PetCategory> getAllPageCate(Integer pageNum, Integer pageSize) {
        QueryWrapper<PetCategory> queryWrapper = new QueryWrapper<>();
        Page<PetCategory> page = new Page<>(pageNum, pageSize);
        return this.page(page, queryWrapper);
    }

    @Override
    public PetCategory getByName(String name) {
        QueryWrapper<PetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean modifyPetCategory(PetCategory petCategory) {
            PetCategory oldCategory = getById(petCategory.getId());
            if (oldCategory != null) {
                if (!petCategory.getName().equals(oldCategory.getName())) {
                    // 旧useful置为false
                    oldCategory.setUseful(false);
                    updateById(oldCategory);
                    PetCategory newCategory = new PetCategory();
                    newCategory.setName(petCategory.getName());
                    newCategory.setImg(petCategory.getImg());
                    newCategory.setUseful(petCategory.getUseful());
                    save(newCategory);
                }
                else {
                    oldCategory.setImg(petCategory.getImg());
                    oldCategory.setUseful(petCategory.getUseful());
                    updateById(oldCategory);
                }
                return true;
            }
        return false;
    }

}
