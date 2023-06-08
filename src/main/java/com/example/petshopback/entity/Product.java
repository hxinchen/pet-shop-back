package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 产品表（商品，周边）
 * </p>
 *
 * @author hahaha
 * @since 2023-06-08 07:25:48
 */
@Getter
@Setter
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 产品唯一主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 产品类型
     */
    private Integer categoryId;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品描述
     */
    private String describle;

    /**
     * 产品单价
     */
    private Double price;

    /**
     * 产品图片
     */
    private String img;

    /**
     * 所属商店id
     */
    private Integer shopId;
}
