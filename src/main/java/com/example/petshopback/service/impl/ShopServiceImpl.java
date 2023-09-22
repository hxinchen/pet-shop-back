package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.*;
import com.example.petshopback.mapper.ShopMapper;
import com.example.petshopback.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 	商店表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    @Lazy
    private OrderItemService orderItemService;
    @Autowired
    @Lazy
    private OrderService orderService;
    @Autowired
    @Lazy
    private PetService petService;
    @Autowired
    @Lazy
    private ProductService productService;


    @Override
    public boolean addShop(Shop shop) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", shop.getName());
        Shop isExist = getOne(queryWrapper);
        if (isExist != null) {
            System.out.println("商店已存在，新增商店失败");
            return false;
        }
        Shop newShop = new Shop();

        // 根据area和detailAddress获取经纬度
//        String address = shop.getArea() + shop.getDetailAddress();
        newShop.setArea(shop.getArea());
        newShop.setDetailAddress(shop.getDetailAddress());
        newShop.setName(shop.getName());
        newShop.setLatitude(shop.getLatitude());
        newShop.setLongitude(shop.getLongitude());
        // 解析token
        String token = request.getHeader("token");
        String userId = JwtUtil.validateToken(token);
        newShop.setUserId(Integer.valueOf(userId));
        save(newShop);
        return true;
    }

    @Override
    public Page<Shop> getAllShop(Integer pageNum, Integer pageSize) {
        Page<Shop> page = new Page<>(pageNum, pageSize);
        return this.page(page);
    }

    @Override
    public List<Shop> getShop() {
        String token = request.getHeader("token");

        String userId = JwtUtil.validateToken(token);
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", Integer.valueOf(userId));
        queryWrapper.eq("user_id", userId);
        return this.list(queryWrapper);
    }

    @Override
    public Double getSalesByTime(Integer shopId, String startTime, String endTime,Integer isPet) {
        Double sales = 0.0;
        Double price=0.0;

        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        //统计status小于等于5的订单
        queryWrapper.le("status", 5).gt("status", 1);
        //refund_time为空
        queryWrapper.isNull("refund_time");
        if(isPet!=-1){
            queryWrapper.eq("is_pet",isPet);
        }
        List<OrderItem> orderItemList = orderItemService.list(queryWrapper);
        for (OrderItem orderItem : orderItemList) {
            Order order= orderService.getById(orderItem.getOrderId());
            //统计createTime在startTime和endTime之间的订单
            if (order.getCreateTime().compareTo(startTime) >= 0 && order.getCreateTime().compareTo(endTime) <= 0) {
                //如果是宠物
                if(orderItem.getIsPet()){
                    price= petService.getById(orderItem.getProductId()).getPrice();
                }else {
                    price= productService.getById(orderItem.getProductId()).getPrice();
                }
                sales += price * orderItem.getCount();
            }
        }
        return sales;
    }

    @Override
    public Integer getSalesCountByTime(Integer shopId, String startTime, String endTime, Integer isPet) {
        Integer salesCount = 0;
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        //统计status小于等于5的订单
        queryWrapper.le("status", 5).gt("status", 1);
        //refund_time为空
        queryWrapper.isNull("refund_time");
        List<OrderItem> orderItemList = orderItemService.list(queryWrapper);
        for (OrderItem orderItem : orderItemList) {
            Order order= orderService.getById(orderItem.getOrderId());
            //统计createTime在startTime和endTime之间的订单
            if (order.getCreateTime().compareTo(startTime) >= 0 && order.getCreateTime().compareTo(endTime) <= 0) {
                salesCount += orderItem.getCount();
            }
        }
        return salesCount;
    }

    @Override
    public Integer getAccessCount(Integer shopId,Integer isPet) {
        Integer accessCount = 0;
        Integer petAccessCount = 0;
        Integer productAccessCount = 0;

        List<Pet> petList = petService.getPetByShopId(shopId);
        List<Product> productList = productService.getProductByShopId(shopId);
        if (isPet != 0) {
            for (Pet pet : petList) {
                petAccessCount += pet.getAccessCount();
            }
        }
        if (isPet != 1) {
            for (Product product : productList) {
                productAccessCount += product.getAccessCount();
            }
        }
        //如果isPet为-1，统计宠物和商品的访问量
        accessCount = petAccessCount + productAccessCount;
        return accessCount;
    }
}
