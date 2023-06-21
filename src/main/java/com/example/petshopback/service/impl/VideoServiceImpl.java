package com.example.petshopback.service.impl;

import com.example.petshopback.entity.Video;
import com.example.petshopback.mapper.VideoMapper;
import com.example.petshopback.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频表 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Override
    public void addPetVideo(Integer petId, String url, String name) {
        Video video=new Video();
        video.setPetId(petId);
        video.setVideoUrl(url);
        video.setVideoName(name);
        this.save(video);
    }

    @Override
    public Video getVideoById(Integer id) {
        return this.getById(id);
    }
}
