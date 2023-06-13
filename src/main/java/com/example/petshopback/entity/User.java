package com.example.petshopback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Getter
@Setter
@TableName("user")
@ApiModel(value = "User", description = "用户实体")
public class User extends BaseEntity implements Serializable {

    /**
     * 用户名，用于登录，同时也是用户在软件中公开显示的昵称
     */
    @ApiModelProperty(value = "用户名，用于登录，同时也是用户在软件中公开显示的昵称")
    private String username;

    /**
     * 密码，用于登录
     */
    @ApiModelProperty(value = "密码，用于登录")
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 性别-0--女--1--男
     */
    @ApiModelProperty(value = "性别：0表示女，1表示男")
    private Byte sex;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 是否管理员0-不是--1-是
     */
    @ApiModelProperty(value = "是否管理员：0表示不是，1表示是")
    private Boolean isAdmin;

    /**
     * 是否商家0-不是--1-是
     */
    @ApiModelProperty(value = "是否商家：0表示不是，1表示是")
    private Boolean isBusiness;
}
