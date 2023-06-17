package com.example.petshopback.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartVO extends Cart{

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "商品名")
    private String productName;

    @ApiModelProperty(value = "商品价格")
    private Double productPrice;

    @ApiModelProperty(value = "商品图")
    private String productImg;
}
