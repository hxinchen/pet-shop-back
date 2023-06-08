package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-08 07:25:48
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
     * 会员id
     */
    private Integer userId;

    /**
     * 订单状态
     */
    private Byte status;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 商家id
     */
    private Integer businessId;

    /**
     * 地址id
     */
    private Integer addressId;
}
