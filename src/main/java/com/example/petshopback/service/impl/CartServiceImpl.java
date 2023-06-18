package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.petshopback.common.R;
import com.example.petshopback.entity.Cart;
import com.example.petshopback.entity.CartVO;
import com.example.petshopback.entity.ShopCart;
import com.example.petshopback.mapper.CartMapper;
import com.example.petshopback.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<ShopCart> getAll() {

        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
        List<CartVO> cartItems = cartMapper.getCartByUserId(Integer.valueOf(userId));
        Map<String, ShopCart> uniqueShops = new LinkedHashMap<>();

        for (CartVO cartItem : cartItems) {
            String shopName = cartItem.getShopName();
            if (uniqueShops.containsKey(shopName)) {
                // 如果该商店已存在于 uniqueShops 中，则获取对应的 ShopCart 对象
                ShopCart shop = uniqueShops.get(shopName);
                // 将当前商品添加到该商店的商品列表中
                shop.getProducts().add(cartItem);
                shop.setProductNum(shop.getProductNum() + 1); // 每添加一个商品，数量加一
            } else {
                // 如果该商店尚未存在于 uniqueShops 中，则创建一个新的 ShopCart 对象
                ShopCart shopCart = new ShopCart();
                shopCart.setShopName(shopName);
                List<CartVO> products = new ArrayList<>();
                products.add(cartItem);
                shopCart.setProducts(products);
                shopCart.setProductNum(1); // 第一个商品，数量为1
                // 将新的 ShopCart 对象添加到 uniqueShops 中
                uniqueShops.put(shopName, shopCart);
            }
        }
        return new ArrayList<>(uniqueShops.values());

    }

    @Override
    public Cart add(Integer productId) {
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Integer.valueOf(userId)).eq("product_id", productId);
        Cart cart = this.getOne(queryWrapper);
        if (cart != null) {
            cart.setCount(cart.getCount() + 1);
            this.updateById(cart);
        } else {
            cart = new Cart();
            cart.setUserId(Integer.valueOf(userId));
            cart.setProductId(productId);
            cart.setCount(1);
            //默认不选中
            cart.setChecked(false);
            this.save(cart);
        }
        return cart;
    }

    @Override
    public boolean check(Integer productId) {
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Integer.valueOf(userId)).eq("product_id", productId);
        Cart cart = this.getOne(queryWrapper);
        if (cart != null) {
            cart.setChecked(!cart.getChecked());
            this.updateById(cart);
            return true;
        }
        return false;
    }

    @Override
    public Cart sub(Integer productId) {
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Integer.valueOf(userId)).eq("product_id", productId);
        Cart cart = this.getOne(queryWrapper);
        cart.setCount(cart.getCount()-1);
        this.updateById(cart);
        return cart;
    }

    @Override
    public boolean delete(Integer productId) {
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Integer.valueOf(userId)).eq("product_id", productId);
        Cart cart = this.getOne(queryWrapper);
        if (cart != null) {
            this.removeById(cart.getId());
            return true;
        }
        return false;
    }
    @Override
    public List<Cart> getCartByUserId() {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        String token = request.getHeader("token");
//        System.out.println("token" +token);
        String userId = JwtUtil.validateToken(token);
        queryWrapper.eq("user_id", Integer.valueOf(userId));

        return this.list(queryWrapper);
    }
    @Override
    public Boolean updateAll(String ids) {
        String token = request.getHeader("token");
//        System.out.println("token" + token);
        String userId = JwtUtil.validateToken(token);
        String[] array = ids.split(",");
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        List<String> list = new ArrayList<>();
        for (String id:array) {
            list.add(id);
        }
        queryWrapper.in("product_id", list).eq("user_id", Integer.valueOf(userId)).eq("checked", 0);
        List<Cart> cartList = this.list(queryWrapper);
        Boolean allCheck = true;
        if (cartList.size() != 0)
            allCheck = false;
        if (allCheck) {
            List<Cart> list1 = this.getCartByUserId();
            for (Cart cart: list1) {
                cart.setChecked(false);
                this.updateById(cart);
            }
        }
        else {
            for (Cart cart: cartList) {
                cart.setChecked(true);
                this.updateById(cart);
            }
        }

        return true;
    }
}
