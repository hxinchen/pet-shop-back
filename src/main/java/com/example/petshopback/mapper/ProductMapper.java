package com.example.petshopback.mapper;

import com.example.petshopback.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 产品表（商品，周边） Mapper 接口
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
