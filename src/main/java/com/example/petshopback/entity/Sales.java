package com.example.petshopback.entity;

import lombok.Data;
import lombok.ToString;

import java.text.DecimalFormat;

@Data
@ToString
public class Sales extends Shop{
    private double sales;
}
