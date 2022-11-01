package com.yiran.oss.utis;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yiran.oss.common.ApiException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author 小番茄
 * @Date 2022/11/1 8:58
 */
@Component
public class AliOssUtis {
    /**
     * 封装一个判断方法
     * @param file 入参
     * @return file
     */
    public String uploadOneFile(MultipartFile file) throws ApiException {
            if (!(Objects.requireNonNull(file.getOriginalFilename()).endsWith(".png"))
                    &&!(file.getOriginalFilename().endsWith(".jpg"))){
                throw new ApiException("文件类型错误，只能为png或者jpg");
            }
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI5tAMy22FRh7hqcvs32VT";
        String accessKeySecret = "uQAbRcMdyxKnySQaIS2revJY67XnQe";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //设置文件名
        String fileName = new DateTime().toString("yyyy/MM/dd")
                + UUID.randomUUID().toString().replace("-","")
                +file.getOriginalFilename();
        try {
            //创建PUTObject请求
            String bucketName = "yiran-shop";
            ossClient.putObject(bucketName,fileName,file.getInputStream());
            return "http://" + bucketName +"."+ endpoint +"/"+fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (ossClient != null){
                ossClient.shutdown();
            }
        }
    }
}

