package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 宠物表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("pet")
@ApiModel(value = "Pet", description = "宠物实体")
public class Pet extends BaseEntity implements Serializable {

    /**
     * 类别id
     */
    @ApiModelProperty(value = "类别id")
    private Integer categoryId;

    /**
     * 宠物名字（昵称）
     */
    @ApiModelProperty(value = "宠物名称")
    private String name;

    /**
     * 宠物年龄
     */
    @ApiModelProperty(value = "宠物年龄")
    private Integer age;

    /**
     * 宠物描述
     */
    @ApiModelProperty(value = "宠物描述")
    private String describe;

    /**
     * 品种
     */
    @ApiModelProperty(value = "品种")
    private String breed;

    /**
     * 出生年月日
     */
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    /**
     * 健康情况
     */
    @ApiModelProperty(value = "健康情况")
    private String health;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Double price;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 图片url
     */
    @ApiModelProperty(value = "图片url")
    private String img;

    /**
     * 所属店商店id
     */
    @ApiModelProperty(value = "所属店商店id")
    private Integer shopId;

    /**
     * 视频id
     */
    @ApiModelProperty(value = "视频id")
    private Integer videoId;
}
