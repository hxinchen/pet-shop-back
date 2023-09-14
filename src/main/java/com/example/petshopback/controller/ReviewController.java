package com.example.petshopback.controller;

import com.example.petshopback.entity.Review;
import com.example.petshopback.service.ProductService;
import com.example.petshopback.service.ReviewService;
import com.example.petshopback.service.UserService;
import com.example.petshopback.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 评价表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    //新增评论
    @PostMapping("/add")
    public Result add( int shopId,String comment,int rate,int orderItemId, int isPet, int orderId){
        Result result =new Result();
        Review review =new Review();
        review.setShopId(shopId);
        review.setComment(comment);
        review.setRate(rate);
        review.setOrderItemId(orderItemId);
        review.setIsPet(isPet);
        reviewService.add(review,orderId);

        result.success("评论成功");
       return result;
    }
    //删除评论
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        Result result =new Result();
        reviewService.delete(id);

        result.success("删除评论成功");
        return result;
    }
    //获取评论
    @GetMapping("/getByShopId")
    public Result getByShopId(int shopId,int productId){
        Result result =new Result();
        result.setData(reviewService.getByShopId(shopId,productId));

        return result;
    }
    //评论点赞
//    @PostMapping("/like")
//    public Result like(int id){
//        Result result =new Result();
//        reviewService.like(id);
//        result.success("点赞成功");
//        return result;
//    }
}
