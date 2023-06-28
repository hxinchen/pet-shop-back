package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public Video addPetVideo(String url, String name) {
        Video video=new Video();
        video.setVideoUrl(url);
        video.setVideoName(name);
        this.save(video);
        return video;
    }

    @Override
    public void modifyPetVideo(Integer petId, String url, String name) {
        // 根据petId修改video
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pet_id", petId);
        Video video = this.getOne(queryWrapper);
        video.setVideoUrl(url);
        video.setVideoName(name);
        this.updateById(video);
    }

    @Override
    public void updateVideo(Integer videoId, Integer petId) {
        Video video = this.getById(videoId);
        video.setPetId(petId);
        this.updateById(video);
    }

    @Override
    public Video getVideoByPetId(Integer id) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pet_id", id);
        return this.getOne(queryWrapper);
    }
}
