package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("`order`")
@ApiModel(value = "Order", description = "订单实体")
public class Order extends BaseEntity implements Delayed {

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 总价
     */
    @ApiModelProperty(value = "总价")
    private Double sumPrice;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Integer userId;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态，0--待付款，1--待发货，2--待收货，3--待评价，4--已完成，5--已取消")
    private Integer status;

    /**
     * 取消时间
     */
    @ApiModelProperty(value = "取消时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date cancelTime;

    /**
     * 取消原因
     */
    @ApiModelProperty(value = "取消原因")
    private String cancelReason;

    /**
     * 商家id
     */
    @ApiModelProperty(value = "商家id")
    private Integer businessId;

    /**
     * 地址id
     */
    @ApiModelProperty(value = "地址id")
    private Integer addressId;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(cancelTime.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return cancelTime.compareTo(((Order) o).getCancelTime());
    }
}
