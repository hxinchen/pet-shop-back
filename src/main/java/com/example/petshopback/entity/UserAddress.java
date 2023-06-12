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
 * 用户地址表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("user_address")
@ApiModel(value = "UserAddress", description = "用户地址实体")
public class UserAddress extends BaseEntity implements Serializable {

    /**
     * 地址对应的用户id
     */
    @ApiModelProperty(value = "地址对应的用户id")
    private Integer userId;

    /**
     * 是否默认地址0-不是--1-是
     */
    @ApiModelProperty(value = "是否默认地址：0表示不是，1表示是")
    private Boolean isDefault;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    /**
     * 地区码
     */
    @ApiModelProperty(value = "地区码")
    private String areaCode;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String detail;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String tag;
}
