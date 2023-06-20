package com.example.petshopback.service;

import com.example.petshopback.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 视频表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface VideoService extends IService<Video> {
    public void addPetVideo(Integer petId,String url,String name);
}
