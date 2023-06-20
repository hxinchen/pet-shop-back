package com.example.petshopback.mapper;

import com.example.petshopback.entity.Favor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petshopback.entity.FavorVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 收藏表 Mapper 接口
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Mapper
@Repository
public interface FavorMapper extends BaseMapper<Favor> {
    @Select("SELECT " +
            "   f.is_pet, " +
            "   f.id, " +
            "   IF(f.is_pet = 1, p.id, pro.id) AS favor_id, " +
            "   IF(f.is_pet = 1, p.name, pro.name) AS favor_name, " +
            "   IF(f.is_pet = 1, p.price, pro.price) AS favor_price, " +
            "   IF(f.is_pet = 1, p.img, pro.img) AS favor_img, " +
            "   IF(f.is_pet = 1, p.breed,'') AS favor_breed, " +
            "   IF(f.is_pet = 1, (SELECT shop.name FROM shop WHERE shop.id = p.shop_id), (SELECT shop.name FROM shop WHERE shop.id = pro.shop_id)) AS favor_shop_name, " +
            "   u.username " +
            "FROM " +
            "   favor f " +
            "   LEFT JOIN pet p ON f.favor_id = p.id AND f.is_pet = 1 " +
            "   LEFT JOIN product pro ON f.favor_id = pro.id AND f.is_pet = 0 " +
            "   LEFT JOIN user u ON f.user_id = u.id " +
            "WHERE " +
            "   f.user_id = #{userId} and f.is_pet=#{isPet}")
    List<FavorVO> getAll(int userId, Boolean isPet);
}
