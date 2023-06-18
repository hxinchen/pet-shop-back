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
        "SELECT",
        "c.*,",
        "s.name AS shopName,",
        "p.name AS productName,",
        "p.img AS productImg,",
        "p.price AS productPrice,",
        "p.stock AS productStock",
        "FROM",
        "cart c",
        "JOIN product p ON c.product_id = p.id",
        "JOIN shop s ON p.shop_id = s.id",
        "WHERE",
        "c.user_id = #{userId}"
    })
    List<CartVO> getCartByUserId(Integer userId);

}
