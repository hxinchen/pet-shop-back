package com.example.petshopback.service.impl;

import com.example.petshopback.entity.Pet;
import com.example.petshopback.mapper.PetMapper;
import com.example.petshopback.service.PetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宠物表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:52:52
 */
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

}
