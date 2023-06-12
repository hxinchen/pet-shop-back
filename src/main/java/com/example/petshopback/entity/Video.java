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
 * 视频表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("video")
@ApiModel(value = "Video", description = "视频实体")
public class Video extends BaseEntity implements Serializable {

    /**
     * 宠物id
     */
    @ApiModelProperty(value = "宠物id")
    private Integer petId;

    /**
     * 视频链接
     */
    @ApiModelProperty(value = "视频链接")
    private String videoUrl;

    /**
     * 视频名称
     */
    @ApiModelProperty(value = "视频名称")
    private String videoName;
}