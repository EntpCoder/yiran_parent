package com.yiran.oss.controller;

import com.yiran.common.result.R;
import com.yiran.oss.common.AliOssUtis;
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

    /**
     * @param file 上传生成的类型
     * @return 返回上传成功的url
     */
    @SneakyThrows
    @PostMapping("/uploadOneFile")
    public R<String> uploandOneFile(MultipartFile file){
        //返回上传的oss的url
        return file == null ? R.fail():R.ok("aliOssUtis.uploadOneFile(file)",aliOssUtis.uploadOneFile(file));
    }

    /**
     * 根据生成的url来删除图片
     * @param fileUrl 生成的url
     * @return 返回删除成功
     */
    @PostMapping("deleteFile")
    public R<Boolean> deleteFile(String fileUrl){
        return fileUrl == null ? R.fail():R.ok("aliOssUtis.deleFile(fileUrl)",aliOssUtis.deleFile(fileUrl));
    }
}
