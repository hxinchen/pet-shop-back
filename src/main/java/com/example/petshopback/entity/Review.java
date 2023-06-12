package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评价表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("review")
@ApiModel(value = "Review", description = "评价实体")
public class Review extends BaseEntity implements Serializable {

    /**
     * 评价对应的商店id
     */
    @ApiModelProperty(value = "评价对应的商店id")
    private Integer shopId;

    /**
     * 评论
     */
    @ApiModelProperty(value = "评论")
    private String comment;

    /**
     * 评分0-5
     */
    @ApiModelProperty(value = "评分，取值范围为0-5")
    private Double rate;

    /**
     * 发出评价的用户
     */
    @ApiModelProperty(value = "发出评价的用户id")
    private Integer userId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    /**
     * 发出评价的时间
     */
    @ApiModelProperty(value = "发出评价的时间")
    private String createTime;
}
