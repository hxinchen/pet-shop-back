package com.example.petshopback.mapper;

import com.example.petshopback.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petshopback.entity.CartVO;
import com.example.petshopback.entity.FavorVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Mapper
@Repository
public interface CartMapper extends BaseMapper<Cart> {
    @Select({
"SELECT\n" +
        "\tc.* ,\n" +
        "\tpro.*,\n" +
        "\tpro.`name` AS productName,\n" +
        "\tpro.price AS productPrice,\n" +
        "\tpro.img AS productImg\n" +
        "FROM\n" +
        "\tcart c\n" +
        "\tLEFT JOIN product pro ON c.product_id = pro.id \n" +
        "WHERE\n" +
        "\tc.user_id = #{userId}"
    })
    List<CartVO> getA(Integer userId);
}
