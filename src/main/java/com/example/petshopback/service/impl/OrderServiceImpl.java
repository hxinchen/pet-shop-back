package com.example.petshopback.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Order;
import com.example.petshopback.entity.OrderItem;
import com.example.petshopback.mapper.OrderMapper;
import com.example.petshopback.service.OrderItemService;
import com.example.petshopback.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.DateTool;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.DelayQueue;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OrderItemService orderItemService;
    //    private StringRedisTemplate redisTemplate;
    //是否自动取消订单
    private int isStarted = 1;
    private DelayQueue<Order> queue = new DelayQueue<>();
    @Resource
    private ThreadPoolTaskExecutor executorService;


    // 加到延时队列
    @Override
    public DelayQueue<Order> pushOrder(Order order){
        executorService.submit(()->{
            try {
                queue.add(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return queue;
    }

    @Override
    public Page<Order> getAll(Integer pageNum, Integer pageSize) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Order> getByStatus(Integer status) {
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
        System.out.println(userId);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();

        if(status == 0){//0-全部订单
            queryWrapper.eq("user_id", Integer.valueOf(userId));
        }
        else {//其他状态：1-待支付，2-待发货，3-待收货，4-待评价，5-已取消
            queryWrapper.eq("user_id", Integer.valueOf(userId)).eq("status", status);
        }
        return this.list(queryWrapper);
    }

    @Override
    public Order add(Double sumPrice, Integer isPay, Integer addressId) throws ParseException {
        Order order = new Order();
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);

        order.setAddressId(addressId);
        order.setSumPrice(sumPrice);
        order.setCreateTime(DateTool.getCurrTime());
        order.setUserId(Integer.valueOf(userId));
        order.setNo(String.valueOf(UUID.randomUUID()));

        //订单状态
        if (isPay == 1){//支付
            order.setStatus(2);
        }
        else if (isPay == 0) {//未支付
            order.setStatus(1);
            Date date = DateUtil.parse(order.getCreateTime());
//            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
            //自动取消时间5分钟
            order.setCancelTime(DateUtil.offset(date, DateField.MINUTE, 2));
            this.pushOrder(order);
            this.cancelOrder();
        }
        this.save(order);



        return order;
    }

    @Override
    public void cancelOrder() {
        //新建一个线程，用来模拟定时取消订单
        executorService.submit(()->{
            try {
                System.out.println("开启自动取消订单,当前时间：" + DateTool.getCurrTime());
                while (isStarted == 1 && !queue.isEmpty()) {
                    try {
                        Order order = queue.take();
                        this.cancel(order.getId(), "超时自动取消");
                        List<OrderItem> list = orderItemService.getByOrderId(order.getId());
                        for (OrderItem OrderItem: list) {//更新该订单下的所有订单详情状态
                            OrderItem.setStatus(5);
                            orderItemService.updateById(OrderItem);
                        }
//                        queue.remove(order);
                        System.out.println("订单：" + order.getNo() + "付款超时，自动取消，当前时间：" + DateTool.getCurrTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    // 取消订单
    @Override
    public Order cancel(Integer orderId, String reason) {
//        String token = request.getHeader("Authorization");
////        System.out.println("token" + token);
//        String userId = JwtUtil.validateToken(token);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        Order order = this.getOne(queryWrapper);
        if (order != null) {
            order.setStatus(5);//取消订单
            order.setCancelTime(new Date());
            order.setCancelReason(reason);
            this.updateById(order);
        }
        return order;
    }
}
