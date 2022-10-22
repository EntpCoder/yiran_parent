package com.yiran.product.service.impl;


import com.yiran.model.entity.Product;
import com.yiran.product.mapper.ProductMapper;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.vo.CollectionsVO;
import com.yiran.product.mapper.CollectionsMapper;
import com.yiran.product.service.CollServicel;
import org.springframework.stereotype.Service;
import com.yiran.model.entity.Collections;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/10/15 16:10
 */
@Service
public class CollectionsImp implements CollServicel {

    private final CollectionsMapper collectionsMapper;
    private final ProductMapper productMapper;

    public CollectionsImp(CollectionsMapper collectionsMapper, ProductMapper productMapper) {
        this.collectionsMapper = collectionsMapper;
        this.productMapper = productMapper;
    }

    /**
     *根据用户的id查看个人收藏
     * @param userId 用户id
     * @return 返回封装的集合
     */
    @Override
    public List<CollectionsVO> getUserCollections(String userId){
        //创建一个空的集合
        List<CollectionsVO> collectionsVOS =new ArrayList<>();
        //查询收藏
        QueryWrapper<Collections> collectionsQueryWrapper = new QueryWrapper<>();
        collectionsQueryWrapper.eq("user_id",userId);
        List<Collections> collections = collectionsMapper.selectList(collectionsQueryWrapper);
        System.out.println("你有吗"+collections);
        for (Collections c :
             collections) {
            //一条收藏的数据
            CollectionsVO collectionsVO = new CollectionsVO();
            BeanUtils.copyProperties(c,collectionsVO);
            //根据商品的信息表中的商品来去查商id
            Product product = productMapper.selectById(c.getProId());
            BeanUtils.copyProperties(product,collectionsVO);
        }
        return collectionsVOS;
    }

    /**
     * 根据商品的id来新增
     * @param collections 购物车
     * @return 返回R
     */
    @Override
    public Boolean increaseCollections(Collections collections){
        return collectionsMapper.insert(collections) >0;
    }

    /**
     * 根据收场的id来取消收藏
     * @param collectionId 收藏id
     * @return 返回R
     * */
    @Override
    public Boolean deleCollerticon(String collectionId){
        return collectionsMapper.deleteById(collectionId) > 0 ;
    }

}
