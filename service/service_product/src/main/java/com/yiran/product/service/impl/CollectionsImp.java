package com.yiran.product.service.impl;


import com.yiran.model.entity.Brand;
import com.yiran.model.entity.Product;
import com.yiran.product.mapper.BrandMapper;
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
    private final BrandMapper brandMapper;

    public CollectionsImp(CollectionsMapper collectionsMapper, ProductMapper productMapper,BrandMapper brandMapper) {
        this.collectionsMapper = collectionsMapper;
        this.productMapper = productMapper;
        this.brandMapper = brandMapper;
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
        for (Collections c :
             collections) {
            //一条收藏的数据
            CollectionsVO collectionsVO = new CollectionsVO();
            BeanUtils.copyProperties(c,collectionsVO);
            //根据商品的信息表中的商品来去查商id
            Product product = productMapper.selectById(c.getProId());
            Brand brand = brandMapper.selectById(product.getBrandId());
            String brandName = brand.getBrandName();
            BeanUtils.copyProperties(product,collectionsVO);
            collectionsVO.setBrandName(brandName);
            collectionsVOS.add(collectionsVO);
        }
        return collectionsVOS;
    }

    /**
     * 添加收藏
     * @param proId 商品id
     * @param userId 用户id
     * @return 是否收藏成功 boolean
     */
    @Override
    public Boolean increaseCollections(String proId,String userId){
        Collections collections = new Collections();
            collections.setProId(proId);
            collections.setUserId(userId);
            return collectionsMapper.insert(collections)>0;
    }

    /**
     * 取消收藏,删除收藏
     * @param proId 商品id
     * @param userId 用户id
     * @return 是否取消收藏成功 boolean
     */
    @Override
    public Boolean deleCollerticon(String proId,String userId){
        return collectionsMapper.deleteByUserByPro(userId, proId);
    }

    /**
     * 判断商品是否被收藏
     * @param proId 商品id
     * @param userId 用户id
     * @return boolean
     */
    @Override
    public Boolean chaxun(String proId, String userId) {
        Collections collection = collectionsMapper.selectCollection(proId, userId);
        return collection != null;
    }
}
