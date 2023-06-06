package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-06 01:54:45
 */
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名，用于登录，同时也是用户在软件中公开显示的昵称
     */
    private String username;

    /**
     * 密码，用于登录
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别-0--女--1--男
     */
    private Byte sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private String createaTime;

    /**
     * 是否管理员0-不是--1-是
     */
    private Boolean isAdmin;
}
