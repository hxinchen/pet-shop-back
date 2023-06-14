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
 * 	商店表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("shop")
@ApiModel(value = "Shop", description = "商店实体")
public class Shop extends BaseEntity implements Serializable {

    /**
     * 商店名称
     */
    @ApiModelProperty(value = "商店名称")
    private String name;

    /**
     * 商店纬度
     */
    @ApiModelProperty(value = "商店纬度")
    private String latitude;

    /**
     * 商店经度
     */
    @ApiModelProperty(value = "商店经度")
    private String longitude;

    /**
     * 地区码
     */
    @ApiModelProperty(value = "地区码")
    private String areaCode;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    /**
     * 商家id
     */
    @ApiModelProperty(value = "商家id")
    private Integer userId;
}
