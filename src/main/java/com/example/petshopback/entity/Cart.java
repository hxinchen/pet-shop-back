package com.example.petshopback.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("cart")
@ApiModel(value = "Cart", description = "购物车实体")
public class Cart extends BaseEntity implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Integer productId;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer count;

    /**
     * 是否选中
     */
    @ApiModelProperty(value = "是否选中")
    private Boolean checked;
}
