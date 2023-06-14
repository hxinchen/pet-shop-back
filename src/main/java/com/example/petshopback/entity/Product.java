package com.example.petshopback.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 产品表（商品，周边）
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("product")
@ApiModel(value = "Product", description = "产品实体")
public class Product extends BaseEntity implements Serializable {

    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private Integer categoryId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String name;

    /**
     * 产品描述
     */
    @ApiModelProperty(value = "产品描述")
    private String description;

    /**
     * 产品单价
     */
    @ApiModelProperty(value = "产品单价")
    private Double price;

    /**
     * 产品图片
     */
    @ApiModelProperty(value = "产品图片")
    private String img;

    /**
     * 所属商店id
     */
    @ApiModelProperty(value = "所属商店id")
    private Integer shopId;

    /**
     * 产品库存
     */
    @ApiModelProperty(value = "产品库存")
    private Integer stock;
}
