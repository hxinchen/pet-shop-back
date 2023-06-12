package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 产品（商品）类别
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("product_category")
@ApiModel(value = "ProductCategory", description = "产品类别实体")
public class ProductCategory extends BaseEntity implements Serializable {

    /**
     * 产品类别名称
     */
    @ApiModelProperty(value = "产品类别名称")
    private String name;

    /**
     * 是否可用0--不可用 1--可用
     */
    @ApiModelProperty(value = "是否可用，0--不可用，1--可用")
    private Boolean useful;
}
