package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:52:37
 */
@Getter
@Setter
@TableName("order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 是否宠物0-不是  1-是
     */
    private Boolean isPet;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 商品id（是产品则去产品表查，是宠物则去宠物表查）
     */
    private Integer productId;
}
