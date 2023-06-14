package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("order_item")
@ApiModel(value = "OrderItem", description = "订单商品实体")
public class OrderItem extends BaseEntity implements Serializable {

    /**
     * 是否宠物0-不是  1-是
     */
    @ApiModelProperty(value = "是否宠物，0--不是，1--是")
    private Boolean isPet;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer count;

    /**
     * 商品id（是产品则去产品表查，是宠物则去宠物表查）
     */
    @ApiModelProperty(value = "商品id")
    private Integer productId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Integer orderId;


    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;
}
