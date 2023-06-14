package com.example.petshopback.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.common.R;
import com.example.petshopback.entity.User;
import com.example.petshopback.service.UserService;
import com.example.petshopback.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public R<String> register(@RequestBody User user) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User existUser = userService.getOne(queryWrapper);
        if (existUser != null) {
            return R.error("用户名已存在");
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
        return R.success("注册成功");
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody User user) {
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        queryWrapper.eq(User::getPassword, password);
        User loginUser = userService.getOne(queryWrapper);

        if (loginUser == null) {
            return R.error("登录失败，用户名或密码错误");
        }

        // 生成Token
        String token = JwtUtil.generateToken(loginUser.getId().toString());
        // 将Token存储到Session中
        request.getSession().setAttribute("token", token);

        return R.success("登录成功");
    }

    /**
     * 用户退出
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 删除Session中存储的Token
        request.getSession().removeAttribute("token");
        return R.success("退出成功");
    }

    /**
     * 修改用户信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody User user) {
        // 从Session中获取Token
        String token = (String) request.getSession().getAttribute("token");
        // 验证Token的有效性
        String userId = JwtUtil.validateToken(token);
        if (StringUtils.isBlank(userId)) {
            return R.error("Token无效，请重新登录");
        }

        // 验证用户权限
        User loginUser = userService.getById(Long.valueOf(userId));
        if (!loginUser.getId().equals(user.getId()) && !loginUser.getIsAdmin()) {
            return R.error("您没有权限修改其他用户的信息");
        }

        userService.updateById(user);
        return R.success("用户信息修改成功");
    }

    /**
     * 根据用户 ID 查询用户信息
     */
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id, HttpServletRequest request) {
        // 从Session中获取Token
        String token = (String) request.getSession().getAttribute("token");
        // 验证Token的有效性
        String userId = JwtUtil.validateToken(token);
        if (StringUtils.isBlank(userId)) {
            return R.error("Token无效，请重新登录");
        }

        User user = userService.getById(id);
        if (user != null) {
            // 验证用户权限
            if (!user.getId().equals(Long.valueOf(userId)) && !userService.getById(Long.valueOf(userId)).getIsAdmin()) {
                return R.error("您没有权限查询其他用户的信息");
            }
            return R.success(user);
        }
        return R.error("查询失败，用户不存在");
    }

    /**
     * 删除用户信息
     */
    @DeleteMapping("/{id}")
    public R<String> deleteById(@PathVariable Long id, HttpServletRequest request) {
        // 从Session中获取Token
        String token = (String) request.getSession().getAttribute("token");
        // 验证Token的有效性
        String userId = JwtUtil.validateToken(token);
        if (StringUtils.isBlank(userId)) {
            return R.error("Token无效，请重新登录");
        }

        // 验证用户权限
        User loginUser = userService.getById(Long.valueOf(userId));
        if (!loginUser.getIsAdmin()) {
            return R.error("您没有权限删除用户信息");
        }

        userService.removeById(id);
        return R.success("用户信息删除成功");
    }
}
