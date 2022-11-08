package com.yiran.product.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.vo.CollectionsVO;
import com.yiran.product.service.CollServicel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/10/15 15:35
 */
@RestController
@RequestMapping("/collections")
public class CollectionsController {
    private final CollServicel collServicel;
    public CollectionsController(CollServicel collServicel) {
        this.collServicel = collServicel;
    }
    /**
     * 根据用户的id查看个人收藏
     * @param userId 用户id
     * @return 返回封装的CollectionsVO集合
     */
    @GetMapping("/userCollection")
    public R<List<CollectionsVO>> userCollection(@RequestHeader("userId") String userId){
        List<CollectionsVO> collectionsVOList = collServicel.getUserCollections(userId);
        return collectionsVOList == null ? R.fail():R.ok("collectionsVOList",collectionsVOList);
    }

    /**
     * 添加收藏
     * @param proId 商品id
     * @param userId 用户id
     * @return 是否收藏成功 boolean
     */
    @PostMapping("/increaseCollections")
    public R<Boolean> increaseCollections(String proId,@RequestHeader("userId") String userId){
        return collServicel.increaseCollections(proId,userId) ? R.ok("isDelete",false):R.fail(ResultCodeEnum.FAIL);
    }

    /**
     * 取消收藏
     * @param proId 商品id
     * @param userId 用户id
     * @return 是否收藏成功 boolean
     */
    @DeleteMapping("/deletCollection")
    public R<Boolean> deletCollection(String proId,@RequestHeader("userId") String userId){
        return collServicel.deleCollerticon(proId,userId) ?R.ok("isDelete",true) :R.fail(ResultCodeEnum.FAIL);
    }

    /**
     * 检查用户是否已经收藏商品
     * @param proId 商品id
     * @param userId 用户id
     * @return boolean
     */
    @GetMapping("/chaxun")
    public R<Boolean> chaxun(String proId,@RequestHeader("userId") String userId){
        return collServicel.chaxun(proId,userId)?R.ok("iscollect",true):R.ok("iscollect",false);
    }

}
