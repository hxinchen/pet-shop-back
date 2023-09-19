package com.example.petshopback;

import com.example.petshopback.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class PetshopBackApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void contextLoads() {

        //存一个键值对
        redisUtils.set("name", "zhangsan");
        //取出键为name的值
        System.out.println(redisUtils.get("name"));
    }

    public static void main(String[] args) {
        // 启动Spring Boot应用程序并获取应用程序上下文
        ConfigurableApplicationContext context = SpringApplication.run(PetshopBackApplication.class, args);

        // 获取redisTemplate Bean
        RedisTemplate<String, Object> redisTemplate = context.getBean(RedisTemplate.class);

        // 关闭应用程序上下文
        context.close();
    }
}
