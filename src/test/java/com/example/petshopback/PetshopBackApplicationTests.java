package com.example.petshopback;

import com.example.petshopback.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class PetshopBackApplicationTests {

    @Resource
    private RedisTemplate redisTemplate=new RedisTemplate<>();

    @Test
    void contextLoads() {
//        连接redis


        //        redis存数
        redisTemplate.opsForValue().set("name","zhangsan");
        System.out.println(redisTemplate.opsForValue().get("name"));
//        redis存哈希
        redisTemplate.opsForHash().put("user","name","zhangsan");
        redisTemplate.opsForHash().put("user","age","18");
        System.out.println(redisTemplate.opsForHash().get("user","name"));
        System.out.println(redisTemplate.opsForHash().get("user","age"));
//        redis显示全部数据
        System.out.println(redisTemplate.opsForHash().entries("user"));
    }

    public static void main(String[] args) {
        PetshopBackApplicationTests petshopBackApplicationTests = new PetshopBackApplicationTests();
        petshopBackApplicationTests.contextLoads();

    }

}
