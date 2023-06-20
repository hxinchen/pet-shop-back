package com.example.petshopback.controller;

import com.example.petshopback.service.VideoService;
import com.example.petshopback.utils.Result;
import com.example.petshopback.utils.fileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/upLoad")
    public Result upLoad(MultipartFile file,Integer petId,String videoName){
        Result result=new Result();
        fileUpload fileUpload=new fileUpload();
        String url= fileUpload.upload(file);
        videoService.addPetVideo(petId,url,videoName);
        result.setData(url);
        result.success("上传成功");
        return result;
    }
}
