package com.example.petshopback.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.petshopback.entity.User;
import com.example.petshopback.service.UserService;
import com.example.petshopback.utils.JwtUtil;
import com.example.petshopback.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        Result result=new Result();
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User existUser = userService.getOne(queryWrapper);
        if (existUser != null) {
            result.fail("用户名已存在");
            return result;
        }
        // 设置默认头像
        user.setAvatar("default.png");
        // 设置创建时间
        user.setCreateTime(String.valueOf(LocalDateTime.now()));
        // 设置默认为普通用户
        user.setIsAdmin(false);
        user.setIsBusiness(false);
        // 对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userService.save(user);
        result.success("注册成功");
        return result;
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Result result=new Result();
        String password = user.getPassword();
        // 对密码进行加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        queryWrapper.eq(User::getPassword, password);
        User loginUser = userService.getOne(queryWrapper);
        // 判断用户是否存在
        if (loginUser == null) {
            result.fail("登录失败，用户名或密码错误");
            return result;
        }
        result.success("登录成功");
        // 生成Token
        String token = JwtUtil.generateToken(loginUser.getId().toString());
        Map<String, Object> map = new HashMap<>();
        // 将Token存储到Map中
        user.setPassword(null);
        map.put("token", token);
        map.put("user", loginUser);
        result.setData(map);
        return result;
    }


    /**
     * 修改用户信息
     */
    @PutMapping
    public Result update(HttpServletRequest request, @RequestBody User user) {
        Result result=new Result();
        // 从Session中获取Token
        String token = (String) request.getSession().getAttribute("token");
        // 验证Token的有效性
        String userId = JwtUtil.validateToken(token);
        if (StringUtils.isBlank(userId)) {
            result.fail("Token无效，请重新登录");
            return result;
        }

        // 验证用户权限
        User loginUser = userService.getById(Long.valueOf(userId));
        if (!loginUser.getId().equals(user.getId()) && !loginUser.getIsAdmin()) {
            result.fail("您没有权限修改其他用户的信息");
            return result;
        }

        userService.updateById(user);
        result.success("用户信息修改成功");
        return result;
    }

    /**
     * 根据用户 ID 查询用户信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id, HttpServletRequest request) {
        Result result=new Result();

        // 从Session中获取Token
        String token = (String) request.getSession().getAttribute("token");
        // 验证Token的有效性
        String userId = JwtUtil.validateToken(token);
        if (StringUtils.isBlank(userId)) {
            result.fail("Token无效，请重新登录");
            return result;
        }

        User user = userService.getById(id);
        if (user != null) {
            // 验证用户权限
            if (!user.getId().equals(Long.valueOf(userId)) && !userService.getById(Long.valueOf(userId)).getIsAdmin()) {
                result.fail("您没有权限查询其他用户的信息");
                return result;
            }
            result.setData(user);
            return result;
        }
        result.fail("查询失败，用户不存在");
        return result;
    }

    /**
     * 删除用户信息
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id, HttpServletRequest request) {
        Result result=new Result();
        // 从Session中获取Token
        String token = (String) request.getSession().getAttribute("token");
        // 验证Token的有效性
        String userId = JwtUtil.validateToken(token);
        if (StringUtils.isBlank(userId)) {
            result.fail("Token无效，请重新登录");
            return result;
        }

        // 验证用户权限
        User loginUser = userService.getById(Long.valueOf(userId));
        if (!loginUser.getIsAdmin()) {
            result.fail("您没有权限删除用户信息");
            return result;
        }

        userService.removeById(id);
        result.success("用户信息删除成功");
        return result;
    }
}
