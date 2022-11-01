package com.yiran.oss.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
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
    @Value(value = "${aliyun.oss.accessKeyId}")
    public String accessKeyId;
    @Value(value = "${aliyun.oss.accessKeySecret}")
    public String accessKeySecret;
    @Value(value = "${aliyun.oss.endpoint}")
    public String endPoint;
    @Value(value = "${aliyun.oss.bucketName}")
    public String bucketName;
    /**
     * 封装一个判断方法
     * @param file 入参
     * @return file
     */
    public String uploadOneFile(MultipartFile file) throws Exception {
        //先判断一下是否上传为已知的照片格式
            if (!(Objects.requireNonNull(file.getOriginalFilename()).endsWith(".png"))
                    &&!(file.getOriginalFilename().endsWith(".jpg"))
                    &&!(file.getOriginalFilename().endsWith(".bmp"))
                    &&!(file.getOriginalFilename().endsWith(".gif"))
                    &&!(file.getOriginalFilename().endsWith(".jpeg"))
            ){
                throw new Exception("文件类型错误，只能为png或者jpg");
            }
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            //设置文件名
        String fileName = new DateTime().toString("yyyy/MM/dd")
                + UUID.randomUUID().toString().replace("-","")
                +file.getOriginalFilename();
        try {
            //创建PUTObject请求
            ossClient.putObject(bucketName,fileName,file.getInputStream());
            return "https://" + bucketName +"."+ endPoint +"/"+fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (ossClient != null){
                ossClient.shutdown();
            }
        }
    }

    /**
     * 删除照片方法
     * @param filerUrl 生成的照片地址
     * @return 成功
     */
    public boolean deleFile(String filerUrl){
        //创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
        /*
         * oss删除文件是根据路径删除的，但是文件完整路径中不能包含Bucket实例名。
         */
        //找到文件路径的开始下标
        int begin =("https://" +bucketName+"."+endPoint+"/").length();
        String deleterUrl = filerUrl.substring(begin);
        try {
            //删除文件请求
            ossClient.deleteObject(bucketName,deleterUrl);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (ossClient != null){
                ossClient.shutdown();
            }
        }

    }
}

