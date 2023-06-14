package com.example.petshopback.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

// 分页查询结果类

/**
 * <p>
 * 分页查询结果类
 * </p>
 *
 * @param <T> 记录类型
 */

@Getter
@Setter
@ApiModel(value = "Data", description = "分页查询结果类")
public class Data<T> implements Serializable {

    private List<T> records;  // 记录列表

    private int count;        // 记录数

    private Long total;        // 总记录数

    private Long pages;        // 总页数

    private Long current;      // 当前页码

}

