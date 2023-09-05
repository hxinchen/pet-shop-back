package com.example.petshopback.service;

import com.example.petshopback.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 视频表 服务类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
public interface VideoService extends IService<Video> {
    public Video addPetVideo(String url,String name);
    public void modifyPetVideo(Integer petId,String url,String name);
    //根据id查询视频

    void updateVideo(Integer videoId, Integer petId);

    Video getVideoByPetId(Integer id);
}
