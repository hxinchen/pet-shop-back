package com.example.petshopback.controller;

import com.example.petshopback.entity.Video;
import com.example.petshopback.service.VideoService;
import com.example.petshopback.utils.Result;
import com.example.petshopback.utils.fileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * <p>
 * 视频表 前端控制器
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/modifyUpload")
    public Result modifyUpload(MultipartFile file,Integer petId,String videoName){
        Result result=new Result();
        fileUpload fileUpload=new fileUpload();
        String url= fileUpload.upload(file);
        videoService.modifyPetVideo(petId, url, videoName);
        result.setData(url);
        result.success("修改成功");
        return result;
    }
    @PostMapping("/addUpload")
    public Result addUpload(MultipartFile file, String videoName){
        Result result=new Result();
        fileUpload fileUpload=new fileUpload();
        String url= fileUpload.upload(file);
        result.setData(videoService.addPetVideo(url, videoName));
        result.success("添加成功");
        return result;
    }

    // 更新video
    @PostMapping("/update")
    public Result update(Integer videoId,Integer petId){
        Result result=new Result();
        // 输出videoId
        System.out.println("videoId"+videoId);
        videoService.updateVideo(videoId,petId);
        result.success("更新成功");
        return result;
    }
}
