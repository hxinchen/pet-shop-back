package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:52:14
 */
@Getter
@Setter
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 总价
     */
    private Double sumPrice;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 订单状态
     */
    private Byte status;
}
