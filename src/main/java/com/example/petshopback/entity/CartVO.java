package com.example.petshopback.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartVO extends Cart{
    private String username;
    private String productName;
    private Double productPrice;
    private String productImg;
}
