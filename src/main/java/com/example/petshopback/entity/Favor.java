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
 * 收藏表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("favor")
@ApiModel(value = "Favor", description = "收藏实体")
public class Favor extends BaseEntity implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 宠物id
     */
    @ApiModelProperty(value = "宠物id")
    private Integer petId;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private Integer productId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
