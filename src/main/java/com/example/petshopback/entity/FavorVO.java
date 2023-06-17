package com.example.petshopback.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FavorVO extends Favor{
    private String username;
    private String petName;
    private Double petPrice;
    private String petImg;
    private String productName;
    private Double productPrice;
    private String productImg;


}
