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
    @Select({
           "SELECT\n" +
                   "\tf.* ,\n" +
                   "\tp.*,\n" +
                   "\tp.`name` AS petName,\n" +
                   "\tp.price AS petPrice,\n" +
                   "\tp.img AS petImg,\n" +
                   "\tpro.*,\n" +
                   "\tpro.`name` AS productName,\n" +
                   "\tpro.price AS productPrice,\n" +
                   "\tpro.img AS productImg,\n" +
                   "\tu.username AS username\n" +
                   "FROM\n" +
                   "\tfavor f\n" +
                   "\tLEFT JOIN pet p ON f.pet_id = p.id\n" +
                   "\tLEFT JOIN product pro ON f.product_id = pro.id \n" +
                   "\tLEFT JOIN user u ON f.user_id = u.id\n" +
                   "WHERE\n" +
                   "\tf.user_id = #{userId}"
    })
    List<FavorVO> getA(Integer userId);
}
