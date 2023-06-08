package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 宠物类别表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-08 07:25:48
 */
@Getter
@Setter
@TableName("pet_category")
public class PetCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 宠物类别名称
     */
    private String name;

    /**
     * 是否可用0--不可用 1--可用
     */
    private Boolean useful;
}
