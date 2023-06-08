package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户地址表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-08 07:25:48
 */
@Getter
@Setter
@TableName("user_address")
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地址唯一id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 地址对应的用户id
     */
    private Integer userId;

    /**
     * 是否默认地址0-不是--1-是
     */
    private Boolean isDefault;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 地区码
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 标签
     */
    private String tag;
}
