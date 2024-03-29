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
     * 商店id
     */
    @ApiModelProperty(value = "商店id")
    private Integer shopId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 退款原因
     */
    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    /**
     * 退款时间
     */
    @ApiModelProperty(value = "退款时间")
    private String refundTime;

    /**
     * 退款审核状态 0-未审核 1-审核通过 2-审核不通过
     */
    @ApiModelProperty(value = "退款审核状态")
    private Integer refundStatus;
}
