package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 	商店表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-08 07:25:48
 */
@Getter
@Setter
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String shopName;

    /**
     * 商店纬度
     */
    private String latitude;

    /**
     * 商店经度
     */
    private String longitude;

    /**
     * 地区码
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 商家id
     */
    private Integer userId;
}
