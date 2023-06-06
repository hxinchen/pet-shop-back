package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 视频表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:55:13
 */
@Getter
@Setter
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 宠物id
     */
    private Integer petId;

    /**
     * 视频链接
     */
    private String videoUrl;

    /**
     * 视频名称
     */
    private String videoName;
}
