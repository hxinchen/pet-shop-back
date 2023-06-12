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
 * 宠物类别表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("pet_category")
@ApiModel(value = "PetCategory", description = "宠物类别实体")
public class PetCategory extends BaseEntity implements Serializable {

    /**
     * 宠物类别名称
     */
    @ApiModelProperty(value = "宠物类别名称")
    private String name;

    /**
     * 是否可用0--不可用 1--可用
     */
    @ApiModelProperty(value = "是否可用，0--不可用，1--可用")
    private Boolean useful;

    /**
     * 图片url
     */
    @ApiModelProperty(value = "图片url")
    private String img;

}
