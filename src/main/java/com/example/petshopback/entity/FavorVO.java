package com.example.petshopback.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FavorVO extends Favor{
    private String username;
    private String favorName;
    private Double favorPrice;
    private String favorImg;
    private String favorShopName;
    private String favorBreed;

}
