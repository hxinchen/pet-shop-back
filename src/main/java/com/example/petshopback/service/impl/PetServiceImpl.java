package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.Video;
import com.example.petshopback.mapper.PetMapper;
import com.example.petshopback.service.PetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.service.VideoService;
import com.example.petshopback.utils.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 宠物表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {
    @Autowired
    private VideoService videoService;
    // 新增宠物
    @Override
    public boolean addPet(Pet pet) {
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", pet.getName());
        Pet isExist = getOne(queryWrapper);
        if (isExist != null) {
            System.out.println("宠物已存在，新增宠物失败");
            return false;
        }
        Pet pet1 = pet;

        pet1.setCreateTime(DateTool.getCurrTime());
        pet1.setBirthday(DateTool.getCurrDay());

        System.out.println("id:"+ pet1.getId());
        return save(pet1);
    }

    @Override
    public Page<Pet> pageByShopId(Integer pageNum, Integer pageSize, Integer shopId) {
        Page<Pet> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        //根据每个Pet的videoId字段查询对应的video
        List<Pet> petList = this.page(page, queryWrapper).getRecords();
        List<Pet> petList1 = new ArrayList<>();
        for (Pet pet : petList) {
            String videoIds = pet.getVideoId();
            if (videoIds != null) {
                String[] videoId = videoIds.split(",");
                List<Video> videoList = new ArrayList<>();
                for (String id : videoId) {
                    Video video = videoService.getById(Integer.valueOf(id));
                    videoList.add(video);
                }
                pet.setEtc(videoList);
            }
            petList1.add(pet);
        }
        page.setRecords(petList1);
        return page;
    }

    // 根据宠物类别查询宠物
    @Override
    public Page<Pet> getByCategory(Integer pageNum, Integer pageSize, Integer category) {
        Page<Pet> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
        if (category == 0)
            return this.page(page, queryWrapper);
        queryWrapper.eq("category_id", category);
        return this.page(page, queryWrapper);
    }

    @Override
    public Pet addVideo(Integer petId, Integer videoId) {
        Pet pet = getById(petId);
        if (pet == null) {
            return null;
        }
        String videoIds= pet.getVideoId();
        //将videoId加在videoIds后面
        if (videoIds != null) {
            videoIds = videoIds + "," + videoId;
        } else {
            videoIds = videoId.toString();
        }
        pet.setVideoId(videoIds);
        updateById(pet);
        return pet;
    }

    //根据ids删除宠物
    public boolean deleteByIds(String ids) {
        List<String> list = new ArrayList<>();
        String[] array = ids.split(",");
        for (String i:array) {
            list.add(i);
        }
        return this.removeByIds(list);
    }
}
