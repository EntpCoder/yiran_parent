package com.yiran.product.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Collections;
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
    @GetMapping("/userCollection/{userId}")
    public R<List<CollectionsVO>> userCollection(@PathVariable("userId") String userId){
        List<CollectionsVO> collectionsVOList = collServicel.getUserCollections(userId);
        return collectionsVOList == null ? R.fail():R.ok("collectionsVOList",collectionsVOList);
    }

    /**
     * 根据商品的id来新增
     * @param collections 购物车
     * @return 返回R
     */
    @PostMapping("/increaseCollections")
    public R<Boolean> increaseCollections(Collections collections){
        return collServicel.increaseCollections(collections) ? R.fail(ResultCodeEnum.FAIL):R.ok("isDelete",true);
    }

    /**
     * 根据收场的id来取消收藏
     * @param collectionId 收藏id
     * @return 返回R
     * */
    @DeleteMapping("/deletCollection")
    public R<Boolean> deletCollection(String collectionId){
        return collServicel.deleCollerticon(collectionId) ? R.fail(ResultCodeEnum.FAIL): R.ok("isDelete",true);
    }

}
