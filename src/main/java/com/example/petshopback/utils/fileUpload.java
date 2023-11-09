package com.example.petshopback.utils;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
public class fileUpload {

    private String endpoint="xxx";

    private String accessKeyId="xxx";

    private String secret="xxx";

    private String bucket="xxx";

    public String upload(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写
        String endpoint = this.endpoint;
        // 阿里云账号AccessKey
        String accessKeyId = this.accessKeyId;
        String accessKeySecret = this.secret;
        // 填写Bucket名称
        String bucketName = this.bucket;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            InputStream inputStream= file.getInputStream();
            String fileName=file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //按照当前日期，创建文件夹，上传到创建文件夹里面
            //  2022/03/15/xx.jpg
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            fileName = "pet_shop/"+ timeUrl+"/"+fileName;
            // 调用方法实现上传
            ossClient.putObject(bucketName,fileName,inputStream);

            // 关闭ossclient
            ossClient.shutdown();

            // 上传之后文件路径
            String url="https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }
}