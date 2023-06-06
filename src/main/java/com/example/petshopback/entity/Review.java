package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评价表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:54:17
 */
@Getter
@Setter
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评价对应的商店id
     */
    private Integer shopId;

    /**
     * 评论
     */
    private String comment;

    /**
     * 评分0-5
     */
    private Double rate;

    /**
     * 发出评价的用户
     */
    private Integer userId;

    /**
     * 订单id
     */
    private Integer orderId;
}
