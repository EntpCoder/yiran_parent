package com.yiran.oss.controller;

import com.yiran.oss.utis.AliOssUtis;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 小番茄
 * @Date 2022/11/1 10:57
 */
@RestController
@RequestMapping("/oss")
public class OssController {
    public final AliOssUtis aliOssUtis;
    public OssController(AliOssUtis aliOssUtis) {
        this.aliOssUtis = aliOssUtis;
    }
    @SneakyThrows
    @PostMapping("/uploadOneFile")
    public String uploandOneFile(MultipartFile file){
        //返回上传的oss的url
        return aliOssUtis.uploadOneFile(file);
    }
}
