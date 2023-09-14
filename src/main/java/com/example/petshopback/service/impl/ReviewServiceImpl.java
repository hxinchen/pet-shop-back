package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petshopback.entity.Review;
import com.example.petshopback.mapper.ReviewMapper;
import com.example.petshopback.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.DateTool;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评价表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PetService petService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public void add(Review review, int orderId) {
        String token = request.getHeader("token");
        String userId = JwtUtil.validateToken(token);
        review.setCreateTime(DateTool.getCurrTime());
        review.setLikes(0);
        review.setUserId(Integer.parseInt(userId));
        //修改订单状态 未评价4->已评价5
        orderItemService.update(orderId,review.getOrderItemId(), 4);

        this.save(review);

    }

    @Override
    public void delete(int id) {
        this.removeById(id);
    }

    @Override
    public void like(int id) {
        Review review = this.getById(id);
        review.setLikes(review.getLikes()+1);
        this.updateById(review);
    }

    @Override
    public List<Review> getByShopId(int shopId,int productId) {
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id",shopId);
        List<Review> reviews = this.list(queryWrapper);
        List<Review> newReviews=new ArrayList<>();
        for (Review review : reviews) {
            String username = userService.getById(review.getUserId()).getUsername();
            String avatar = userService.getById(review.getUserId()).getAvatar();
            //取商品信息
            int pId =  orderItemService.getById(review.getOrderItemId()).getProductId();
            boolean isPet = orderItemService.getById(review.getOrderItemId()).getIsPet();

            //如果是0，说明是店铺评价，不是商品评价
            if((productId==pId&&!isPet)||productId==0){
//                筛选后的数据
                newReviews.add(review);
                if(orderItemService.getById(review.getOrderItemId()).getIsPet()){
                    String petName= petService.getById(pId).getName();
                    review.put("productName",petName);
                }else {
                    String productName= productService.getById(pId).getName();
                    review.put("productName",productName);
                }
                int count= orderItemService.getById(review.getOrderItemId()).getCount();
                review.put("count",count);
                review.put("username",username);
                review.put("avatar",avatar);
            }
        }
        return newReviews;
    }
}
