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
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String no;

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
     * 1可以取消
     * 2-3-4可以退款
     */
    @ApiModelProperty(value = "订单状态:1-待付款2-待发货3-待收货4-待评价5-已评价6-已取消7-退款")
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
     * 地址id
     */
    @ApiModelProperty(value = "地址id")
    private Integer addressId;

    /**
     * 最后一个订单详情退款时间
     */
    @ApiModelProperty(value = "最后一个订单详情退款时间")
    private String refundTime;


    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(cancelTime.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return cancelTime.compareTo(((Order) o).getCancelTime());
    }
}
