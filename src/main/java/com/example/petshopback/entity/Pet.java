package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 宠物表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:52:52
 */
@Getter
@Setter
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类别id
     */
    private Integer categoryId;

    /**
     * 宠物名字（昵称）
     */
    private String name;

    /**
     * 宠物年龄
     */
    private Integer age;

    /**
     * 宠物描述
     */
    private String describe;

    /**
     * 品种
     */
    private String breed;

    /**
     * 出生年月日
     */
    private LocalDate birthday;

    /**
     * 健康情况
     */
    private String health;

    /**
     * 价格
     */
    private Double price;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 图片url
     */
    private String img;
}
