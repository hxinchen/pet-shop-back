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
import com.example.petshopback.service.ProductService;
import com.example.petshopback.utils.DateTool;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
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

    @Autowired
    private ProductService productService;

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
        // 按照表中的createTime降序排列
        queryWrapper.orderByDesc("create_time");
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
        else {//其他状态：1-待付款2-待发货3-待收货4-待评价5-已评价6-已取消7-已退款
            queryWrapper.eq("user_id", Integer.valueOf(userId)).eq("status", status);
        }
        return this.list(queryWrapper);
    }

    @Override
    public List<Order> getByUserId() {
        String token = request.getHeader("token");
        String userId = JwtUtil.validateToken(token);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Integer.valueOf(userId));
        return this.list(queryWrapper);
    }

    @Override
    public void deleteByIds(String ids) {//根据Id删除
        //        System.out.println(ids);
        List<String> list = new ArrayList<>();
        String[] array = ids.split(",");
        for (String i:array) {
            list.add(i);
        }
        this.removeByIds(list);
    }

    // 更新订单
    @Override
    public Order updateOrder(Integer orderId, Integer status) {
        String token = request.getHeader("token");
//        System.out.println("token" + token);
//        String userId = JwtUtil.validateToken(token);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);

        Order order = this.getOne(queryWrapper);

            if (order.getStatus() == 1) {//继续付款，从超时队列里删除
                order.setCancelTime(null);
                for (Order q:queue)
                    if (q.getId().equals(orderId))
                        queue.remove(q);
                // 将对应全部订单详情状态改为待发货--2
                List<OrderItem> list = orderItemService.getByOrderId(order.getId());
                for (OrderItem OrderItem: list) {//更新该订单下的所有订单详情状态
                    orderItemService.update(orderId, OrderItem.getProductId(), 1);
                }


            }
        order.setStatus(status+1);

            if (status == 7) { // 退款
                order.setStatus(7);
                order.setRefundTime(DateTool.getCurrTime());
            }

        this.updateById(order);
        return order;
    }

    @Override
    public Order refund(Integer orderId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("id", orderId);

        Order order = this.getOne(queryWrapper);
        order.setStatus(7);

        this.updateById(order);
        return order;
    }

    @Override
    public Order updateStatus(Integer orderId) {
        Order order = this.getById(orderId);
        order.setRefundTime(DateTool.getCurrTime());
        this.updateById(order);
        return order;
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
                        // 修改为之前的状态,库存,订单状态
//                        this.modify(order.getId());
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

    @Override
    public void modify(Integer orderId) {
        List<OrderItem> list = orderItemService.getByOrderId(orderId);
        // 将库存加回去
        StringJoiner joinerIds = new StringJoiner(",");
        StringJoiner joinerIsPets = new StringJoiner(",");
        StringJoiner joinerCounts = new StringJoiner(",");
        for (OrderItem orderItem: list) {
            //更新该订单下的所有订单详情状态
            orderItem.setStatus(5);
            orderItemService.updateById(orderItem);

            joinerIds.add(String.valueOf(orderItem.getProductId()));
            // count转为负数加回去
            joinerCounts.add(String.valueOf(-orderItem.getCount()));

            // isPet为Boolean类型，转为0/1放入字符串
            if (orderItem.getIsPet())
                joinerIsPets.add("1");
            else
                joinerIsPets.add("0");
        }
        productService.modifyStockByIds(joinerIds.toString(), joinerIsPets.toString(), joinerCounts.toString());
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

            // 若手动取消订单，从超时队列里删除
            for (Order q:queue)
                if (q.getId().equals(orderId))
                    queue.remove(q);
            // 库存加回去
            this.modify(orderId);
        }
        return order;
    }
}
